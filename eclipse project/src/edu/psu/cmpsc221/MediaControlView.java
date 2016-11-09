package edu.psu.cmpsc221;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class MediaControlView extends GridPane{

	Song activeSong;
	
	//UI Components
	Button pause;
	Button resume;
	Button stop;
	
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
		
		//TODO add volume controls and time controls
		
		add(pause, 0, 0);
		add(resume, 0, 1);
		add(stop, 0, 2);
	}
	
	public void setActiveSong(Song song){
		if(activeSong != null)
			activeSong.getMediaPlayer().stop();
		activeSong = song;
		//TODO see if this works
		activeSong.getMediaPlayer().play();
	}
	
}
