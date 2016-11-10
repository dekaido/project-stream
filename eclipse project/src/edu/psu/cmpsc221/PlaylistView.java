package edu.psu.cmpsc221;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The PlaylistView is intended to allow for users to see the next up songs and make playlists.<br/>
 * This class is yet to be implemented
 * @author David McDermott
 * @author Grace Lin
 */
public class PlaylistView extends Pane{
//TODO implement this in any capacity - we may need to hold for now
	public PlaylistView(Main main){
		//Make the panel cyan
		setStyle("-fx-background-color: #42d1f4");
		setPrefWidth(50);
		//Let the user know that the panel does nothing; it is not an error
		Label label = new Label("Coming soon, get hype!");
		getChildren().add(label);
	}
	
}
