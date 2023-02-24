package sample;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class homeController implements Initializable {
    public static String who;
    public static String movieNames;
    public static String movieAll;

    @FXML private Button btnhome;
    @FXML private Button btnhistory;
    @FXML private Button btnsearch;
    @FXML private Button btnsettings;
    @FXML private Button btnsignout;
    @FXML private ImageView proPic;
    @FXML private Label username ;
    @FXML private Label fullname;
    @FXML private Label Email;
    @FXML private Label Phone;
    @FXML private Label labelBalance;
    @FXML private AnchorPane paneHome;

    @FXML private Label lblup ;
    @FXML private Label lblmoviename;
    @FXML private Label lblmoviedate;
    @FXML private Label lblmovietime;
    @FXML private Label lblnooftickets;

    @FXML private Label lblmovieName;
    @FXML private Label lblmdate;
    @FXML private Label lblstime;
    @FXML private Label lblntckt;

    String user_name,full_name,email,number,movies;
    private Stage primaryStage=Main.mainStage;

    public void img() throws Exception{
       // System.out.println(Client.proPicURL);
       //String pro=Client.proPicURL;
        String pro="POSTER.jpg";
        pro="./Images/"+pro;
        Image image=new Image(pro);
        proPic.setImage(new Image(pro));
       backgroundImage();
    }

    public void backgroundImage() {
        String imageURL="/Images/home.jpg";
        loginController.stage.setBackground(paneHome,imageURL,1200,800);
    }


    @FXML public void home() throws Exception {
        allshow();
        System.out.println(loginController.person.getUserName());
        loginController.stage.loadNewStage("home","HOME",1200,800);
    }
    @FXML public void movies() throws Exception{
        Client.outToServer2.println("#MOVIES#"+homeController.who);
        Thread.sleep(1000);
        loginController.stage.loadNewStage("movies","MOVIES",1200,800);
    }

    @FXML public void settings() throws Exception{
        loginController.stage.loadNewStage("settings","SETTINGS",1200,800);
    }

    @FXML public void signOut() throws Exception{
        loginController.stage.loadNewStage("login","LOGIN",600,400);
    }

    @FXML public void showhistory() throws Exception{
        if(who.equals("user")) {
            History history = new History();
            history.showhistory();
        }
    }

    @FXML public void searchbutton() throws Exception{
            Search search=new Search();
            search.showhistory();
    }

    private void allshow() throws Exception{
        username.setText(loginController.person.getUserName());
        fullname.setText(loginController.person.getName());
        Email.setText(loginController.person.getEmailId());
        Phone.setText(String.valueOf(loginController.person.getPhoneNumber()));
        movieNames=loginController.person.getBoughtMovies();
        System.out.println("In allshow");

        //proPic.setImage(new Image(loginController.person.getProURL()));

        File img=new File(System.getProperty("user.dir")+"\\src\\"+loginController.person.getProURL());
        //File img = new File(loginController.person.getProURL());
        InputStream isImage = new FileInputStream(img);
        proPic.setImage(new Image(isImage));

        lblup.setText("NEXT MOVIE YOU ARE WATCHING:");
        if(homeController.who.equals("user")) {
            boolean t = nextmovie();
            if (!t) {
                lblmovietime.setVisible(false);
                lblup.setVisible(false);
                lblnooftickets.setVisible(false);
                lblmoviedate.setVisible(false);
                lblmoviename.setVisible(false);

                lblmovieName.setVisible(false);
                lblstime.setVisible(false);
                lblmdate.setVisible(false);
                lblntckt.setVisible(false);
            }
        }
        else{
            lblmovietime.setVisible(false);
            lblup.setVisible(false);
            lblnooftickets.setVisible(false);
            lblmoviedate.setVisible(false);
            lblmoviename.setVisible(false);

            lblmovieName.setVisible(false);
            lblstime.setVisible(false);
            lblmdate.setVisible(false);
            lblntckt.setVisible(false);
        }
    }

    public boolean nextmovie(){
        boolean is_found=false;
        LocalDate today=LocalDate.now();
        LocalDate[] localDates=new LocalDate[100];
        String[] out=loginController.person.getBoughtMovies().split(",");
        int k=0;
        for(int i=0;i<out.length;i++) {
            if (!out[i].equals("")) {
                String[] data = out[i].split("~");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate moviedate = LocalDate.parse(data[1], formatter);
                localDates[k]=moviedate;
                k++;
            }
        }

        int o=0,flag=0;
        LocalDate foundDate=null;

        while(true){
            if(k==0) break;
            int i;
            for( i=0;i<=k;i++){
                if(today.equals(localDates[i])){
                    foundDate=localDates[i];
                    flag=1;
                    is_found=true;
                    break;
                }
            }
            if(flag==1) break;
            today=today.plusDays(1);

        }


        for(int i=0;i<out.length;i++) {
            if (!out[i].equals("")) {
                String[] data = out[i].split("~");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate moviedate = LocalDate.parse(data[1], formatter);
                if(foundDate.equals(moviedate)){
                    lblmoviename.setText(data[0]);
                    lblmoviedate.setText(data[1]);
                    lblmovietime.setText(data[2]);
                    lblnooftickets.setText(data[3]);
                }
            }
        }
        return is_found;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            btnsearch.setVisible(false);
            backgroundImage();
        } catch (Exception e){
            e.printStackTrace();
        }
        if(who.equalsIgnoreCase("admin")){
            btnhistory.setVisible(false);
        }
        try {
            allshow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
