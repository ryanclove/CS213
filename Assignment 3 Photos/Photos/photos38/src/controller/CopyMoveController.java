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
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.PhotoLib;
import model.SaveData;
/**
 * controller to choose album to copy/move to
 * @author jason dao, ryan coslove
 *
 */
public class CopyMoveController {
	/**
	 * user data as well as stage and scene and savedata and listview for list of other albums
	 */
	@FXML ListView<String> listView;
	String name;
	boolean copymove;
	ObservableList<String> albumLib2;
	ObservableList<Album> albumLib;
	ObservableList<PhotoLib> photoLib;
	String username;
	int in;
	Photo selected;
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
	 * load user data as well as load other albums to move/copy to
	 * @param s SaveData object
	 * @param a user data
	 * @param username of user
	 * @param copymove boolean to say to move or copy
	 * @param current current album selected to move/copy from
	 * @param in index of current user
	 * @param selected Photo to move/copy
	 */
	public void getList(SaveData s,ObservableList<PhotoLib>a, String username, boolean copymove, String current, int in, Photo selected) {
		photoLib=a;
		this.copymove=copymove;
		this.in=in;
		this.username=username;
		this.selected=selected;
		this.s=s;
		name=current;
		for (int i=0;i<photoLib.size();i++) {
			if (photoLib.get(i).username.equals(username)) {
				albumLib=FXCollections.observableList(photoLib.get(i).lib);
				break;
			}
		}
		this.albumLib2 = FXCollections.observableArrayList();
		for (int i=0;i<albumLib.size();i++) {
			if (albumLib.get(i).name.equals(current)) {
				continue;
			}
			else {
				albumLib2.add(albumLib.get(i).name);
			}
		}
		listView.setItems(albumLib2);
		listView.getSelectionModel().select(0);	
	}
	/**
	 * after user selects album, checks to see if photo already exists, and then copies/moves photo and then goes back to openalbum.fxml
	 * @param e after user hits confirm
	 * @throws IOException
	 */
	public void confirm(ActionEvent e) throws IOException { 
		String item = listView.getSelectionModel().getSelectedItem();
		for (int i=0;i<albumLib.size();i++) {
			if (albumLib.get(i).name.equals(item)) {
				for (int j=0;j<albumLib.get(i).lib.size();j++) {
					if (selected.location.getAbsolutePath().equals(albumLib.get(i).lib.get(j).location.getAbsolutePath())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Error!");
						alert.setContentText("Same photo already exists in other album");
						alert.showAndWait();
						return;
					}	
				}
				albumLib.get(i).lib.add(selected);
				if (copymove==false) {
					albumLib.get(in).lib.remove(selected);
				}
			}
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/OpenAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		OpenAlbumController ad = loader.getController();
		ad.getList(s,photoLib,username, in);
		s.getData(photoLib);
		s.saveFile();
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	/**
	 * goes back to openalbumfxml
	 * @param e after user hits cancel
	 * @throws IOException
	 */
	public void cancel(ActionEvent e) throws IOException  {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/OpenAlbum.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		OpenAlbumController ad = loader.getController();
		ad.getList(s,photoLib,username, in);
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
