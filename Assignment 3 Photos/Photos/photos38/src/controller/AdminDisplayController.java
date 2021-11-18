package controller;


import java.io.IOException;

import java.util.Optional;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import javafx.scene.control.TextInputDialog;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PhotoLib;
import model.SaveData;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * controller to manage admin functions like adding and deleting users
 * @author jason dao, ryan coslove
 *
 */
public class AdminDisplayController {
	/**
	 * list of users, listview to display list of users, stage and scene of this controller, savadata object to save data
	 */
	@FXML ListView<PhotoLib> userListView;
	ObservableList<PhotoLib> photoLib;
	SaveData s;
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
	 * adds user if input from alert dialog is valid (name cannot previously exist, cannot be blank input, cannot make another admin)
	 * @throws IOException in case alertbox data does not come back
	 */
	public void addUser() throws IOException {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(this.stage); 
		dialog.setTitle("Create a user");
		dialog.setHeaderText("Add a new user");
		dialog.setContentText("Enter name: ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { 
			if (result.get().trim().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Username must have at least one character");
				alert.showAndWait();
				return;
			}
			if (result.get().trim().equalsIgnoreCase("admin")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Cannot use this username");
				alert.showAndWait();
				return;
			}
			for (int i=0;i<photoLib.size();i++) {
				if (photoLib.get(i).username.equals(result.get().trim())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("Error!");
					alert.setContentText("User already exists. (Case sensitive)");
					alert.showAndWait();
					return;
				}
			}
			PhotoLib newuser=new PhotoLib(result.get().trim());
			photoLib.add(newuser); 
		}
		s.getData(photoLib);
		s.saveFile();
		userListView.setItems(photoLib);
		userListView.getSelectionModel().select(photoLib.size()-1);
	    
	}
	/**
	 * deletes user admin chooses in listview with confirmation
	 * @throws IOException in case alert does not come back
	 */
	public void deleteUser() throws IOException {
		if (photoLib.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Prompt");
		alert.setContentText("Are you sure you want to delete this user?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			int index = userListView.getSelectionModel().getSelectedIndex();
			boolean inBounds = (index >=0) && (index < photoLib.size()); 
			boolean note=true;
			if (userListView.getSelectionModel().getSelectedIndex()==0){
				note=false;
			}
			if(inBounds = true && result.get() == ButtonType.OK) {
				int currentIndex = userListView.getSelectionModel().getSelectedIndex();
				photoLib.remove(userListView.getSelectionModel().getSelectedIndex());
				if(photoLib.size() > 0) {
					index = currentIndex;
					userListView.getSelectionModel().select(index);
				}
			}
		}
		s.getData(photoLib);
		s.saveFile();
	}

	/**
	 * logs out of admin page and goes back to login.fmxl and photoscontroller
	 * @param e triggers when logout button is pressed
	 * @throws IOException in case it does not get input of button pressed
	 */
	public void logout(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		VBox root = (VBox)loader.load();
		
		// Gets reference to Controller, and then calls it's Start() method
		PhotosController pLibController = loader.getController();
		pLibController.getList(s,photoLib);
		s.getData(photoLib);
		s.saveFile();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * sets up scene by choosing first user and getting list of users as well as storing data locally and savedata object locally
	 * @param s savedata object to store
	 * @param a list of users
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a) {
		this.s=s;
		photoLib=a;
		userListView.setItems(photoLib);
		userListView.getSelectionModel().select(0);
	}
	
}

