package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static sample.loginController.person;

public class User extends Person {
    private int creditCardNumber;
    private String boughtMovies;

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getBoughtMovies() {
        return boughtMovies;
    }

    public void setBoughtMovies(String boughtMovies) {
        this.boughtMovies = boughtMovies;
    }


    public void addMovie() throws IOException, SQLException {

        loginController.client=new Client("#addMovieToUser#"+person.getBoughtMovies()+"#"+String.valueOf(moviesController.movieIDgive)+"#"+String.valueOf(datatime.date)+"#"+String.valueOf(datatime.time)+"#"+person.getUserName()+"#"+hallController.hall+"#"+buyticketController.numoftickets);
        //person.updateDetails();
        FinalBox finalBox = new FinalBox();
        finalBox.showfinalbox();

    }


    public void updateDetails(){
        loginController.client=new Client("#L#"+person.getUserName()+"##"+person.getPassword()+"##user");
    }


    public void action() throws Exception{
        datatime dtime=new datatime();
        dtime.buyticket();
    }


}


