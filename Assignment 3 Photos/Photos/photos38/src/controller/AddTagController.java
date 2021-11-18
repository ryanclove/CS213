package controller;


import java.io.FileNotFoundException;

import java.io.IOException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import model.Tag;
import model.TagType;
/**
 * controller to add tag to photo
 * @author jason dao, ryan coslove
 *
 */
public class AddTagController {
	/**
	 * userdata, saveData object, as well as taglibrary of tags of current photo
	 */
	@FXML ListView<TagType> listView;
	@FXML TextField value;
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
	 * add tag after checking if another value can be added and for no repeats, redirects to tagcontroller afterwards
	 * @param e after user hits add and chooses tag type and puts value
	 * @throws IOException
	 */
	public void add(ActionEvent e) throws IOException{  
		int index=listView.getSelectionModel().getSelectedIndex();
		if(tagLib.get(index).multiple==false) {
			for (int i=0;i<display.getLib().size();i++) {
				if (display.getLib().get(i).name.equalsIgnoreCase(tagLib.get(index).name)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Error!");
					alert.setContentText("This tag can only have 1 value.");
					alert.showAndWait();
					return;
				}
			}
		}
		if (value.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("Value must not be blank");
			alert.showAndWait();
			return;
		}
		Tag temp=new Tag(tagLib.get(index).name,value.getText().trim(), tagLib.get(index).multiple);
		for (int i=0;i<display.getLib().size();i++) {
			if (temp.returnTag().equalsIgnoreCase(display.getLib().get(i).returnTag())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("This tag already exists. (Case insensitive)");
				alert.showAndWait();
				return;
			}
		}
		display.getLib().add(temp);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Edit.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		TagController ad = loader.getController();
		ad.getList(s,photoLib,username,in,display);
		s.getData(photoLib);
		s.saveFile();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * goes to addtagtype fxml page
	 * @param e after user hits add tagtype
	 * @throws IOException
	 */
	public void addT(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AddTagType.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		AddTagTypeController ad = loader.getController();
		ad.getList(s,photoLib,username,in,display);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * goes back to tagcontroller
	 * @param e after  user hits back
	 * @throws IOException
	 */
	public void cancel (ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Edit.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		TagController ad = loader.getController();
		ad.getList(s,photoLib,username,in,display);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * loads tagtype user has and textbox to add field as well as button if they want to add another tagtype
	 * @param s SaveData
	 * @param a user data 
	 * @param username string of current username
	 * @param in index of user in album
	 * @param display current photo to add tag to
	 * @throws FileNotFoundException
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, int in, Photo display) throws FileNotFoundException {
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
		listView.setItems(tagLib);
		listView.getSelectionModel().select(0);	
	}
}
