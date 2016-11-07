package edu.psu.cmpsc221;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Device implements Serializable{

	private static final long serialVersionUID = -7161358199893324302L;
	private URL location;
	transient private ArrayList<Device> locations;
	private boolean local;
	private boolean useSubDirs;
	private String name;
	
	/**
	 * Constructor for Device
	 * Devices are meant to be either local folders, local drives, or remote servers that hold media
	 * @param name
	 * @param location The
	 * @param local
	 * @param useSubDirectories
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
			File dir = new File(location.getFile());
			//list all mp3s
			String[] files = dir.list(new FilenameFilter(){

				@Override
				public boolean accept(File dir, String name) {
					System.out.println(name);
					return name.endsWith(".mp3");
				}
				
			});
			if(files != null){
				//convert to songs and put in the arraylist
				for(String file : files){
					songs.add(new Song(new File((dir.getPath()+"/"+file))));
				}
			}
			
			//get all songs from all subdevices
			if(locations != null){
				for(Device device : locations){
					//traverse the subdevice tree
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
	
	public void rebuildDirs(){
		
		locations = new ArrayList<>();
		
		if(useSubDirs){
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
			if(dirs != null){
				for(String dir : dirs){
					try {
						locations.add(new Device(dir, new File(dir).toURI().toURL(),local,useSubDirs));
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
	
}
