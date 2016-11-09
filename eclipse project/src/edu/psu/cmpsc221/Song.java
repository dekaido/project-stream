package edu.psu.cmpsc221;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Song {

	private MediaPlayer currentAudio;
	private MediaPlayer nextAudio;
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
			currentAudio = new MediaPlayer(new Media(segments.get(0).replace('\\', '/')));

			//If the song is an mp3 file, then set a flag
		}else{
			//TODO parse name
			name = locationOnDisk.getName();
			activeSegment = -1;
			currentAudio = new MediaPlayer(new Media(locationOnDisk.toURI().toString()));
		}
		
	}

	public MediaPlayer getMediaPlayer(){
		return currentAudio;
	}
	
	public void advanceMedia(){
		currentAudio = nextAudio;
		nextAudio = new MediaPlayer(new Media(segments.get(++activeSegment)));
	}
	
	public String getName(){
		return name;
	}
}
