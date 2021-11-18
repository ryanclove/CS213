package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Album;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
import model.Tag;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Node;
import javafx.scene.Scene;
/**
 * class to display photos and various options to do with them
 * @author jason dao, ryan coslove
 *
 */
public class OpenAlbumController {
	/**
	 * fxml elements on website as well as current username, userdata and stage and scene and savedata
	 */
	@FXML ListView<Photo> listView;
	@FXML ListView<Tag> listView2;
	@FXML Label caption;
	@FXML Label dateTime;
	@FXML ImageView clickedImageView;
	ObservableList<Photo> photograph;
	ObservableList<Album> albumLib;
	ObservableList<PhotoLib> photoLib;
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
	 * opens up filechooser to allow person to add photos
	 * @param e when user presses add photo
	 * @throws IOException when photo cannot be found
	 */
	public void addPhoto(ActionEvent e) throws IOException { 
		FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Open Image");
	    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JPG", "*.jpg"),new FileChooser.ExtensionFilter("PNG", "*.png"),new ExtensionFilter("BMP", "*.bmp"), new ExtensionFilter("GIF", "*.gif"));
	    File temp=fileChooser.showOpenDialog(this.stage);
	    if(temp==null) {
	    	return;
	    }
	    int flag=0;
	    for (int j=0;j<photograph.size();j++) {
			if (temp.getAbsolutePath().equals(photograph.get(j).location.getAbsolutePath())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("Same photo already exists in this album");
				alert.showAndWait();
				return;
			}		
		}
	    for (int i=0;i<albumLib.size();i++) {
			for (int j=0;j<albumLib.get(i).lib.size();j++) {
				if (temp.getAbsolutePath().equals(albumLib.get(i).lib.get(j).location.getAbsolutePath())) {
					photograph.add(albumLib.get(i).lib.get(j));
					flag=1;
					break;
				}
			}
			if (flag==1) {
				break;
			}
		}
	    if (flag==0) {
	    	Photo newPhoto=new Photo(temp);
		    photograph.add(newPhoto);
	    }
	    s.getData(photoLib);
		s.saveFile();
		listView.setItems(photograph);
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal)->{
			try {
				selectPhoto();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error!");
				alert.setContentText("File not found. Try again.");
				alert.showAndWait();
				return;
			}
		});
		listView.getSelectionModel().select(photograph.size()-1);
	}
	/**
	 * displays data of photo selected to display next to it, (date, caption, taglist and image)
	 * @throws FileNotFoundException in case photo cannot be opened
	 */
	public void selectPhoto() throws FileNotFoundException{
		Photo item = listView.getSelectionModel().getSelectedItem();
		if (item!=null) {
			caption.setText(item.caption);
			SimpleDateFormat d=new SimpleDateFormat("MM/dd/yyyy kk:mm");
			dateTime.setText(d.format(item.c.getTime()));
			InputStream stream = new FileInputStream(item.location.getAbsolutePath());
			Image image = new Image(stream);
			clickedImageView.setImage(image);
			clickedImageView.setPreserveRatio(true);
			listView2.setItems(FXCollections.observableList(item.getLib()));
		}
	}
	/**
	 * allows user to change caption with alert dialog
	 * @param e when user presses caption photo
	 * @throws IOException when photo cannot be found
	 */
	public void captionPhoto(ActionEvent e) throws IOException { 
		if (photograph.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to caption");
			alert.showAndWait();
			return;
		}
		int index = listView.getSelectionModel().getSelectedIndex();
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(this.stage); 
		dialog.setTitle("Edit Caption");
		dialog.setHeaderText("Enter caption");
		dialog.setContentText("Enter caption: ");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) { 
			Photo temporary=photograph.get(index);
			temporary.caption=result.get();
			photograph.set(index, temporary);
			caption.setText(result.get());
		}
		s.getData(photoLib);
		s.saveFile();
	    listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			try {
				selectPhoto();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		listView.getSelectionModel().select(index);
	}
	/**
	 * allows user to delete photo selected in listview
	 * @param e when user hits delete
	 * @throws IOException when photo cannot be found
	 */
	public void deletePhoto(ActionEvent e) throws IOException {  
		if (photograph.size()==0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("No items to delete");
			alert.showAndWait();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Prompt");
		alert.setContentText("Are you sure you want to delete this photo?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			int index =listView.getSelectionModel().getSelectedIndex();
			boolean inBounds = (index >=0) && (index < photograph.size()); 
			boolean note=true;
			if (listView.getSelectionModel().getSelectedIndex()==0){
				note=false;
			}
			if(inBounds = true && result.get() == ButtonType.OK) {
				clickedImageView.setImage(null);
				caption.setText(null);
				listView2.setItems(null);
				int currentIndex = listView.getSelectionModel().getSelectedIndex();
				photograph.remove(listView.getSelectionModel().getSelectedIndex());
				if(photograph.size() > 0) {
					index = currentIndex;
					listView.getSelectionModel().select(index);
				}
			}
			if (note==false && photograph.size()!=0) {
				Photo item=photograph.get(0);
				caption.setText(item.caption);
				SimpleDateFormat d=new SimpleDateFormat("MM/dd/yyyy kk:mm");
				dateTime.setText(d.format(item.c.getTime()));
				InputStream stream = new FileInputStream(item.location.getAbsolutePath());
				Image image = new Image(stream);
				clickedImageView.setImage(image);
				clickedImageView.setPreserveRatio(true);
				listView2.setItems(FXCollections.observableList(item.getLib()));
			}
		}
		s.getData(photoLib);
		s.saveFile();
	}
	/**
	 * prompts user to copy to another album if another one exists, goes to copy move fxml page
	 * @param e when user hits copy
	 * @throws IOException when photo cannot be found
	 */
	public void copyPhoto(ActionEvent e) throws IOException{
		if (albumLib.size()<2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need another album to copy to.");
			alert.showAndWait();
			return;
		}
		if (photograph.size()<1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need a photo to copy.");
			alert.showAndWait();
			return;
		}
		Photo item = listView.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/CopyMovePhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		CopyMoveController ad = loader.getController();
		ad.getList(s,photoLib,username,true,albumLib.get(in).name,in, item);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * prompts user to move to another album if another one exists, goes to copy move fxml page
	 * @param e when user hits move
	 * @throws IOException when photo cannot be found
	 */
	public void movePhoto(ActionEvent e) throws IOException{
		if (albumLib.size()<2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need another album to move to.");
			alert.showAndWait();
			return;
		}
		if (photograph.size()<1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need a photo to move.");
			alert.showAndWait();
			return;
		}
		Photo item = listView.getSelectionModel().getSelectedItem();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/CopyMovePhoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		CopyMoveController ad = loader.getController();
		ad.getList(s,photoLib,username,false,albumLib.get(in).name,in,item);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * returns back to albumdispay.fxml
	 * @param e when user hits back button
	 * @throws IOException when albumdisplay cannot be found
	 */
	public void back(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		AlbumDisplayController ad = loader.getController();
		ad.getList(s,photoLib,username);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * redirects user to tagcontroller.fxml for various tag functions
	 * @param e when user hits edit tags
	 * @throws IOException when tags cannot be found
	 */
	public void editTag(ActionEvent e) throws IOException{
		if (photograph.size()<1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need a photo to see slideshow.");
			alert.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Edit.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		TagController ad = loader.getController();
		Photo item = listView.getSelectionModel().getSelectedItem();
		ad.getList(s,photoLib,username,in,item);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * redirects user to a page where slideshow is displayed
	 * @param e when user presses slideshow
	 * @throws IOException when library cannot be found
	 */
	public void displayPhotoButton(ActionEvent e) throws IOException {
		if (photograph.size()<1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error!");
			alert.setContentText("You need a photo to see slideshow.");
			alert.showAndWait();
			return;
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/ExternalPhotoDisplay.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		ExternalPhotoDisplayController ad = loader.getController();
		ad.getList(s,photoLib,username,in,photograph);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * goes to next photo in search results when choosing side bar
	 * @param e when hitting next button
	 * @throws IOException in case photo does not exist
	 */
	public void nextPhotoButton (ActionEvent e) throws IOException{
		int currentIndex = listView.getSelectionModel().getSelectedIndex();
		if (currentIndex+1!=photograph.size()) {
			listView.getSelectionModel().select(currentIndex+1);
		}	
	}
	/**
	 * goes to previous photo in search results when choosing side bar
	 * @param e when hitting previous button
	 * @throws IOException in case photo does not exist
	 */
	public void previousPhotoButton (ActionEvent e) throws IOException{
		int currentIndex = listView.getSelectionModel().getSelectedIndex();
		if (currentIndex-1!=-1) {
			listView.getSelectionModel().select(currentIndex-1);
		}
	}
	/**
	 * loads page by getting list of photos from the username and displaying it with thumbnail and caption
	 * @param s SaveData file
	 * @param a list of current data
	 * @param username current user
	 * @param in index number of user
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, int in) {
		photoLib=a;
		this.username=username;
		this.in=in;
		this.s=s;
		for (int i=0;i<photoLib.size();i++) {
			if (photoLib.get(i).username.equals(username)) {
				albumLib=FXCollections.observableList(photoLib.get(i).lib);
				photograph=FXCollections.observableList(albumLib.get(in).lib);
				break;
			}
		}
		listView.setItems(photograph);
		listView.setCellFactory(new Callback<ListView<Photo>, 
	            ListCell<Photo>>() {
	                @Override 
	                public ListCell<Photo> call(ListView<Photo> list) {
	                    return new PhotoCell();
	                }
	            }
	        );
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
			try {
				selectPhoto();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		listView.getSelectionModel().select(0);	
	}
	
	
	
}
/**
 * class to make a cell to put thumbnail in
 * @author jason dao
 *
 */
class PhotoCell extends ListCell<Photo> {
	/**
	 * /**
	 * creates thumbnail of photo passed in
	 */
    @Override
    public void updateItem(Photo name, boolean empty) {
        super.updateItem(name, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	InputStream stream = null;
			try {
				stream = new FileInputStream(name.location.getAbsolutePath());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image image = new Image(stream);
        	ImageView imageView2 = new ImageView();
			imageView2.setImage(image);
			imageView2.setFitWidth(100);
	        imageView2.setPreserveRatio(true);
            setText(name.toString());
            setGraphic(imageView2);
        }
    }
}




