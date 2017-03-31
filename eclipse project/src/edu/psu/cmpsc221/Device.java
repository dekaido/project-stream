package edu.psu.cmpsc221;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * The Device class provides a pointer to a location where audio is stored.<br/>
 * This class can also list all mp3 files in the location.
 * @author David McDermott
 * @author Grace Lin
 */
public class Device implements Serializable{

	//The tree of sublocations is to be rebuilt at runtime to avoid storing massive amounts of data
	private static final long serialVersionUID = -7161358199893324302L;
	private URL location;
	transient private ArrayList<Device> devices;
	private boolean local;
	private boolean useSubDirs;
	private String name;
	private File tempDir;
	
	/**
	 * Constructor for Device
	 * Devices are meant to be either local folders, local drives, or remote servers that hold media
	 * @param name The name for the Device
	 * @param location The location for the device, may be local or networked
	 * @param local Whether or not the device is local
	 * @param useSubDirectories Whether or not to use subdirectories
	 */
	public Device(String name, URL location, boolean local, boolean useSubDirectories, File tempDir){
		this.local = local;
		devices = new ArrayList<>();
		this.location = location;
		this.useSubDirs = useSubDirectories;
		this.name = name;
		this.tempDir = tempDir;
		
		//for local devices, the URL can be treated as a file
		if(local){
			//If we use subdirectories; then we need a tree of devices
			if(useSubDirectories){
				//Convert the URL to a File
				File directory = new File(location.getFile());
				//Get a list of all subdirectories
				String[] dirs = directory.list(new FilenameFilter(){

				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
			
				});
		
				//Add those subdirectories to the list of subdevices
				//TODO make the subdirs work
				try{
					if(dirs != null){
						for(String dir : dirs){
							if(dir != null){
								try {
									devices.add(new Device(dir, new File(dir).toURI().toURL(),local,useSubDirectories, tempDir));
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					//locations = new ArrayList<>();
				}
				//For network devices, there will be no subdevices
			}else{
				devices = new ArrayList<>();
			}
		}
	}
	
	/**Tells whether the device is local or a server
	 * @return local
	 */
	public boolean isLocal(){
		return local;
	}
	
	/**
	 * Returns the list of subdevices from this device
	 * @return locations
	 */
	public ArrayList<Device> getSubDevices(){
		return devices;
	}
	
	/**
	 * Returns a list of songs on the current device
	 * @return a generated list of songs
	 */
	public ArrayList<Song> getSongs(){
		if(local){
			/*//Get a list of local songs
			ArrayList<Song> songs = new ArrayList<>();
			//TODO Remove all debug lines here
			System.err.println("Current Device: " + location);
			
			//Pull a list of songs from the parent directory
			File dir = new File(location.getFile().replace("%20", " "));
			
			System.out.println("Device Location " + dir.getAbsolutePath());
			
			//list all mp3s
			String[] files = dir.list(new FilenameFilter(){

				@Override
				public boolean accept(File dir, String name) {
					//TODO DEBUG
					System.out.println("Location found " + dir.getAbsolutePath() + "\\" + name);
					System.out.println("File found " + name + " in " + dir);
					return name.endsWith(".mp3");
				}
				
			});
			if(files != null){
				//convert to songs and put in the arraylist
				for(String file : files){
					System.out.println("Creating song for " + file);
					songs.add(new Song(new File((dir.getAbsolutePath()+"/"+file))));
				}
			}
			
			//get all songs from all subdevices
			if(devices != null){
				for(Device device : devices){
					//traverse the subdevice tree
					System.out.println("Scanning Locaiton: " + device.location.toString());
					songs.addAll(device.getSongs());
				}
			}
			
			return songs;*/
			//Attempt two			
			return getSongsInDir(new File(location.getFile().replaceAll("%20", " ")));
		}else{
			//Get a list of songs from the network
			ArrayList<Song> songs = new ArrayList<Song>();
			try {
				//Connect to the url
				HttpsURLConnection conn = (HttpsURLConnection) new URL(location.toString()+"/songInfo.php?name=all").openConnection();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				//Read all 
				String input;
				ArrayList<String> names = new ArrayList<>();
				//Put all names in an array
				while((input = br.readLine()) != null){
					input = input.substring(input.indexOf("file: ") + 6, input.indexOf("\tsize: "));
					names.add(input);
				}
				
				//Put each song in the temp dir
				for(String name : names){
					//Download the song
					new BackgroundDownload(location, tempDir, name);
					
					//Put it in the list
					System.out.println(this.tempDir);
					songs.add(new Song(new File(tempDir.getAbsolutePath().replace("\\","/") + "/" + name)));
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return songs;
		}
	}
	
	private ArrayList<Song> getSongsInDir(File dir){
		
		System.err.println("Searching Directory: " + dir.getAbsolutePath());
		
		//List all song files
		String[] songFiles = dir.list(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				System.out.println("MP3: " + dir.getAbsolutePath() + '\\' + name);
				return name.endsWith(".mp3");
			}
			
		});
		
		//List all directories
		String[] subDirs = dir.list(new FilenameFilter(){
			
			@Override
			public boolean accept(File dir, String name){
				System.out.println("Directory: " + dir.getAbsolutePath() + '\\' + name);
				return new File(dir.getAbsolutePath() + '/' + name).isDirectory();
			}
		});
		
		ArrayList<Song> songs = new ArrayList<>();
		
		//Add all songs from this dir
		for(String song : songFiles){
			songs.add(new Song(new File(dir.getAbsolutePath() + '/' + song)));
		}
		
		//Recursively add all subDirs
		for(String subDir : subDirs){
			System.err.println("Moving to " + dir.getAbsolutePath() + '\\' + subDir);
			songs.addAll(getSongsInDir(new File(dir.getAbsolutePath() + '/' + subDir)));
		}
		
		return songs;
		
	}
	
	public boolean usesSubDirectories(){
		return useSubDirs;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return name + "\n" + location.toString() + "\n" + local + useSubDirs;
	}
	
	/**
	 * Use this to generate the tree of subdirectories used to search files.</br>
	 * This is used after reading devices in from a file as the tree of subdirectories is transient
	 */
	public void buildDirs(){
		//TODO make this search more than one levels down
		devices = new ArrayList<>();
		
		if(useSubDirs){
			//Convert the URL to a File
			File directory = new File(location.getFile().replace("%20", " "));
			//Get a list of all subdirectories
			String[] dirs = directory.list(new FilenameFilter(){

				@Override
				public boolean accept(File current, String name) {
					return new File(current, name).isDirectory();
				}
		
			});
	
			//Add those subdirectories to the list of subdevices
			if(dirs != null){
				for(String dir : dirs){
					try {
						devices.add(new Device(dir, new File(this.location.getFile().replace("%20", " ") + dir).toURI().toURL(),local,useSubDirs, tempDir));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
			//For network devices, there will be no subdevices
		}else{
			devices = new ArrayList<>();
		}
	}
	
	public boolean exists(){
		if(local){
			File file = new File(location.getFile().replace("%20", " "));
			return file.exists();
		}else{
			//TODO handle existence of network locations
			return true;
		}
	}
	
	public void updateTemp(File tempDir){
		this.tempDir = tempDir;
	}
	
}
