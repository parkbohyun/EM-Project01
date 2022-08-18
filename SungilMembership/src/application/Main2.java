package application;
    
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
 
public class Main2 extends Application {
	
	private Stage primaryStage;
	private VBox chatLayout;
	
	// 紐⑤뱺 紐⑤뱢�뿉�꽌 怨듭쑀�븯�뒗 �쟾�뿭 蹂��닔(�궗�슜�옄 �븘�씠�뵒)
	
	public static String global_user_id = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ChatClient.class.getResource("ClientGUI.fxml"));
		loader.setController(new ClientController());
		chatLayout = (VBox) loader.load();

		// Show the scene containing the root layout.
		Scene scene = new Scene(chatLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}        