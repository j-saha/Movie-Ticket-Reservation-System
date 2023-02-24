package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

public class registerController implements Initializable {

    private Stage primaryStage=Main.mainStage;
    @FXML private TextField username;
    @FXML private TextField fullname;
    @FXML private TextField email;
    @FXML private PasswordField password;
    @FXML private TextField phone;
    @FXML private TextField creditCard;
    @FXML private TextField creditCardpin;
    @FXML private Label labelmsg;
    @FXML private AnchorPane paneRegister;

    loginController login=new loginController();
    @FXML
    public void RegisterUser() throws SQLException{
        try {
            if (!username.getText().equals("") && !fullname.getText().equals("") && !password.getText().equals("") && !phone.getText().equals("") && !creditCard.getText().equals("")) {
                try{
                    int a=Integer.parseInt(phone.getText());
                    int b=Integer.parseInt(creditCard.getText());
                String data = "#R#" + username.getText() + "#" + fullname.getText() + "#" + email.getText() + "#" + password.getText() + "#" + phone.getText() + "#" + creditCard.getText();
                Client client = new Client(data);

                Thread.sleep(1000);

                if (Client.signupmessage.equalsIgnoreCase("successful")) {
                    labelmsg.setText("Registration is Successful");
                    goBack();
                } else if (Client.signupmessage.equalsIgnoreCase("ufailed")) {
                    labelmsg.setText("User_name is already taken!");
                } else {
                    labelmsg.setText("Please Enter Valid Input");
                }
            } catch (Exception e){
                    labelmsg.setText("Please Enter Integer in Contact No. & Credit Card");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void goBack() throws Exception{
        login.primaryStage.close();
        loginController.stage.loadNewStage("login","LOGIN",600,400);
    }

    public void backgroundImage() {
        String imageURL="/Images/signUp.jpg";
        loginController.stage.setBackground(paneRegister,imageURL,600,600);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        backgroundImage();
    }

}
