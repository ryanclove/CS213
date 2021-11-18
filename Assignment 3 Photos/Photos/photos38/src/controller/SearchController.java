package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
 * controller to load search results and create a new album from results
 * @author jason dao, ryan coslove
 *
 */
public class SearchController {
	/**
	 * listview of tags of pictures, listview of results, caption and datetime of pictures, image of search results, as well as current 
	 * username and list of local data user is using(list of photo) and then savedata object and current stage and scene
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
	 * goes back to album.fxml page
	 * @param e when user hits back button
	 * @throws IOException in case search results pictures cannot be found
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
	 * adds album from list of results, checks for error, then goes back to album.fxml page
	 * @param e triggers when user presses create album from results
	 * @throws IOException in case it cannot find photo
	 */
	public void addAlbum(ActionEvent e) throws IOException {  
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(this.stage); 
		dialog.setTitle("Create Album");
		dialog.setHeaderText("Enter name for this album");
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
			Album newalbum=new Album(result.get().trim());
			newalbum.lib=new ArrayList<Photo>(photograph);
			for (int i=0;i<photoLib.size();i++) {
				if (photoLib.get(i).username.equals(username)) {
					for (int j=0;j<photoLib.get(i).lib.size();j++) {
						if (photoLib.get(i).lib.get(j).name.equalsIgnoreCase(newalbum.name)) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error Dialog");
							alert.setHeaderText("Error!");
							alert.setContentText("Album with same name(case insensitive) already exists");
							alert.showAndWait();
							return;
						}
					}
					photoLib.get(i).lib.add(newalbum);
				}
			}
		}
		s.getData(photoLib);
		s.saveFile();
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
	 * loads up page when visiting, creates listview of results , stores username, saveData as well as arraylist of results
	 * @param s savedata object to save data
	 * @param a local data user is using
	 * @param username  string of current user
	 * @param b  list of results
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, ArrayList<Photo>b) {
		photoLib=a;
		this.username=username;
		this.s=s;
		photograph=FXCollections.observableList(b);
		listView.setItems(photograph);
		listView.setCellFactory(new Callback<ListView<Photo>, 
	            ListCell<Photo>>() {
	                @Override 
	                public ListCell<Photo> call(ListView<Photo> list) {
	                    return new PhotoCell2();
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
 * class to add cell to display thumbnail
 * @author jason dao
 *
 */
class PhotoCell2 extends ListCell<Photo> {
	/**
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





