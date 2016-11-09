package edu.psu.cmpsc221;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class MediaControlView extends GridPane{

	Song activeSong;
	
	//UI Components
	private Button pause;
	private Button resume;
	private Button stop;
	private Slider volume;
	
	public MediaControlView(Main main){
		
		activeSong = null;
		
		//Create UI Components
		//TODO Make 'em pretty
		pause = new Button("Pause");
		pause.setOnAction(e -> {
			if(activeSong != null)
				activeSong.getMediaPlayer().pause();
		});
		
		stop = new Button("Stop");
		stop.setOnAction(e -> {
			if(activeSong != null)
					activeSong.getMediaPlayer().stop();
		});
		
		resume = new Button("Play");
		resume.setOnAction(e -> {
			if(activeSong != null)
					activeSong.getMediaPlayer().play();
		});
		
		volume = new Slider();
		volume.setMin(0);
		volume.setMax(1);
		volume.valueProperty().addListener(new ChangeListener<Number>(){

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number old, Number current) {
				if(activeSong != null)
					activeSong.getMediaPlayer().setVolume(current.doubleValue());
			}
			
		});
		
		//TODO add time controls
		
		add(pause, 0, 0);
		add(resume, 0, 1);
		add(stop, 0, 2);
		add(new Label("Volume:"),0,3);
		add(volume, 1, 3);
	}
	
	public void setActiveSong(Song song){
		if(activeSong != null)
			activeSong.getMediaPlayer().stop();
		activeSong = song;
		activeSong.getMediaPlayer().play();
		activeSong.getMediaPlayer().setVolume(volume.getValue());
	}
	
}
