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
	
	public Song(File locationOnDisk){
		
		//Start playing from frame 0
		activeSegment = 0;
		
		//List all files in the directory <locationOnDisk>
		String[] listOfFiles = locationOnDisk.list(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".mp3");
			}
		});
		
		//Add all frames to the array list
		segments = new ArrayList<>();
		for(String fileName: listOfFiles){
			segments.add(fileName);
		}
		
		currentAudio = new AudioClip(segments.get(0));
	}

	/**
	 * Begin playback of the audioclip
	 */
	public void play(){
		currentAudio.play();
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
	
}
