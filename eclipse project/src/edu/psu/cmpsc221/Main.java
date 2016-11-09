package edu.psu.cmpsc221;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Main extends Application{

	private DeviceView deviceView;
	private SongView songView;
	private PlaylistView playlistView;
	private MediaControlView mediaControlView;
	
	//I wish that I could use #DEFINE here
	private static final int SCALE_FACTOR = 7;
	
	public static void main(String[] args){
		launch();
	}

	@Override
	public void start(Stage primaryStage){
		
		boolean firstRun = true;
		
		if(new File("Devices.dat").exists())
			firstRun = false;
		
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
		//TODO add more functionality to the menu
		MenuBar menu = new MenuBar();
		Menu menuFile = new Menu("File");
		MenuItem addLocalDevice = new MenuItem("Add Local Device");
		menuFile.getItems().add(addLocalDevice);
		menu.getMenus().add(menuFile);
		
		addLocalDevice.setOnAction(e -> {
			AddLocalDeviceWindow wind = new AddLocalDeviceWindow(deviceView);
			wind.initOwner(primaryStage);
			wind.initModality(Modality.WINDOW_MODAL);
			wind.show();
		});
		
		//Enable the menu
		container.setTop(menu);
		
		//Show the window
		primaryStage.setScene(scene);	
		primaryStage.setMaximized(true);
		primaryStage.show();
		
		//If this is the first run, then introduce yourself
		if(firstRun){
			Alert dialogue = new Alert(Alert.AlertType.INFORMATION);
			dialogue.setTitle("Welcome to Media Player");
			dialogue.setHeaderText("How to get started");
			dialogue.setContentText("To get started, just go to the file menu, and create a new device by picking any folder with \"mp3\" files in it.\n"
					+ "Once you have a device, you may select your device from the menu on your left, and then the song that you would like from the menu on your right.\n"
					+ "We hope that you enjoy our media player.");
			dialogue.showAndWait();
		}
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
