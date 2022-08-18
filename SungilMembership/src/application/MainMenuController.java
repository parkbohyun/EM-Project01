package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private VBox chatLayout;
	private Stage primaryStage;
	
	public static String global_user_id = null;

	public void switchToMain(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void switchTosigan(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("sigan.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void switchTocalcu(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("calcu.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public void switchTochat(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ChatClient.class.getResource("ClientGUI.fxml"));
		loader.setController(new ClientController());
		chatLayout = (VBox) loader.load();

		// Show the scene containing the root layout.
		Scene scene = new Scene(chatLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}