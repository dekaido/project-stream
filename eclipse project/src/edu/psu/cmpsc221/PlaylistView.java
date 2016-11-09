package edu.psu.cmpsc221;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PlaylistView extends Pane{
//TODO implement this in any capacity - we may need to hold for now
	public PlaylistView(Main main){
		setStyle("-fx-background-color: #42d1f4");
		setPrefWidth(50);
		Label label = new Label("Coming soon, get hype!");
		getChildren().add(label);
	}
	
}
