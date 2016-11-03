package edu.psu.cmpsc221;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{

	private DeviceView deviceView;
	private SongView songView;
	private PlaylistView playlistView;
	private MediaControlView mediaControlView;
	
	private static final int SCALE_FACTOR = 7;
	
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Media Player");

		//The primary container for the application is a border pane
		BorderPane container = new BorderPane();
		Scene scene = new Scene(container);
		
		//These subViews each allow for control over different aspects of the application
		deviceView = new DeviceView(this);
		songView = new SongView(this);
		playlistView = new PlaylistView(this);
		mediaControlView = new MediaControlView(this);
		
		container.setLeft(deviceView);
		container.setCenter(songView);
		container.setRight(playlistView);
		container.setBottom(mediaControlView);

		//Dynamically resize the subViews based on window size
		container.widthProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				deviceView.setMaxWidth(newValue.doubleValue() / SCALE_FACTOR);
				deviceView.setMinWidth(newValue.doubleValue() / SCALE_FACTOR);
				playlistView.setMaxWidth(newValue.doubleValue() / SCALE_FACTOR);
				playlistView.setMinWidth(newValue.doubleValue() / SCALE_FACTOR);
			}
			
		});
		
		container.heightProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mediaControlView.setMaxHeight(newValue.doubleValue() / SCALE_FACTOR);
				mediaControlView.setMinHeight(newValue.doubleValue() / SCALE_FACTOR);
			}
			
		});
		
		//Menu for application control
		//TODO add functionality to the menu
		MenuBar menu = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem addDevice = new MenuItem("Add Device");
		menuFile.getItems().add(addDevice);
		Menu menuEdit = new Menu("Edit");
		menu.getMenus().addAll(menuFile, menuEdit);
		
		//Enable the menu
		container.setTop(menu);
		
		//Show the window
		primaryStage.setScene(scene);	
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public SongView getSongView(){
		return songView;
	}

	public DeviceView getDeviceView() {
		return deviceView;
	}

	public PlaylistView getPlaylistView() {
		return playlistView;
	}

	public MediaControlView getMediaControlView() {
		return mediaControlView;
	}
	
}
