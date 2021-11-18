package controller;


import java.io.IOException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import model.TagType;
/**
 * controller to add new tagtype as well as put it has multiple or single values
 * @author jason dao, ryan coslove
 *
 */
public class AddTagTypeController {
	@FXML TextField name;
	@FXML RadioButton one;
	@FXML RadioButton two;
	Photo display;
	ObservableList<PhotoLib> photoLib;
	ObservableList<TagType> tagLib;
	String username;
	int in;
	private Stage stage;
	private Scene scene;
	SaveData s;
	/**
	 * sets the stage to the scene connected to this controller
	 * @param primaryStage  stage of scene
	 * @throws IOException  in case it cannot find scene
	 */
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
	}
	/**
	 * add tag type after user inputs name and single or multiple values, then goes back to addtags.fxml
	 * @param e after user presses confirms
	 * @throws IOException in case it cannot find tags
	 */
	public void confirm(ActionEvent e) throws IOException{  
		if (one.isSelected()==false && two.isSelected()==false) {  
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Must choose if tag can allow 1 or more than 1 value");
			alert.showAndWait();
			return;
		}
		if (one.isSelected()==true && two.isSelected()==true) {  
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Must choose one or the other");
			alert.showAndWait();
			return;
		}
		
		for (int i=0;i<tagLib.size();i++) { 
			if (tagLib.get(i).name.equalsIgnoreCase(name.getText().trim())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Tag already exists. (Case insensitive)");
				alert.showAndWait();
				return;
			}
		}
		
		if (name.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Value must not be blank");
			alert.showAndWait();
			return;
		}
		TagType temp=new TagType(name.getText().trim(),two.isSelected());
		for (int i=0;i<photoLib.size();i++) {
			if (photoLib.get(i).username.equals(username)) {
				photoLib.get(i).tlib.add(temp);
				break;
			}
		}
		s.getData(photoLib);
		s.saveFile();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddTags.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		AddTagController ad = loader.getController();
		ad.getList(s,photoLib,username,in,display);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * goes back to add tag.fxml
	 * @param e after user presses cancel
	 * @throws IOException
	 */
	public void cancel (ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddTags.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		AddTagController ad = loader.getController();
		ad.getList(s,photoLib,username,in,display);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * loads page with list of tagtypes and photo to add to and savedata to save
	 * @param s savedata to save to
	 * @param a local data of user
	 * @param username current username
	 * @param in array number of user in user list
	 * @param display photo to add to
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, int in, Photo display) {
		photoLib=a;
		this.display=display;
		this.username=username;
		this.in=in;
		this.s=s;
		for (int i=0;i<photoLib.size();i++) {
			if (photoLib.get(i).username.equals(username)) {
				tagLib=FXCollections.observableList(photoLib.get(i).tlib);
				break;
			}
		}
		
	}
}

