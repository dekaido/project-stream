package edu.psu.cmpsc221;

import java.util.ArrayList;

public class Device {

	private ArrayList<Device> locations;
	private boolean local;
	
	public Device(File location, boolean local){
		this.local = local;
		
	}
	
	public boolean isLocal(){
		return local;
	}
	
}
