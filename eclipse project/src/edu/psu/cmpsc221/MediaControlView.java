package edu.psu.cmpsc221;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * The MediaControlView provides control over the current song playing incluiding volume, pausing, playing, and stopping the audio.<br/>
 * This class is intended to provide total playback control over the Song
 * @author David McDermott
 * @author Grace Lin
 */
public class MediaControlView extends GridPane{

	//TODO make this a prettier grid or non grid view
	
	Song activeSong;
	
	//UI Components
	private Button pause;
	private Button resume;
	private Button stop;
	private Slider volume;
	
	public MediaControlView(Main main){
		
		activeSong = null;
		
		//Create UI Components		
		pause = new Button();
		pause.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bt_pause.png"))));
		pause.setOnAction(e -> {
			if(activeSong != null)
				activeSong.getMediaPlayer().pause();
		});
		
		stop = new Button();
		stop.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bt_stop.png"))));
		stop.setOnAction(e -> {
			if(activeSong != null)
					activeSong.getMediaPlayer().stop();
		});
		
		resume = new Button();
		resume.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("bt_play.png"))));
		resume.setOnAction(e -> {
			if(activeSong != null)
					activeSong.getMediaPlayer().play();
		});
		
		Label vol = new Label("Volume: ");
		
		//Volume is a range from 0-1 so it should be a slider
		volume = new Slider();
		volume.setMin(0);
		volume.setMax(1);
		volume.setValue(1);
		
		//Volume needs a change listener to update in realish time
		volume.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number old, Number current) {
				if(activeSong != null)
					activeSong.getMediaPlayer().setVolume(current.doubleValue());
			}
			
		});
		
		//TODO add time controls
		
		//Add all UI Components
		HBox controls = new HBox(4);
		controls.setPadding(new Insets(20));
//		controls.setAlignment(Pos.CENTER_RIGHT);
		controls.getChildren().addAll(resume, pause, stop, vol, volume);
		add(controls, 0, 0);
//		setHalignment(controls,HPos.CENTER);
		
	}
	
	/**
	 * Set the Song controlled by the View
	 * @param song The Song to be controlled
	 */
	public void setActiveSong(Song song){
		//Stop the old song
		if(activeSong != null)
			activeSong.getMediaPlayer().stop();
		activeSong = song;
		//Play the new song
		activeSong.getMediaPlayer().setVolume(volume.getValue());
		activeSong.getMediaPlayer().play();
		//TODO make split songs play successfully
	}
	
}
