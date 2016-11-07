package edu.psu.cmpsc221;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

public class SongView extends ListView<String>{

	private ArrayList<String> names;
	private ArrayList<Song> songs;
	
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
						for(i = 0; i < songs.size() && !songs.get(i).getName().equals(newVal); i++);
						main.getMediaControlView().setActiveSong(songs.get(i));
					}
				});
	}
	
	public void setDevice(Device device){
		names = new ArrayList<>();
		ArrayList<Song> sings = device.getSongs();
		for(Song song : sings){
			names.add(song.getName());
			songs.add(song);
		}
		setItems((FXCollections.observableArrayList(names)));
	}
	
}
