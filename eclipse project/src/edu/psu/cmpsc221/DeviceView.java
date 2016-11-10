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

//TODO use Devices instead of Strings
/**
 * DeviceView provides a view for all of the locations where the program should search for media.<br/>
 * The DeviceView class extends the ListView class and allows for the user to select which media to have access to.
 * @author David McDermott
 * @author Grace Lin
 */
public class DeviceView extends ListView<String>{

	ArrayList<Device> devices;
	Main main;
	ObjectOutputStream outFile;
	
	/**
	 * Create the DeviceView
	 * @param main A reference to the view containing the DeviceView
	 */
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
					d.buildDirs();
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
		//The ObjectOutputStream class writes a header at the top of every file that begins with 0xAC
		//If the ObjectInputStream finds 0xAC then it will halt
		try {
			//Create an ObjectOutputStream to write to the data file
			outFile = new ObjectOutputStream(new FileOutputStream("./Devices.dat"));
			//Write each device to the file
			for(Device d : devices)
				outFile.writeObject(d);
			
			outFile.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Get a list of all device names for the ListView
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
				//For each device, check to see if it has the same name as the selection
				for(Device d : devices){
					if(d.getName().equals(newVal))
						main.getSongView().setDevice(d);
				}
			}
		});
	}
	
	//This is called upon creating a new Device
	/**
	 * Add a device to the device view
	 * @param device the device to be added
	 */
	public void addDevice(Device device){
		//Add the device to the list of devices and update the view
		device.buildDirs();
		devices.add(device);
		ObservableList<String> list = getItems();
		list.add(device.getName());
		setItems(list);
		
		//Append the new device to the Devices.dat file
		try{
			outFile.writeObject(device);
			outFile.flush();
		} catch (IOException e) {
			// TODO Handle Exceptions
			e.printStackTrace();
		}
	}
	
}
