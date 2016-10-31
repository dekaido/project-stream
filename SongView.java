package edu.psu.cmpsc221;


import java.io.File;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

public class SongView extends Pane{
    private AudioClip audio;
    private ArrayList<File> location;
    private File directory;
    
    public SongView(){
            setStyle("-fx-background-color: #6bf442");
    }
    public String getName(){
        return audio.toString();
    }
    public File getLocation(){
        return directory;
    }
    
    public void setLocation(File f){
        
    }
    public double getVolume(){
        return audio.getVolume();
    }
    public double getTimeStamp(){
        return audio.getRate();
    }
    
}
