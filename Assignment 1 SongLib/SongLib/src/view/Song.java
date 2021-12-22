package view;

/*
 * Jason Dao jnd88
 * Ryan Coslove rmc326
 * 
 */
class Song {
	private String name = "name";
	private String artist = "artist";
	private String album = "";
	private int year = 0;
	
	public Song() { }
	public Song(String name) {
		setName(name);
	}
	public Song(String name, String artist) {
		setName(name);
		setArtist(artist);
	}
	public Song(String name, String artist, String album) {
		setName(name);
		setArtist(artist);
		setAlbum(album);
	}
	public Song(String name, String artist, String album,int year) {
		setName(name);
		setArtist(artist);
		setAlbum(album);
		setYear(year);
	}
	public Song(String name, String artist, int year) {
		setName(name);
		setArtist(artist);
		setYear(year);
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	public String toString() {
		return name + " | " + artist;
	}
	public String forCSV() {  //method for CSV
		return name + "|" + artist + "|" + album+ "|" + String.valueOf(year);
	}
}
