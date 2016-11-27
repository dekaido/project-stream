package edu.psu.cmpsc221;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BackgroundDownload extends Thread{

	private URL location;
	private File tempDir;
	private String name;
	
	public BackgroundDownload(URL location, File tempDir, String name){
		this.location = location;
		this.tempDir = tempDir;
		this.name = name;
		
		start();
	}
	
	public void run(){
		try{
			int size = -1;
			HttpsURLConnection conn = (HttpsURLConnection) new URL(location.toString()+"/songInfo.php?name="+name).openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			//Read all 
			String input;
			//Put all names in an array
			input = br.readLine();
			input = input.substring(input.indexOf("size: ") + 6);
			input = input.trim();
			size = Integer.parseInt(input);

			System.out.println(size);
			
			//Download the file
			//TODO fix this to make the download work
			URL fileLocation = new URL(location.toString()+"/dl.php?name="+name);
			InputStream istream = fileLocation.openConnection().getInputStream();
			OutputStream ostream = new FileOutputStream(tempDir.getAbsolutePath().replaceAll("%20", " ") + "/" + name);
			byte[] buffer = new byte[size];
			int n = -1;
			while((n = istream.read(buffer)) != -1){
				ostream.write(buffer, 0, n);
			}
			ostream.close();
			istream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
