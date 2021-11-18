package controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;



import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import model.Photo;
import model.PhotoLib;
import model.SaveData;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * displays slideshow of pictures in album
 * @author jason dao, ryan coslove
 *
 */

public class ExternalPhotoDisplayController {
	/**
	 * fxml of photoslide as well as info about current user and to save data and the scene and stage
	 */
	@FXML Label caption;
	@FXML Label dateTime;
	@FXML ImageView imageView;
	ObservableList<PhotoLib> photoLib;
	ObservableList<Photo> Lib;
	String username;
	int in;
	int curr;
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
	 * goes to next photo in album
	 * @param e when user hits >
	 * @throws IOException
	 */
	public void forward(ActionEvent e) throws IOException{
		if (curr!=Lib.size()-1) {
			InputStream stream = new FileInputStream(Lib.get(curr+1).location.getAbsolutePath());
			Image image = new Image(stream);
			imageView.setImage(image);
			imageView.setPreserveRatio(true);
			curr+=1;
		}
	}
	/**
	 * goes to previous photo in album
	 * @param e when user hits <
	 * @throws IOException
	 */
	public void backward(ActionEvent e) throws IOException{
		if (curr!=0) {
			InputStream stream = new FileInputStream(Lib.get(curr-1).location.getAbsolutePath());
			Image image = new Image(stream);
			imageView.setImage(image);
			imageView.setPreserveRatio(true);
			curr-=1;
		}
	}
	/**
	 * goes back to openalbum.fxl
	 * @param e when user hits back
	 * @throws IOException
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
	 * loads page to set first photo of album chosen and then allows user to see other pictures in album
	 * @param s data to save 
	 * @param a current user data
	 * @param username current username
	 * @param in user index
	 * @param b photos from user album to see
	 * @throws FileNotFoundException
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, int in, ObservableList<Photo>b) throws FileNotFoundException {
		photoLib=a;
		this.username=username;
		this.in=in;
		this.s=s;
		Lib=b;
		InputStream stream = new FileInputStream(b.get(0).location.getAbsolutePath());
		Image image = new Image(stream);
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		curr=0;
	}
	
	
	
}



