package edu.psu.cmpsc221;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Media Player");
		
		BorderPane container = new BorderPane();
		Scene scene = new Scene(container);
		
		DeviceView deviceView = new DeviceView();
		SongView songView = new SongView();
		PlaylistView playlistView = new PlaylistView();
		MediaControlView mediaControlView = new MediaControlView();
		
		container.setLeft(deviceView);
		container.setCenter(songView);
		container.setRight(playlistView);
		container.setBottom(mediaControlView);
		
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
}
