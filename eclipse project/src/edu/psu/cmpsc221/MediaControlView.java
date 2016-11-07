package edu.psu.cmpsc221;

import javafx.scene.layout.Pane;

public class MediaControlView extends Pane{

	Song activeSong;
	
	public MediaControlView(Main main){
		activeSong = null;
		setStyle("-fx-background-color: #f44256");
		setPrefHeight(50);
	}
	
	public void setActiveSong(Song song){
		if(activeSong != null)
			activeSong.stop();
		activeSong = song;
		//TODO see if this works
		activeSong.play();
	}
	
}
