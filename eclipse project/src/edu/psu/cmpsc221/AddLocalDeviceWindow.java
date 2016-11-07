package edu.psu.cmpsc221;

import java.io.File;
import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class AddLocalDeviceWindow extends Stage{

	private URL location;
	
	public AddLocalDeviceWindow(DeviceView deviceView){
		
		//Create view elements
		GridPane view = new GridPane();
		Scene scene = new Scene(view);
		
		Label lblName = new Label("Device Name: ");
		TextField txtName = new TextField();
		
		Label lblLocation = new Label("Location: ");
		Button btnLocation = new Button("Open Location Chooser");
		
		CheckBox chkSubDirs = new CheckBox("Use Sub-Directories ");
		chkSubDirs.setSelected(true);
		
		Button btnSubmit = new Button("Submit");
		Button btnCancel = new Button("Cancel");
		
		//Create spacers
		Pane spring1 = new Pane();
		spring1.setPrefHeight(10);
		Pane spring2 = new Pane();
		spring2.setPrefHeight(10);
		Pane spring3 = new Pane();
		spring3.setPrefHeight(10);
		
		//Add all elements to the window
		view.add(lblName, 0, 0);
		view.add(txtName, 0, 1);
		view.add(spring1, 0, 2);
		view.add(lblLocation, 0, 3);
		view.add(btnLocation, 1, 3);
		view.add(spring2, 0, 4);
		view.add(chkSubDirs, 0, 5);
		view.add(spring3, 0, 6);
		view.add(btnSubmit, 0, 7);
		view.add(btnCancel, 1, 7);
		
		//Set button actions
		btnCancel.setOnAction(e -> {
			close();
		});
		
		btnLocation.setOnAction(e -> {
			//Create a directory chooser window
			DirectoryChooser dirSelect = new DirectoryChooser();
			//Store the selected directory in this file
			File dirSelected = dirSelect.showDialog(this);
			//Store the directory and display it
			if(dirSelected == null){
				//TODO handle no directory selected
				lblLocation.setText("Location: No Location Selected");
			}else{
				lblLocation.setText("Location: " + dirSelected.getAbsolutePath());
				try {
					location = dirSelected.toURI().toURL();
				} catch (Exception e1) {
					// TODO Handle malformedURL exception
					e1.printStackTrace();
				}
			}
		});
		
		btnSubmit.setOnAction(e -> {
			deviceView.addDevice(new Device(txtName.getText(),location,true,chkSubDirs.isSelected()));
			close();
		});
		
		setTitle("Add a new device");
		setResizable(false);
		
		setScene(scene);
	}
	
}
