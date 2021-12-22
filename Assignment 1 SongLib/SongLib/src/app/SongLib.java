package app;

/*
 * Jason Dao jnd88
 * Ryan Coslove rmc326
 * 
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import view.SongLibController;

public class SongLib extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Creates a FXML loader, and loads the FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/songLib.fxml"));
		
		// gets the root GridPane from the FXML loader
		GridPane root = (GridPane)loader.load();
		
		// Gets reference to Controller, and then calls it's Start() method
		SongLibController songLibController = loader.getController();
		songLibController.start(primaryStage);
		
		// Creates the scene, passing in the root pane
		Scene scene = new Scene(root);
		
		// Configures the scene
		primaryStage.setScene(scene);
		primaryStage.setTitle("SongLib");
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(200);
		primaryStage.setResizable(false);
		
		// Shows the scene
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}