package edu.psu.cmpsc221;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class SongView extends ListView<Song>{

	private ArrayList<Song> songs;
	private Main main;
	
	public SongView(Main main){
		
		songs = null;
		this.main = main;
		setOrientation(Orientation.VERTICAL);
		setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){

			@Override
            public ListCell<Song> call(ListView<Song> p) {
                 
                ListCell<Song> cell = new ListCell<Song>(){
 
                    @Override
                    protected void updateItem(Song song, boolean bln) {
                        super.updateItem(song, bln);
                        if (song != null) {
                        	//TODO Display full information on Song
                            setText(song.getName());
                        }
                    }
 
                };
                 
                return cell;
            }
			
		});

		//TODO add click actions
	}
	
	public void setDevice(Device device){
		songs = device.getSongs();
		setItems((FXCollections.observableArrayList(songs)));
	}
	
}
