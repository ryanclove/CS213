package app;

import controller.PhotosController;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

/**
 * creates photos app
 * @author jason dao, ryan coslove
 *
 */
public class Photos extends Application {
	/**
	 * starts app by getting login fxml and photos controller and creates scene and stage
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Creates a FXML loader, and loads the FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		
		// gets the root AnchorPane from the FXML loader
		VBox root = (VBox)loader.load();
		
		// Gets reference to Controller, and then calls it's Start() method
		PhotosController pLibController = loader.getController();
		pLibController.start(primaryStage);
		
		// Creates the scene, passing in the root pane
		Scene scene = new Scene(root);
		
		// Configures the scene
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo App");
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(200);
		primaryStage.setResizable(false);
		
		// Shows the scene
		primaryStage.show();
	}
	/**
	 * main method to run app
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
