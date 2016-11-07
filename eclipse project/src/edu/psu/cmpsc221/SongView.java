package edu.psu.cmpsc221;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

public class SongView extends ListView<String>{

	private ArrayList<String> songs;
	private Main main;
	
	public SongView(Main main){
		
		songs = new ArrayList<>();
		this.main = main;
		setOrientation(Orientation.VERTICAL);
		//TODO convert songs to a Song arraylist

		//TODO add click actions
		songs.add("Crap");
		setItems((FXCollections.observableArrayList(songs)));
	}
	
	public void setDevice(Device device){
		songs = new ArrayList<>();
		ArrayList<Song> sings = device.getSongs();
		for(Song song : sings){
			songs.add(song.getName());
		}
		setItems((FXCollections.observableArrayList(songs)));
	}
	
}
