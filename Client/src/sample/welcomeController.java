package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class welcomeController implements Initializable {
    Stage primaryStage = Main.mainStage;
    public static myStage stage=new myStage();
    @FXML private ImageView welcomepic;

    public void img() {
        welcomepic.setImage(new Image("/Images/front.jpg"));
    }

    @FXML void enter(){
        welcomeController.stage.loadNewStage("login","Log in",600,400);
    }

    public void initialize(URL location, ResourceBundle resources) {
        img();
    }
}
