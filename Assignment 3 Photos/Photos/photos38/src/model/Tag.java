package model;

import java.io.Serializable;
/**
 * class to create Tag object to store tags
 * @author jason dao ryan coslove
 *
 */
public class Tag implements Serializable {
	/**
	 * serialuid to serialize data
	 * name of tag type
	 * value of tag
	 * multiple if tag can have more than one value or not
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public String value;
	public boolean multiple;
	/**
	 * constructor that creates tag with name, value and boolean multiple from input
	 * @param name tagtype
	 * @param value of tag
	 * @param multiple (see if it takes multiple inputs or not)
	 */
	public Tag(String name, String value, boolean multiple) {
		this.name=name;
		this.value=value;
		this.multiple=multiple;
	}
	/**
	 * compare tags to see if they are the same
	 * @param temp (string of tag to compare)
	 * @return boolean if true or false
	 */
	public boolean compare(String temp) {
		if (temp.equalsIgnoreCase(name+"="+"value")){
			return true;
		}
		return false;
	}
	/**
	 * return name and value to later compare with other tags
	 * @return string to compare
	 */
	public String returnTag() {
		return name+"="+value;
	}
	/**
	 * when printing tag, just print name and value
	 */
	public String toString() {
		return name+"="+value;
	}
	
}
