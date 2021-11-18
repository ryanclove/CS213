package model;

import java.io.Serializable;
/**
 * Tagtype class to keep track of tags user can add and if it can take 1 or more inputs
 * @author jason dao, ryan coslove
 *
 */
public class TagType implements Serializable{
	/**
	 * long serialVersionUID to prevent errors
	 * name of tagType
	 * boolean to see if it can take multiple values
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public boolean multiple;
	/**
	 * takes name and boolean to see if it can take multiple inputs
	 * @param name of tag
	 * @param multiple boolean to see if it can take multiple inputs
	 */
	public TagType(String name, boolean multiple) {
		this.name=name;
		this.multiple=multiple;
	}
	/**
	 * print name of tag as toString
	 */
	public String toString() {
		return name;
	}
	
	
}
