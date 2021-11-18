package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import model.Tag;
/**
 * main page to edit tags (add, delete, or add tag type)
 * @author jason dao, ryan coslove
 *
 */
public class TagController {
	/**
	 * fxml functions on site(listview, caption, picture) as well as current user data (user data, username)
	 * and functions to save and stage and scene
	 */
	@FXML Label caption;
	@FXML Label dateTime;
	@FXML ImageView imageView;
	@FXML ListView<Tag> listView;
	Photo display;
	ObservableList<PhotoLib> photoLib;
	ObservableList<Tag> tagLib;
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
	 * redirects user to addtags.fxml to add tags
	 * @param e when user hits add tags
	 * @throws IOException when tags cannot be found
	 */
	public void addTagButton(ActionEvent e) throws IOException{
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
	 * delete tag from listview that user selects
	 * @param e when user hits delete after choosing tag
	 * @throws IOException
	 */
	public void deleteTagButton(ActionEvent e) throws IOException{
		if (tagLib.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Prompt");
		alert.setContentText("Are you sure you want to delete this tag?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			int index = listView.getSelectionModel().getSelectedIndex();
			boolean inBounds = (index >=0) && (index < tagLib.size()); 
			boolean note=true;
			if (listView.getSelectionModel().getSelectedIndex()==0){
				note=false;
			}
			if(inBounds = true && result.get() == ButtonType.OK) {
				int currentIndex = listView.getSelectionModel().getSelectedIndex();
				tagLib.remove(listView.getSelectionModel().getSelectedIndex());
				if(tagLib.size() > 0) {
					index = currentIndex;
					listView.getSelectionModel().select(index);
				}
			}
		}
		s.getData(photoLib);
		s.saveFile();
	}
	/**
	 * goes back to openalbumpage
	 * @param e when user hits back
	 * @throws IOException when album cannot be found
	 */
	public void back(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/OpenAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		OpenAlbumController ad = loader.getController();
		ad.getList(s,photoLib,username,in);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * loads page by displaying tags of current photo as well as the photo and the caption
	 * @param s saveData class to save data
	 * @param a list of user data
	 * @param username current user
	 * @param in index of user in album
	 * @param display photo currently
	 * @throws FileNotFoundException
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, int in, Photo display) throws FileNotFoundException {
		photoLib=a;
		this.display=display;
		this.username=username;
		this.in=in;
		this.s=s;
		caption.setText(display.caption);
		SimpleDateFormat d=new SimpleDateFormat("MM/dd/yyyy kk:mm");
		dateTime.setText(d.format(display.c.getTime()));
		InputStream stream = new FileInputStream(display.location.getAbsolutePath());
		Image image = new Image(stream);
		imageView.setImage(image);
		tagLib=FXCollections.observableList(display.getLib());
		imageView.setPreserveRatio(true);
		listView.setItems(tagLib);
		listView.getSelectionModel().select(0);	
	}
}
