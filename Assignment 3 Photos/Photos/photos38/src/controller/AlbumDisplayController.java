package controller;
/**
 * controller that displays list of albums and options and search bar for photos
 * @author jason dao, ryan coslove
 */
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import model.Tag;
import javafx.scene.Node;
import javafx.scene.Scene;

public class AlbumDisplayController {
	/**
	 * fxml of list of albums, tag options for 1 or 1 tag searchs and combobox to choose the option you want, labels, datepickers to choose date ranges, and stores the arraylist of photolib
	 * for local use, bolean to see if datepicker has been chosen, stage and scene and the savedata object
	 */
	@FXML ListView<Album> listView;
	@FXML TextField tag1;
	@FXML TextField tag2;
	@FXML TextField value1;
	@FXML Label d;
	@FXML TextField value2;
	@FXML ComboBox<String> choice;
	@FXML DatePicker before;
	@FXML DatePicker after;
	ObservableList<Album> albumLib;
	ObservableList<PhotoLib> photoLib;
	SaveData s;
	boolean good=true;
	String username;
	private Stage stage;
	private Scene scene;
	/**
	 * sets the stage to the scene connected to this controller
	 * @param primaryStage  stage of scene
	 * @throws IOException  in case it cannot find scene
	 */
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
	}
	/**
	 * on ction event that will hide second tga if user does not want it
	 * @param e  actionevent that triggers when user selects 1 tag in dropdown
	 * @throws IOException in case it cannot find button
	 */
	public void disable(ActionEvent e) throws IOException {
		good=false;
		if (choice.getValue().equals("1 Tag")) {
			value2.setVisible(false);
			tag2.setVisible(false);
			d.setVisible(false);
		}
		else {
			value2.setVisible(true);
			tag2.setVisible(true);
			d.setVisible(true);
		}
	}
	/**
	 * when searching by tag gets input of tag entered and checks if tags are valid and have results, if not throw errors
	 * creates arraylist of results and transitions user to searchcontroller and search.fxml if there are results
	 * @param e when user hits search by tag
	 * @throws IOException in case it cannot find pictures
	 */
	public void search(ActionEvent e) throws IOException {   
		if (good==true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Must select from drop down");
			alert.showAndWait();
			return;
		}
		String temp=tag1.getText().trim();
		String temp1=value1.getText().trim();
		ArrayList<Photo> results=new ArrayList<Photo>();
		if (choice.getValue().equals("1 Tag")) {
			if (temp.equals("")|| temp1.equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Blank tag or value");
				alert.showAndWait();
				return;
			}
			
			Tag searchtag=new Tag(temp, temp1,false);
			for (int i=0;i<albumLib.size();i++) {
				for (int j=0;j<albumLib.get(i).lib.size();j++) {
					for (int k=0;k<albumLib.get(i).lib.get(j).getLib().size();k++) {
						if(albumLib.get(i).lib.get(j).getLib().get(k).returnTag().equalsIgnoreCase(searchtag.returnTag())) {
							results.add(albumLib.get(i).lib.get(j));
						}
					}
				}
			}
		}
		else {
			String temp2=tag2.getText().trim();
			String temp3=value2.getText().trim();
			if (temp.equals("")|| temp1.equals("") || temp2.equals("") || temp3.equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Blank tag or value");
				alert.showAndWait();
				return;
			}
			Tag searchtag=new Tag(temp, temp1,false);
			Tag searchtag2=new Tag(temp2, temp3,false);
			int flag=0;
			if (choice.getValue().equals("AND")){
				for (int i=0;i<albumLib.size();i++) {
					for (int j=0;j<albumLib.get(i).lib.size();j++) {
						for (int k=0;k<albumLib.get(i).lib.get(j).getLib().size();k++) {
							if(albumLib.get(i).lib.get(j).getLib().get(k).returnTag().equalsIgnoreCase(searchtag.returnTag()) ) {
								flag+=1;
							}
							if (albumLib.get(i).lib.get(j).getLib().get(k).returnTag().equalsIgnoreCase(searchtag2.returnTag())){
								flag+=1;
							}
							if (flag==2) {
								results.add(albumLib.get(i).lib.get(j));
								break;
							}
						}
						flag=0;
					}
				}
			}
			else {
				for (int i=0;i<albumLib.size();i++) {
					for (int j=0;j<albumLib.get(i).lib.size();j++) {
						for (int k=0;k<albumLib.get(i).lib.get(j).getLib().size();k++) {
							if(albumLib.get(i).lib.get(j).getLib().get(k).returnTag().equalsIgnoreCase(searchtag.returnTag()) || albumLib.get(i).lib.get(j).getLib().get(k).returnTag().equals(searchtag2.returnTag()) ) {
								results.add(albumLib.get(i).lib.get(j));
							}
						}
					}
				}
			}
		}
		if (results.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No results fit the tag");
			alert.showAndWait();
			return;
		}
		ArrayList<Photo> results2=new ArrayList<Photo>();
		for (int i=0;i<results.size();i++) {
			Photo removed=results.get(i);
			if (!results2.contains(removed)) {
				results2.add(removed);
			}
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Search.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		SearchController ad = loader.getController();
		ad.getList(s,photoLib,username, results2);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	/**
	 * when user searches by date, check for errors and if there are photos between dates, if there are redirect them to search.fxml
	 * else show error
	 * @param e when user hits search by date
	 * @throws IOException in case it cannot find folder
	 */
	public void searchDate(ActionEvent e) throws IOException { 
		LocalDate temp=before.getValue();
		LocalDate temp2=after.getValue();
		if (temp==null || temp2==null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Must choose date range, first date before or equal to second.");
			alert.showAndWait();
			return;
		}
		if (temp.isAfter(temp2)){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("First time cannot be earlier than second");
			alert.showAndWait();
			return;
		}
		ArrayList<Photo> results=new ArrayList<Photo>();
		for (int i=0;i<albumLib.size();i++) {
			for (int j=0;j<albumLib.get(i).lib.size();j++) {
				if(albumLib.get(i).lib.get(j).c.get(Calendar.YEAR)-temp.getYear()>=0 && albumLib.get(i).lib.get(j).c.get(Calendar.YEAR)-temp2.getYear()<=0) {
					if (albumLib.get(i).lib.get(j).c.get(Calendar.YEAR)-temp.getYear()==0) {
						if (albumLib.get(i).lib.get(j).c.get(Calendar.DAY_OF_YEAR)-temp.getDayOfYear()<0) {
							continue;
						}
					}
					if (albumLib.get(i).lib.get(j).c.get(Calendar.YEAR)-temp2.getYear()==0) {
						if (albumLib.get(i).lib.get(j).c.get(Calendar.DAY_OF_YEAR)-temp2.getDayOfYear()>0) {
							continue;
						}
					}
					results.add(albumLib.get(i).lib.get(j));
				}
				
			}
		}
		if (results.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No results fit the tag");
			alert.showAndWait();
			return;
		}
		ArrayList<Photo> results2=new ArrayList<Photo>();
		for (int i=0;i<results.size();i++) {
			Photo removed=results.get(i);
			if (!results2.contains(removed)) {
				results2.add(removed);
			}
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Search.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		SearchController ad = loader.getController();
		ad.getList(s,photoLib,username, results2);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	/**
	 * logs out of album page and goes back to login.fmxl and photoscontroller
	 * @param e triggers when logout button is pressed
	 * @throws IOException in case it does not get input of button pressed
	 */
	public void logout(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		VBox root = (VBox)loader.load();
		PhotosController ad = loader.getController();
		ad.getList(s,photoLib);
		s.getData(photoLib);
		s.saveFile();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * adds new album to whatever user inputs from dialog
	 * @param e if user presses add album
	 * @throws IOException in case albums cannot be found
	 */
	public void addAlbum(ActionEvent e) throws IOException {  
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(this.stage); 
		dialog.setTitle("Create Album");
		dialog.setHeaderText("Add a new album");
		dialog.setContentText("Enter name: ");
	
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { 
			if (result.get().trim().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Album must have at least one character");
				alert.showAndWait();
				return;
			}
			for (int j=0;j<albumLib.size();j++) {
				if (albumLib.get(j).name.equalsIgnoreCase(result.get().trim())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Error!");
					alert.setContentText("Album with same name(case insensitive) already exists");
					alert.showAndWait();
					return;
				}
			}
			Album newalbum=new Album(result.get().trim());
			albumLib.add(newalbum);
		}
		s.getData(photoLib);
		s.saveFile();
		listView.setItems(albumLib);
		listView.getSelectionModel().select(albumLib.size()-1);
	}
	/**
	 * renames album to whatever user inputs from dialog
	 * @param e if user chooses album and presses rename album
	 * @throws IOException in case albums cannot be found
	 */
	public void renameAlbum(ActionEvent e) throws IOException {
		if (albumLib.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to rename");
			alert.showAndWait();
			return;
		}
		int index = listView.getSelectionModel().getSelectedIndex();
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(this.stage); 
		dialog.setTitle("Edit Album Name");
		dialog.setHeaderText("Enter new name");
		dialog.setContentText("Enter name: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { 
			if (result.get().trim().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Album must have at least one character");
				alert.showAndWait();
				return;
			}
			for (int j=0;j<albumLib.size();j++) {
				if (albumLib.get(j).name.equalsIgnoreCase(result.get().trim())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Error!");
					alert.setContentText("Album with same name(case insensitive) already exists");
					alert.showAndWait();
					return;
				}
			}
			Album temporary=albumLib.get(index);
			temporary.name=result.get().trim();
			albumLib.set(index, temporary);
		}
		s.getData(photoLib);
		s.saveFile();
		listView.setItems(albumLib);
		listView.getSelectionModel().select(index);
	}
	/**
	 * deletes album that user presses(asks for prompt)
	 * @param e when user hits delete album after choosing album from list
	 * @throws IOException in case it cannot find album
	 */
	public void deleteAlbum(ActionEvent e) throws IOException {
		if (albumLib.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Prompt");
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			int index =listView.getSelectionModel().getSelectedIndex();
			boolean inBounds = (index >=0) && (index < albumLib.size()); 
			boolean note=true;
			if (listView.getSelectionModel().getSelectedIndex()==0){
				note=false;
			}
			if(inBounds = true && result.get() == ButtonType.OK) {
				int currentIndex = listView.getSelectionModel().getSelectedIndex();
				albumLib.remove(listView.getSelectionModel().getSelectedIndex());
				if(albumLib.size() > 0) {
					index = currentIndex;
					listView.getSelectionModel().select(index);
				}
			}
		}
		s.getData(photoLib);
		s.saveFile();
	}
	/**
	 * opens album in openalbum.fxml if album exits
	 * @param e when user chooses album and hits open album
	 * @throws IOException in case it cannot find album
	 */
	public void openAlbum(ActionEvent e) throws IOException {
		if (albumLib.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to open");
			alert.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/OpenAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		OpenAlbumController ad = loader.getController();
		int index = listView.getSelectionModel().getSelectedIndex();
		ad.getList(s,photoLib,username, index);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * loads page (creates listview and loads album and search results)
	 * @param s savedata object to save data
	 * @param a local user data
	 * @param username String of user
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username) {
		photoLib=a;
		this.username=username;
		this.s=s;
		for (int i=0;i<photoLib.size();i++) {
			if (photoLib.get(i).username.equals(username)) {
				albumLib=FXCollections.observableList(photoLib.get(i).lib);
				break;
			}
		}
		listView.setItems(albumLib);
		listView.getSelectionModel().select(0);
		choice.getItems().addAll(
			    "AND", "OR", "1 Tag"
			);
	}
	
	
	
}


