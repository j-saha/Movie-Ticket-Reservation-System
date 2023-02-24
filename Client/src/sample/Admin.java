package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static sample.loginController.client;
import static sample.loginController.person;
import static sample.moviesController.numOfMovies;

public class Admin extends Person {

/*    public boolean updateDetails() throws IOException, SQLException {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        try {
            Statement statement=connection.createStatement();
            String sql = "SELECT * FROM admin WHERE name = '"+person.getUserName()+"' AND password = '"+person.getPassword()+"';";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                person.setUserName(resultSet.getString(1));
                person.setName(resultSet.getString(2));
                person.setEmailId(resultSet.getString(3));
                person.setPassword(resultSet.getString(4));
                person.setPhoneNumber(resultSet.getInt(5));
                return true;
            }
            else return false;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }*/


    public void updateDetails(){
        Client.outToServer2.println("#L#"+person.getUserName()+"##"+person.getPassword()+"##admin");
        Client.outToServer2.flush();
    }

    public void action() throws Exception{
        Client.outToServer2.println("#dateDelete#"+moviesController.movieIDgive);
        Client.outToServer2.flush();
        Thread.sleep(1000);

        Client.outToServer2.println("#deleteMovie#"+String.valueOf(moviesController.movieIDgive)+"#"+String.valueOf(numOfMovies));
        Client.outToServer2.flush();

        moviesController.showMovieDetailsStage.close();


        Client.outToServer2.println("#MOVIES#"+homeController.who);
        Client.outToServer2.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginController.stage.loadNewStage("movies","MOVIES",1200,800);
    }

    public void addMovie(String txtmovieName, String txtdirectorName, String txtCast, String txtRating, String txtPrice, String path, String startDate, String endDate) throws SQLException, IOException {
        loginController.client=new Client("#addMovie#"+txtmovieName+"#"+txtdirectorName+"#"+txtCast+"#"+txtRating+"#"+txtPrice+"#"+path+"#"+startDate+"#"+endDate);
        moviesController.showaddMovies.close();
        Client.outToServer2.println("#MOVIES#"+homeController.who);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginController.stage.loadNewStage("movies","MOVIES",1200,800);

    }

    public void dateDelete() throws SQLException{


    }

}
