package edu.psu.cmpsc221;

import javafx.scene.layout.Pane;

public class MediaControlView extends Pane{

	Song activeSong;
	
	public MediaControlView(Main main){
		setStyle("-fx-background-color: #f44256");
		setPrefHeight(50);
	}
	
}
