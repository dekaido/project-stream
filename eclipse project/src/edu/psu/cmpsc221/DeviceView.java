package edu.psu.cmpsc221;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

public class DeviceView extends ListView<String>{

	ArrayList<Device> devices;
	Main main;
	
	public DeviceView(Main main){
		//Set a default width
		setPrefWidth(100);
		
		devices = new ArrayList<>();
		this.main = main;
		
		//Read devices from Devices.txt into the arraylist
		try {
			//TODO get the path for Devices.txt
			Scanner inFile = new Scanner(new File("./Devices.txt"));
			while(inFile.hasNextLine()){
				String line = inFile.nextLine();
				URL location;
				String name = line;
				line = inFile.nextLine();
				try {
					location = new URL(line);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					location = null;
				}
				boolean local = inFile.nextBoolean();
				boolean subDirs = inFile.nextBoolean();
				devices.add(new Device(name, location, local, subDirs));
				inFile.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Create a list view to display all of the devices
		setOrientation(Orientation.VERTICAL);
		ArrayList<String> list = new ArrayList<>();
		for(Device device : devices){
			list.add(device.getName());
		}
		setItems(FXCollections.observableArrayList(list));
		//TODO add click actions
	}
	
}
