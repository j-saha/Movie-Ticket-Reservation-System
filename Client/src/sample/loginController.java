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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController implements Initializable{
    @FXML private Button btnsignIn;
    @FXML private Button btnsignUp;
    @FXML private Button btnadminLogin;
    @FXML public TextField username;
    @FXML private PasswordField password;
    @FXML private ImageView imgpic;
    @FXML private AnchorPane paneLogin;
    Stage primaryStage = Main.mainStage;

    static public String name;
    public static Person person;
    public static Client client;

    public static myStage stage=new myStage();

    public void backgroundImage() {
        String imageURL="/Images/login.jpg";
        stage.setBackground(paneLogin,imageURL,600,400);
    }

    @FXML public void signIn() throws Exception{
        if(!username.getText().equals("")&&!password.getText().equals("")) {
            homeController.who = "user";
            person = new User();
            client = new Client("#L#" + username.getText() + "##" + password.getText() + "##user");

            Thread.sleep(1000);

            if (Client.loginsuccess)
                loginController.stage.loadNewStage("home", "HOME", 1200, 800);
        }
    }
    @FXML public void signUp() throws Exception{
        loginController.stage.loadNewStage("register","REGISTER",600,600);
    }
    @FXML public void adminLogin() throws Exception{
        loginController.stage.loadNewStage("adminLogin","ADMIN LOGIN",700,500);
    }


    public void initialize(URL location, ResourceBundle resources) {
       backgroundImage();

    }
}
