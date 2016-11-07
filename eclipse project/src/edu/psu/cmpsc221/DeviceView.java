package edu.psu.cmpsc221;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.ListView;

public class DeviceView extends ListView<String>{

	ArrayList<Device> devices;
	Main main;
	ObjectOutputStream outFile;
	
	public DeviceView(Main main){
		//Set a default width
		setPrefWidth(100);
		
		devices = new ArrayList<>();
		this.main = main;
		
		//Read devices from Devices.dat into the arraylist
		try(ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Devices.dat"))) {
			while(true){
				try{
					Device d = (Device)inFile.readObject();
					d.rebuildDirs();
					devices.add(d);
				}catch(EOFException e){
					break;
				} catch (ClassNotFoundException e) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Handle Many Exceptions
			e.printStackTrace();
		}
		
		//We must carry the objectoutputstream between writes to prevent writing the header multiple times
		try {
			outFile = new ObjectOutputStream(new FileOutputStream("./Devices.dat"));
			
			for(Device d : devices)
				outFile.writeObject(d);
			
			outFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Create a list view to display all of the devices
		setOrientation(Orientation.VERTICAL);
		ArrayList<String> list = new ArrayList<>();
		for(Device device : devices){
			list.add(device.getName());
		}
		setItems(FXCollections.observableArrayList(list));

		//Handle clicks
		getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selection, String oldVal, String newVal) {
				for(Device d : devices){
					if(d.getName().equals(newVal))
						main.getSongView().setDevice(d);
				}
			}
		});
	}
	
	public void addDevice(Device device){
		devices.add(device);
		ObservableList<String> list = getItems();
		list.add(device.getName());
		setItems(list);
		
		try{
			outFile.writeObject(device);
			outFile.flush();
		} catch (IOException e) {
			// TODO Handle Exceptions
			e.printStackTrace();
		}
	}
	
}
