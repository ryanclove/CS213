package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * class to save arraylist of photolib to serialize and deserialize
 * @author jason dao, ryan coslove
 *
 */
public class SaveData {
	/**
	 * arraylist of photolib of all data
	 */
	public ArrayList<PhotoLib>data;
	/**
	 * default contructor when there is no previous data, make new arraylist
	 */
	public SaveData() {
		data=new ArrayList<PhotoLib>();
	}
	/**
	 * gets arraylist of photolib as param and keeps it for serialization
	 * @param data (arraylist of photolib to set to)
	 */
	public SaveData(ArrayList<PhotoLib>data) {
		this.data=data;
	}
	/**
	 * Gets an observable list of PhotoLib and keeps it as arraylist to save
	 * @param a Observable list of PhotoLib(list of users)
	 */
	public void getData(ObservableList<PhotoLib>a) {
		this.data=new ArrayList<PhotoLib>(a);
	}
	/**
	 * saves arraylist of photolib (with libs of album, photo, tag and tagtype as arraylist in Data.dat)
	 * @throws IOException  in case file doe snot exists
	 */
	public void saveFile() throws IOException {
		 FileOutputStream fileOutputStream
	      = new FileOutputStream("Data.dat");
	    ObjectOutputStream objectOutputStream 
	      = new ObjectOutputStream(fileOutputStream);
	    objectOutputStream.writeObject(new ArrayList<PhotoLib>(data));
		objectOutputStream.flush();
	    objectOutputStream.close();
	}
	/**
	 * gets arraylist from data.data and saves it to arraylist of this class
	 * @throws FileNotFoundException  in case file is not found
	 * @throws IOException   in case file is not found
	 * @throws ClassNotFoundException  in case data cannot be pulled from dat file
	 */
	public void getFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Data.dat"));
		this.data = ((ArrayList<PhotoLib>)ois.readObject());
	}
}
