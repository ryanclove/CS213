package model;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
/**
 *creates a Photo object that stores the photo, caption, date, and arraylist of tags
 * @author jason dao, ryan coslove
 *
 */

public class Photo implements Serializable{
	/**
	 * long serialVersionUID to prevent errors
	 * file of the photo to be used
	 * String of caption of photo
	 * date of object(date of last modified)
	 * arraylist of tags of the photo
	 */
	private static final long serialVersionUID = 1L;
	public File location;  
	public String caption;
	public Calendar c;     
	private ArrayList<Tag> lib;
	/**
	 * creates new Photo by getting last modified date and adding it and creating new list of tags for that photo as well as storing photo
	 * @param location photo file to add
	 */
	public Photo(File location) {
		this.location=location;
		c=Calendar.getInstance();
		SimpleDateFormat d=new SimpleDateFormat("MM");
		SimpleDateFormat e=new SimpleDateFormat("dd");
		SimpleDateFormat f=new SimpleDateFormat("yyyy");
		SimpleDateFormat g=new SimpleDateFormat("kk");
		SimpleDateFormat h=new SimpleDateFormat("mm");
		c.set(Calendar.MONTH,Integer.parseInt(d.format(location.lastModified()))-1);
		c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(e.format(location.lastModified())));
		c.set(Calendar.YEAR,Integer.parseInt(f.format(location.lastModified())));
		c.set(Calendar.HOUR,Integer.parseInt(g.format(location.lastModified())));
		c.set(Calendar.MINUTE,Integer.parseInt(h.format(location.lastModified())));
		c.set(Calendar.MILLISECOND,0);
		this.lib=new ArrayList<Tag>();
	}
	/**
	 * add tag to photo object
	 * @param temp Tag object that is a tag of the photo
	 */
	public void addTag(Tag temp) {
		lib.add(temp);
	}

	/**
	 * set caption of the photo
	 * @param caption String that will be the caption
	 */
	public void setCaption(String caption) {
		this.caption=caption;
	}
	/**
	 * returns the library of tags
	 * @return Arraylist of tags
	 */
	public ArrayList<Tag> getLib() {
		return lib;
	}
	/**
	 * returns file object
	 * @return file of photo
	 */
	public File getLocation() {
		return location;
	}
	/**
	 * return caption of photo
	 * @return string of photo
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * when photo is printed or displayed in listview, print object
	 */
	public String toString() {
		return caption;
	}
}
