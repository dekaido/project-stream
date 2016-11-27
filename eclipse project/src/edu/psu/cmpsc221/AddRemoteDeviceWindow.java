package edu.psu.cmpsc221;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddRemoteDeviceWindow extends Stage{

	public AddRemoteDeviceWindow(DeviceView deviceView, File tempDir){
		
		GridPane view = new GridPane();
		Scene scene = new Scene(view);
		
		Button btnSubmit = new Button("Submit");
		Button btnCancel = new Button("Cancel");
		
		Label lblLocation = new Label("Location: ");
		Label lblName = new Label("Name: ");
		
		TextField txtLocation = new TextField();
		TextField txtName = new TextField();
		
		Pane spring1 = new Pane();
		spring1.setPrefHeight(10);
		Pane spring2 = new Pane();
		spring2.setPrefHeight(10);
		
		view.add(lblName, 0, 0);
		view.add(txtName, 1, 0);
		view.add(spring1, 0, 1);
		view.add(lblLocation, 0, 2);
		view.add(txtLocation, 1, 2);
		view.add(spring2, 0, 3);
		view.add(btnSubmit, 0, 4);
		view.add(btnCancel, 1, 4);
		
		txtLocation.setText("https://");
		
		btnCancel.setOnAction(e -> {
			close();
		});
		
		btnSubmit.setOnAction(e -> {
			//Validate URL by connecting to it
			System.out.println("Validating URL");
			try{
				URL url = new URL(txtLocation.getText());
				URLConnection conn = url.openConnection();
				conn.connect();
			}catch(MalformedURLException e1){
				System.err.println("Bad URL format");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Invalid URL format");
				alert.setContentText("Please use a URL format of the form https://example.com");
				alert.showAndWait();
				return;
			} catch (Exception e1) {
				System.err.println("Bad URL");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Invalid URL");
				alert.setContentText("Please use a URL format of the form https://example.com with a valid https/ssl plaintext server");
				alert.showAndWait();
				return;
			}
			//If URL is valid, keep going
			try {
				deviceView.addDevice(new Device(txtName.getText(), new URL(txtLocation.getText()), false, false, tempDir));
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			close();
		});
		
		setTitle("Add a new Device");
		setResizable(false);
		
		setScene(scene);
		
	}
	
}
