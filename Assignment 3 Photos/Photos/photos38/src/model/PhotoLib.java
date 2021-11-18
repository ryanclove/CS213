package model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * class that implements user and their album library and their tagtype library
 * @author jason dao ryan coslove
 *
 */

public class PhotoLib implements Serializable {
	/**
	 * serialVersionUID to serialize this
	 * username String to store username
	 * list to store albums with user
	 * list to store tagtypes user added
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public ArrayList<Album> lib;
	public ArrayList<TagType> tlib;
	/**
	 * constructor to add new user, add user with new album list, tagtpe list (preset location and person tags added)
	 * @param username string to create new username as
	 */
	public PhotoLib(String username) {
		this.username=username;
		this.lib=new ArrayList<Album>();
		this.tlib=new ArrayList<TagType>();
		tlib.add(new TagType("location",false));
		tlib.add(new TagType("person",true));
	}
	/**
	 * add album to list
	 * @param temp Album to add
	 */
	public void addAlbum(Album temp) {
		lib.add(temp);
	}
	/**
	 * return album if it is in library
	 * @param temp string of album name to check
	 * @return Album if it has that name
	 */
	public Album getAlbum(String temp) {
		for(int i=0;i<lib.size();i++) {
			if (lib.get(i).name.equalsIgnoreCase(temp)) {
				return lib.get(i);
			}
		}
		return null;
	}
	/**
	 * checks to see if album exist in library
	 * @param temp string of album name to check
	 * @return boolean to see if true or false
	 */
	public boolean isAlbumThere(String temp) {   
		for(int i=0;i<lib.size();i++) {
			if (lib.get(i).name.equalsIgnoreCase(temp)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * prints username when printing out user data
	 */
	public String toString() {
		return username;
	}
}
