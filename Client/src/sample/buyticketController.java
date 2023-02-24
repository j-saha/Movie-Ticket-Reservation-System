package sample;

import javafx.fxml.FXML;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.io.IOException;

import static sample.loginController.person;


public  class buyticketController implements Initializable {
    @FXML private Button btnyes;
    @FXML private Button btnno;
    @FXML private Label labelmsg;
    @FXML private Label ticketalert;
    @FXML private TextField numOftickets;
    @FXML private AnchorPane buyticket;

    public static String h;
    public static int idx;
    public static boolean isAvailable;
    public static int numoftickets,seats;
    String name=null,movies=null;
    public static Stage buyTicketstage=new Stage();

    public void backgroundImage() {
        String imageURL="/Images/ticket.jpg";
        loginController.stage.setBackground(buyticket,imageURL,700,400);
    }

    @FXML private void confirmTicket() throws Exception{
        if(!numOftickets.getText().equals("")) {
            try{
            numoftickets = Integer.parseInt(numOftickets.getText());
            if (numoftickets <= 15 && numoftickets > 0) {
                checkavailableTicket(numoftickets);
                Thread.sleep(500);
                if (isAvailable) {
                    CardConfirmation cardConfirmation = new CardConfirmation();
                    cardConfirmation.showbox();
                } else {
                    ticketalert.setText("Hall is full!!!!");
                }
            } else if (numoftickets > 15) {
                ticketalert.setText("You can't buy more than 15 tickets!");
            } else {
                ticketalert.setText("Please select a valid number from 1 to 15");
            }
        }   catch (Exception e){
                ticketalert.setText("Please select a valid number from 1 to 15");
            }
        }
    }
    @FXML private void cancelTicket(){
        labelmsg.setText("You are canceling the ticket!");
        buyTicketstage.close();
        System.out.println("you are canceling the ticket");
    }

    @FXML
    public void buyticket() throws IOException {
        hallController.hallstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("ticket.fxml"));
        buyTicketstage.setTitle("BUY TICKET");
        buyTicketstage.setScene(new Scene(root, 700, 400));
        buyTicketstage.show();
        buyTicketstage.setResizable(false);
    }

    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }

    private void checkavailableTicket(int x) throws Exception{
        h=hallController.hall;
        if(h.equalsIgnoreCase("hall1_seat")){idx=1;}
        else if(h.equalsIgnoreCase("hall2_seat")){idx=2;}
        else idx=3;
        loginController.client=new Client("#checkAvailavleTickets#"+h+"#"+datatime.date+"#"+x);
      //  loginController.stage.loadNewStage("movies","MOVIES",1200,800);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backgroundImage();

    }
}
