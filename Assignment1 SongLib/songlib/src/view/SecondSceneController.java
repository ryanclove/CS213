package view;

/*
 * Jason Dao jnd88
 * Ryan Coslove rmc326
 * 
 */

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class SecondSceneController {
		@FXML TextField one;
		@FXML TextField two;
		@FXML TextField three;
		@FXML TextField four;
		private Stage stage;
		private Scene scene;
		ObservableList<Song> list;
		int mode;
		int index;
		public void start(Stage primaryStage) {
			this.stage = primaryStage;
		}
		
		public void confirm(ActionEvent e) throws IOException {
			
			if (one.getText().trim().compareTo("")==0 || two.getText().trim().compareTo("")==0 ) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error! Incorrect Input");
				alert.setContentText("You must at least include a song name and an artist name!");
				alert.showAndWait();
				return;
			}
			else if(four.getText().trim().compareTo("")!=0 && checkInt(four.getText().trim())==false) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error! Incorrect Input");
				alert.setContentText("Year must be a positive integer!");
				alert.showAndWait();
				return;
			}
			else if(four.getText().trim().compareTo("")!=0 && Integer.parseInt(four.getText().trim())<1) {
				
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error! Incorrect Input");
				alert.setContentText("Year must be a positive integer!");
				alert.showAndWait();
				return;
			}
			else {
				if (mode==1) {
					for (int i=0;i<list.size();i++) {
						if (list.get(i).getName().toLowerCase().compareTo(one.getText().trim().toLowerCase())==0 && list.get(i).getArtist().toLowerCase().compareTo(two.getText().trim().toLowerCase())==0) {
							Alert alert = new Alert(AlertType.ERROR);
		    				alert.setTitle("Error Dialog");
		    				alert.setHeaderText("Error! Incorrect Input");
		    				alert.setContentText("The song name and artist already exists!");
		    				alert.showAndWait();
		    				return;
						}
					}
				}
				else {
					for (int i=0;i<list.size();i++) {
						if (i!=index && list.get(i).getName().toLowerCase().compareTo(one.getText().trim().toLowerCase())==0 && list.get(i).getArtist().toLowerCase().compareTo(two.getText().trim().toLowerCase())==0) {
							Alert alert = new Alert(AlertType.ERROR);
		    				alert.setTitle("Error Dialog");
		    				alert.setHeaderText("Error! Incorrect Input");
		    				alert.setContentText("The song name and artist already exists!");
		    				alert.showAndWait();
		    				return;
						}
					}
				}
				if(mode==1) {
					if(one.getText().trim().contains("|") || two.getText().trim().contains("|") || three.getText().trim().contains("|")) {
						Alert alert1 = new Alert(AlertType.ERROR);
	    				alert1.setTitle("Error Dialog");
	    				alert1.setHeaderText("Error! Incorrect Input");
	    				alert1.setContentText("Song Name/Artist/Album can not contain ' | ' !");
	    				alert1.showAndWait();
	    				return;
					}
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Add Prompt");
					alert.setContentText("Are you sure you want to add this song?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){ 
						if (four.getText().trim().compareTo("")!=0) { 
							list.add(new Song(one.getText().trim(),two.getText().trim(),three.getText().trim(),Integer.parseInt(four.getText())));
						} else { 
							list.add(new Song(one.getText().trim(),two.getText().trim(),three.getText().trim(),0));
								}
					}
						else {
							// user chose cancel or closed dialog
							return;
							}
				}
				
				else {
					if(one.getText().trim().contains("|") || two.getText().trim().contains("|") || three.getText().trim().contains("|")) {
						Alert alert1 = new Alert(AlertType.ERROR);
	    				alert1.setTitle("Error Dialog");
	    				alert1.setHeaderText("Error! Incorrect Input");
	    				alert1.setContentText("Song Name/Artist/Album can not contain ' | ' !");
	    				alert1.showAndWait();
	    				return;
					}
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Edit Prompt");
					alert.setContentText("Are you sure you want to edit this song?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK){ 
						if (four.getText().trim().compareTo("")!=0) {
							list.set(index,new Song(one.getText().trim(),two.getText().trim(),three.getText().trim(),Integer.parseInt(four.getText())));
						} else {
							list.set(index,new Song(one.getText().trim(),two.getText().trim(),three.getText().trim(),0));
								}
							}
						else {
						// user chose cancel or closed dialog
							return;
						}
				}
					
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/songLib.fxml"));
			GridPane root = (GridPane)loader.load();
			SongLibController songLibController = loader.getController();
			songLibController.getList(list,one.getText().trim(),two.getText().trim());
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		}
		
		public void cancel(ActionEvent e) throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/songLib.fxml"));
			GridPane root = (GridPane)loader.load();
			SongLibController songLibController = loader.getController();
			songLibController.getList(list,null,null);
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();	
			if (mode==0) {
				songLibController.songListView.getSelectionModel().select(index);
			}
		}
		public void getList(ObservableList<Song> a,int b) {
			list=a;
			mode=b;
		}
		public void getSong(int a, Song b) {
			index=a;
			one.setText(b.getName());
			two.setText(b.getArtist());
			three.setText(b.getAlbum());
			if (b.getYear()==0) {
				four.setText("");
			}
			else {
				four.setText(String.valueOf(b.getYear()));
			}
		}
		public boolean checkInt(String a) {
			try {
			    int intValue = Integer.parseInt(a);
			    return true;
			} catch (NumberFormatException e) {
			   return false;
			}
		}

}