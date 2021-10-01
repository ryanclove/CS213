package view;

/*
 * Jason Dao jnd88
 * Ryan Coslove rmc326
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class SongLibController {
	@FXML ListView<Song> songListView;
	private ObservableList<Song> songList;
	@FXML ListView<String> detailListView;
	private ObservableList<String> detailList;
	private Stage stage;
	private Scene scene;
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
		this.songList = FXCollections.observableArrayList();
		
		File csvFile = new File("Song.csv");
		if (csvFile.isFile()) {
			BufferedReader csvReader = new BufferedReader(new FileReader("Song.csv"));
			String temp;
			while ((temp=csvReader.readLine()) != null) {
				String []data=temp.split("\\|");
				Song temp2=new Song(data[0],data[1],data[2],Integer.parseInt(data[3]));
				this.songList.add(temp2);	            
			}
			csvReader.close();
		}
		songListView.setItems(songList);
		songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectSong());
		songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> details());
		songListView.getSelectionModel().select(0);
	}
	private void details() {  
		Song item = songListView.getSelectionModel().getSelectedItem();
		if (item!=null){
			this.detailList=FXCollections.observableArrayList("Song: "+item.getName());
			this.detailList.add("Artist: "+item.getArtist());
			this.detailList.add("Album: "+item.getAlbum());
			if (item.getYear()==0) {
				this.detailList.add("Year: ");
			}
			else {
				this.detailList.add("Year: "+item.getYear());
			}
			detailListView.setItems(detailList);
		}
	}
	public void addSong(ActionEvent e) throws IOException {
	    FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/SecondScene.fxml"));
		AnchorPane add = (AnchorPane)loader.load();
		SecondSceneController second = loader.getController();
		//second.start(stage);
		second.getList(songList,1);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(add);
		stage.setScene(scene);
		stage.show();
	}
	
	public void deleteSong(ActionEvent e) throws IOException {  
		if (songList.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Prompt");
		alert.setContentText("Are you sure you want to delete this song?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			detailList.clear();
			int index = songListView.getSelectionModel().getSelectedIndex();
			boolean inBounds = (index >=0) && (index < songList.size()); 
			boolean note=true;
			if (songListView.getSelectionModel().getSelectedIndex()==0){
				note=false;
			}
			if(inBounds = true && result.get() == ButtonType.OK) {
				int currentIndex = songListView.getSelectionModel().getSelectedIndex();
				songList.remove(songListView.getSelectionModel().getSelectedIndex());
				if(songList.size() > 0) {
					index = currentIndex;
					songListView.getSelectionModel().select(index);
				}
			}
			
			FileWriter csvWriter = new FileWriter("Song.csv");
			for (int i=0;i<songList.size();i++) {
				
				csvWriter.write(songList.get(i).forCSV());
				csvWriter.write("\n");
			}
			csvWriter.flush();
			csvWriter.close();
			songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectSong());
			songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> details());
			if (note==false && songList.size()!=0) {
				Song item=songList.get(0);
				this.detailList.add("Song: "+item.getName());
				this.detailList.add("Artist: "+item.getArtist());
				this.detailList.add("Album: "+item.getAlbum());
				if (item.getYear()==0) {
					this.detailList.add("Year: ");
				}
				else {
					this.detailList.add("Year: "+item.getYear());
				}
				detailListView.setItems(detailList);
			}
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
	}
	public void editSong(ActionEvent e) throws IOException{	
		if (songList.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to edit");
			alert.showAndWait();
			return;
		}
	    FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(
				getClass().getResource("/view/SecondScene.fxml"));
		AnchorPane add = (AnchorPane)loader.load();
		SecondSceneController second = loader.getController();
		//second.start(stage);
		second.getList(songList,0);
		int index = songListView.getSelectionModel().getSelectedIndex();
		Song item = songListView.getSelectionModel().getSelectedItem();
		second.getSong(index, item);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(add);
		stage.setScene(scene);
		stage.show();
	}
	
	public void selectSong() {
		//System.out.println("selectSong called: " + songListView.getSelectionModel().getSelectedIndex());
	}
	public void getList(ObservableList<Song> a, String b, String c) throws IOException {
		songList=a;
		songListView.setItems(songList);
		FXCollections.sort(songList, new SongComparator());
		FileWriter csvWriter = new FileWriter("Song.csv");
		int j=0;
		if (b!=null){
			for (int i=0;i<songList.size();i++) {
				if (songList.get(i).getName().compareTo(b)==0 && songList.get(i).getArtist().compareTo(c)==0) {
					j=i;
				}
				csvWriter.write(songList.get(i).forCSV());
				csvWriter.write("\n");
			}
		}
		else {
			for (int i=0;i<songList.size();i++) {
				csvWriter.write(songList.get(i).forCSV());
				csvWriter.write("\n");
			}
		}
		csvWriter.flush();
		csvWriter.close();
		songListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> selectSong());
		songListView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				details());
		songListView.getSelectionModel().select(j);
	}
	public class SongComparator implements Comparator<Song>{
		 //less than 0 if song1 is earlier in the alphabet, else if song1 appears after song2
		@Override
		public int compare(Song song1, Song song2) {
			int result = song1.getName().toLowerCase().compareTo(song2.getName().toLowerCase());
			if (result == 0) {
	            // compare artist names
	            result = song1.getArtist().toLowerCase().compareTo(song2.getArtist().toLowerCase());
			}
			return result;
		}
	}
	public class IgnoreCaseSensitiveComparator implements Comparator<Object> {
	    public int compare(Object o1, Object o2) {
	        String s1 = (String) o1;
	        String s2 = (String) o2;
	        return s1.toLowerCase().compareTo(s2.toLowerCase());
	    }
	}
}