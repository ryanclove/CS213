package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**This creates an Album class to store an album and a list of photos that go with it. It is serializable
 * to be able to be serialized.
 *   
 * @author Jason Dao, ryan coslove
 *
 */
public class Album implements Serializable {
	/**
	 * long serialVersionUID to prevent errors
	 * String name of album
	 * Arraylist of photos in album
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public ArrayList<Photo> lib;
	/**
	 * This constructor creates a new album and takes in a name to make it the name of the album 
	 * as well as an ArrayList of Photos.
	 * @param name (set album name to this)
	 */
	public Album(String name) {
		this.name=name;
		this.lib=new ArrayList<Photo>();
	}
	/**
	 * This method allows a Photo to be passed in to be added to an album.
	 * @param temp  (add this photo to list)
	 */
	public void addPhoto(Photo temp) {
		lib.add(temp);
	}
	/**
	 * This checks to see if a Photo is in an album by comparing the file path String of the Photo 
	 * and each element in the Arraylist and returns a boolean of if the Photo was found in the album.
	 * 
	 * @param temp (photo to see if photo is in album)
	 * @return boolean to see if it is there
	 */
	public boolean isPhotoThere(Photo temp) {
		for(int i=0;i<lib.size();i++) {
			if (lib.get(i).location.equals(temp.location)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * This prints out the album with name, size, earliest and latest date in toString method.
	 */
	public String toString() {
		if (lib.size()==0) {
			return "Name:"+name +"\tSize:"+lib.size() + "\tEarliest Photo:NA\tLatest Photo:NA";
		}
		if (lib.size()==1) {
			SimpleDateFormat d=new SimpleDateFormat("MM/dd/yyyy kk:mm");
			return "Name:"+name +"\tSize:"+lib.size() + "\tEarliest Photo:" + d.format(lib.get(0).c.getTime())+"\tLatest Photo:"+ d.format(lib.get(0).c.getTime());
		}
		SimpleDateFormat d=new SimpleDateFormat("MM/dd/yyyy kk:mm");
		Calendar temp=lib.get(0).c;
		Calendar temp2=lib.get(0).c;
		for (int i=1;i<lib.size();i++) {
			if (temp.compareTo(lib.get(i).c)<0){
				temp=lib.get(i).c;
			}
		}
		for (int i=1;i<lib.size();i++) {
			if (temp2.compareTo(lib.get(i).c)>0){
				temp2=lib.get(i).c;
			}
		}
		return "Name:"+name +"\tSize:"+lib.size() +"\tEarliest Photo:" + d.format(temp2.getTime())+"\tLatest Photo:"+ d.format(temp.getTime());
	}
}
