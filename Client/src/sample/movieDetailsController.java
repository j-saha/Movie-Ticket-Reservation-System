package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import static sample.moviesController.numOfMovies;

public class movieDetailsController implements Initializable {
    public static int movieID;
    public static String movieData;

    private   Stage primaryStage=Main.mainStage;

    public static String mname;
    @FXML private ImageView imgPoster;
    @FXML private Label lblRating;
    @FXML private Label lblPrice;
    @FXML private Label lblMoviename;
    @FXML private Label lblDirector;
    @FXML private Label lblCast;
    @FXML private Button btnbuyDel;
    @FXML private AnchorPane paneMovieDetails;

    public void set(){
        String[] movieInfo=movieData.split("#");
       // System.out.println("in moviedate "+data[9]+data[10]);
        movieID=moviesController.movieIDgive;
        mname=movieInfo[3];
        String poster=movieInfo[8];
        Image image=new Image(poster);
        imgPoster.setImage(image);
        lblMoviename.setText("MOVIE NAME: "+movieInfo[3]);
        lblDirector.setText("DIRECTOR NAME: "+movieInfo[4]);
        lblCast.setText("CAST: "+movieInfo[5]);
        lblRating.setText("IMDB RATING: "+movieInfo[6]);
        lblPrice.setText("TICKET PRICE: "+movieInfo[7]);

        if(homeController.who.equals("admin")) btnbuyDel.setText("DELETE MOVIE");
    }



    public void act(){
        try {
            loginController.person.action();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void backgroundImage() {
        String imageURL="/Images/addMovie.jpg";
        loginController.stage.setBackground(paneMovieDetails,imageURL,521,574);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        set();
        backgroundImage();
    }

}
