package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class SideBarController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }

    @FXML
    private void homeClick(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    private void page01Click(MouseEvent event) {
        loadPage("page01");
    }

    @FXML
    private void page02Click(MouseEvent event) {
        loadPage("page02");
    }

    @FXML
    private void page03Click(MouseEvent event) {
        loadPage("page03");
    }

    private void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
            bp.setCenter(root);
        } catch (IOException ex) {
            Logger.getLogger(SideBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
