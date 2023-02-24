package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class adminLoginController implements Initializable {
    @FXML private Button btnsignIn;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private AnchorPane paneAdmin;

    Stage primaryStage = Main.mainStage;

    public void backgroundImage() {
        String imageURL="/Images/admin.jpg";
        loginController.stage.setBackground(paneAdmin,imageURL,700,500);
    }

    @FXML public void signIn() throws Exception {
        if(!username.getText().equals("")&&!password.getText().equals("")) {
            homeController.who = "admin";
            loginController.person = new Admin();
            loginController.client = new Client("#AL#" + username.getText() + "##" + password.getText() + "##admin");

            Thread.sleep(1000);

            //System.out.println(loginController.person.getEmailId());
            if (Client.loginsuccess)
                loginController.stage.loadNewStage("home", "HOME", 1200, 800);

        }
    }

    @FXML public void back(){
        welcomeController.stage.loadNewStage("login","Log in",600,400);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        backgroundImage();
    }
}
