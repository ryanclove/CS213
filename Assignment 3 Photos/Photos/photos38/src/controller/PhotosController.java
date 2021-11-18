package controller;


import java.io.File;
import java.io.IOException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import javafx.scene.Node;
import javafx.scene.Scene;
/**
 * creates controller for login page
 * @author jason dao, ryan coslove
 *
 */
public class PhotosController {
	/**
	 * photoLib local object, fmxl for login input box, savedata object to save data and stage and scene
	 */
	private ObservableList<PhotoLib> photoLib;
	@FXML TextField one;
	SaveData s;
	private Stage stage;
	private Scene scene;
	/**
	 * sets the stage to the scene connected to this controller by seeing if file exists, if not get create stock account
	 * @param primaryStage  stage of scene
	 * @throws IOException  in case it cannot find scene
	 */
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		this.stage = primaryStage;
		File cFile = new File("Data.dat");
		s=new SaveData();
		if (cFile.isFile()) {
			s.getFile();
			this.photoLib = FXCollections.observableList(s.data);
		}
		else {
			this.photoLib = FXCollections.observableArrayList();
			PhotoLib newuser=new PhotoLib("stock");
			photoLib.add(newuser); 
			Album newalbum=new Album("stock");
			photoLib.get(0).lib.add(newalbum);
			File one=new File(".."+File.separatorChar+"data"+File.separatorChar+"Flowers.jpg");
			Photo one1=new Photo(one);
			photoLib.get(0).lib.get(0).lib.add(one1);
			File two=new File(".."+File.separatorChar+"data"+File.separatorChar+"Field.jpg");
			Photo two2=new Photo(two);
			photoLib.get(0).lib.get(0).lib.add(two2);
			File three=new File(".."+File.separatorChar+"data"+File.separatorChar+"Glasses.jpg");
			Photo three3=new Photo(three);
			photoLib.get(0).lib.get(0).lib.add(three3);
			File four=new File(".."+File.separatorChar+"data"+File.separatorChar+"Hong Kong.jpg");
			Photo four4=new Photo(four);
			photoLib.get(0).lib.get(0).lib.add(four4);
			File five=new File(".."+File.separatorChar+"data"+File.separatorChar+"Mosaic.jpg");
			Photo five5=new Photo(five);
			photoLib.get(0).lib.get(0).lib.add(five5);
			s.getData(photoLib);
			s.saveFile();
		}
	}
	/**
	 * on event someone presses login button, if user enter admin, redirects them to admin fxml and controller, else check to see if the user
	 * exists and if does, redirect to albumdisplay home page fxml and controller
	 * 
	 * if error print alert screen
	 * @param e that triggers when soemone hits login button
	 * @throws IOException in case it cannot find button
	 */
	public void login(ActionEvent e) throws IOException {
		if (one.getText().trim().equals("admin")) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AdminDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			AdminDisplayController ad = loader.getController();
			ad.getList(s,photoLib);
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		else if (one.getText().trim().compareTo("")==0) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("Error Dialog");
			alert1.setHeaderText("Must enter a username!");
			alert1.setContentText("Username must have at least one character");
			alert1.showAndWait();
			return;
		}
		else {
			boolean temp=false;
			for (int i=0;i<photoLib.size();i++) {
				if (photoLib.get(i).username.equals(one.getText().trim())){
					temp=true;
					break;
				}
			}
			if (temp==false) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("Error Dialog");
				alert1.setHeaderText("User not found!");
				alert1.setContentText("Enter a valid username! They are case sensitive.");
				alert1.showAndWait();
				return;
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			AlbumDisplayController ad = loader.getController();
			ad.getList(s,photoLib, one.getText().trim());
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
	/**
	 * if someone comes to this website, pass data locally along, (savedata object and photolib list)
	 * @param s stores photoLib to save
	 * @param a list of users and their photos
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a) {
		this.s=s;
		photoLib=a;
	}
	
}
