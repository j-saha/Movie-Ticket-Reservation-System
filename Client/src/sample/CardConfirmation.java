package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CardConfirmation implements Initializable {

    String name=null,movies=null;
    static Stage pinstage=new Stage();

    public static int price;
    @FXML private Label labelmsg;
    @FXML private Label txtamount;
    @FXML private AnchorPane panecard;
    @FXML
    private void backHome(){
        moviesController.showMovieDetailsStage.close();
        pinstage.close();
    }

    public void backgroundImage() {
        String imageURL="/Images/taka.jpg";
        loginController.stage.setBackground(panecard,imageURL,700,400);
    }

    @FXML
    public void showbox() throws IOException {
        buyticketController.buyTicketstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("card_confirmation.fxml"));
        pinstage.setTitle("CONFIRMATION Box");
        pinstage.setScene(new Scene(root, 700, 400));
        pinstage.show();
        pinstage.setResizable(false);
        pinstage.setOnCloseRequest(e->{
            e.consume();
            try {
                close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    @FXML public void confirm() throws Exception, SQLException{
        loginController.person.addMovie();
        writefile();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginController.client=new Client("#payment#"+moviesController.movieIDgive+"#"+buyticketController.numoftickets);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        backgroundImage();
        if(price>=0) {
            String string = "You have to pay " + price + " taka only!!";
            System.out.println(price);

            txtamount.setText(string);
        }
        else{
            txtamount.setText("Please try later.. Sorry!");
        }
    }

    private void writefile() throws Exception{
        String user=loginController.person.getUserName(),full_name=loginController.person.getName(),email=loginController.person.getEmailId();
        String phone=String.valueOf(loginController.person.getPhoneNumber()),movie=movieDetailsController.mname,time=datatime.time,date=datatime.date,hall=String.valueOf(buyticketController.idx);
        hall=String.valueOf(buyticketController.idx);
        System.out.println(movie+time+date+hall);
        PrintWriter writer = new PrintWriter("the-file-name.txt");
        writer.println("                    ONLINE TICKET RESERVATION SYSTEM");
        writer.println();
        writer.println();
        writer.println();
        writer.println("USER NAME : "+user);
        writer.println();
        writer.println("FULL NAME : "+full_name);
        writer.println();
        writer.println("EMAIL ID : "+email);
        writer.println();
        writer.println("PHONE NUMBER : 0"+phone);
        writer.println();
        writer.println("MOVIE NAME : "+movie);
        writer.println();
        writer.println("DATE : "+date);
        writer.println();
        writer.println("SHOW TIME : "+time);
        writer.println();
        writer.println("Hall number : "+hall);
        writer.println();
        writer.println("NUMBER OF TICKETS : "+buyticketController.numoftickets);
        writer.println();
        writer.println("Total COST : "+CardConfirmation.price);
        writer.println();
        writer.println();
        writer.println();
        writer.println("*Please bring the printed copy of this document");
        writer.close();
    }

}
