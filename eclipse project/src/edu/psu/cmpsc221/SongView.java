package edu.psu.cmpsc221;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

/**
 * SongView provides a list of songs for the user to play.<br/>
 * The SongView receives a list of songs and then pushes the selected song to the MediaControlView
 * @author David McDermott
 * @author Grace Lin
 */
public class SongView extends ListView<String>{

	private ArrayList<String> names;
	private ArrayList<Song> songs;
	
	/**
	 * Create the SongView
	 * @param main A reference to the view containing the DeviceView
	 */
	public SongView(Main main){
		
		names = new ArrayList<>();
		songs = new ArrayList<>();
		setOrientation(Orientation.VERTICAL);
		//TODO convert songs to a Song arraylist

		//Handle clicks
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selection, String oldVal, String newVal) {
			int i;
			//Find the song with the same name as the selection, and then make it the active song
			for(i = 0; i < songs.size() && !songs.get(i).getName().equals(newVal); i++);
				main.getMediaControlView().setActiveSong(songs.get(i));
			}
		});
	}
	
	/**
	 * Populate the song list from a device
	 * @param device
	 */
	public void setDevice(Device device){
		//Create a list of song names and songs from the device's songs
		names = new ArrayList<>();
		ArrayList<Song> sings = device.getSongs();
		for(Song song : sings){
			names.add(song.getName());
			songs.add(song);
		}
		setItems((FXCollections.observableArrayList(names)));
	}
	
}
