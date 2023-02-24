package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static sample.loginController.person;

public class Person {
    private String name;
    private String userName;
    private String emailId;
    private String password;
    private int phoneNumber;
    private String proURL;

    public String getProURL() {
        return proURL;
    }

    public void setProURL(String proURL) {
        this.proURL = proURL;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getCreditCardNumber(){return 0;}
    public void setCreditCardNumber(int creditCardNumber){}
    public String getBoughtMovies(){return null;}
    public void setBoughtMovies(String boughtMovies){}
    public void addMovie() throws IOException, SQLException{}
    public void updateDetails(){}
    public void action() throws Exception{}
    public void addMovie(String txtmovieName, String txtdirectorName, String txtCast, String txtRating, String txtPrice, String path, String startDate, String endDate) throws SQLException, IOException{}
    public void dateDelete() throws SQLException{}


    public void changePassword(String newpass,String oldpass) throws Exception{
        String message="#P#"+newpass+"#"+oldpass+"#"+ getUserName();
        //loginController.client.setSentence(message);
        Client.outToServer2.println(message);
    }

    public void changeProPic(String picUrl) throws Exception{
        String message="#changePropic#"+picUrl+"#"+person.getUserName()+"#"+homeController.who;
        Client.outToServer2.println(message);
        person.setProURL(picUrl);
        System.out.println(picUrl);
    }




}
