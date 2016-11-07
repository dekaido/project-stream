package edu.psu.cmpsc221;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Song {

	private AudioClip currentAudio;
	private AudioClip nextAudio;
	private ArrayList<String> segments;
	private int activeSegment;
	private String name;
	
	/**
	 * Constructor for a Song<br/>
	 * Songs hold information about an mp3 file and all of its frames
	 * @param locationOnDisk location for the file or directory of the frames
	 * @param split whether or not the song is meant to be streamed
	 */
	public Song(File locationOnDisk){
		
		//Start playing from frame 0
		activeSegment = 0;
		
		//If the song is a set of frames
		if(locationOnDisk.isDirectory()){
			//List all files in the directory <locationOnDisk>
			name = locationOnDisk.getName();
			String[] listOfFiles = locationOnDisk.list(new FilenameFilter(){
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(".mp3");
				}
			});
		
			//Add all frames to the array list
			segments = new ArrayList<>();
			for(String fileName : listOfFiles){
				segments.add(fileName);
			}
			
			//Set the current audioclip from the segments
			currentAudio = new AudioClip(segments.get(0).replace('\\', '/'));

			//If the song is an mp3 file, then set a flag
		}else{
			//TODO parse name
			name = locationOnDisk.getName();
			activeSegment = -1;
			currentAudio = new AudioClip(locationOnDisk.toURI().toString());
		}
		
	}

	/**
	 * Begin playback of the audioclip
	 */
	public void play(){
		currentAudio.play();
		//If the song has multiple segments
		if(activeSegment > -1)
			nextAudio = new AudioClip(segments.get(activeSegment)+1);
	}
	
	/**
	 * Immediately halt the audioclip
	 */
	public void stop(){
		currentAudio.stop();
	}

	/**
	 * Advance to the next frame of the song
	 */
	public void advance(){
		if(activeSegment < 0)
			return;
		currentAudio = nextAudio;
		currentAudio.play();
		nextAudio = new AudioClip(segments.get(++activeSegment));
	}
	
	/**
	 * Get the current volume
	 * @return volume : double
	 */
	public double getVolume(){
		return currentAudio.getVolume();
	}
	
	/**
	 * Returns whether or not the song is playing
	 * @return is playing : boolean
	 */
	public boolean isPlaying(){
		return currentAudio.isPlaying();
	}
	
	public String getName(){
		return name;
	}
}
