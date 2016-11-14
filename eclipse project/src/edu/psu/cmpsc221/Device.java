package edu.psu.cmpsc221;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
	transient private ArrayList<Device> locations;
	private boolean local;
	private boolean useSubDirs;
	private String name;
	
	/**
	 * Constructor for Device
	 * Devices are meant to be either local folders, local drives, or remote servers that hold media
	 * @param name The name for the Device
	 * @param location The location for the device, may be local or networked
	 * @param local Whether or not the device is local
	 * @param useSubDirectories Whether or not to use subdirectories
	 */
	public Device(String name, URL location, boolean local, boolean useSubDirectories){
		this.local = local;
		locations = new ArrayList<>();
		this.location = location;
		this.useSubDirs = useSubDirectories;
		this.name = name;
		
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
									locations.add(new Device(dir, new File(dir).toURI().toURL(),local,useSubDirectories));
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
				locations = new ArrayList<>();
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
		return locations;
	}
	
	/**
	 * Returns a list of songs on the current device
	 * @return a generated list of songs
	 */
	public ArrayList<Song> getSongs(){
		if(local){
			//Get a list of local songs
			ArrayList<Song> songs = new ArrayList<>();
			
			//Pull a list of songs from the parent directory
			File dir = new File(location.getFile().replace("%20", " "));
			//TODO DEBUG
			System.out.println("Device Location " + dir.getAbsolutePath());
			//list all mp3s
			String[] files = dir.list(new FilenameFilter(){

				@Override
				public boolean accept(File dir, String name) {
					//TODO DEBUG
					System.out.println("Location found " + dir.getAbsolutePath() + "\\" + name);
					return name.endsWith(".mp3");
				}
				
			});
			if(files != null){
				//convert to songs and put in the arraylist
				for(String file : files){
					songs.add(new Song(new File((dir.getAbsolutePath()+"/"+file))));
				}
			}
			
			//get all songs from all subdevices
			if(locations != null){
				for(Device device : locations){
					//traverse the subdevice tree
					System.out.println("Scanning Locaiton: " + device.location.toString());
					songs.addAll(device.getSongs());
				}
			}
			
			return songs;
		}else{
			//TODO Get a list of songs from the network
			return null;
		}
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
		locations = new ArrayList<>();
		
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
						locations.add(new Device(dir, new File(this.location.getFile().replace("%20", " ") + dir).toURI().toURL(),local,useSubDirs));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
			//For network devices, there will be no subdevices
		}else{
			locations = new ArrayList<>();
		}
	}
	
	public boolean exists(){
		if(local){
			File file = new File(location.getFile().replace("%20", " "));
			return file.exists();
		}else{
			//TODO handle existence of network locations
			return false;
		}
	}
	
}
