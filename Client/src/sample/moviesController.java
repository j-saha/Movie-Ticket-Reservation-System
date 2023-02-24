package sample;

import com.sun.javafx.collections.ObservableListWrapper;
//import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class moviesController implements Initializable {

    private Stage primaryStage=Main.mainStage;
    public static Stage showaddMovies = new Stage();
    public static Stage showMovieDetailsStage = new Stage();
    public static Stage upComingStage = new Stage();
    public static int movieIDgive=0, numOfMovies;
    public static String[] movieNames;

    ObservableList<Movies> list= FXCollections.observableArrayList();
    @FXML
    private TableView<Movies> tableView;
    @FXML
    private TableColumn<Movies,String> colmovie;
    @FXML
    private TableColumn<Movies,String> colsdate;
    @FXML
    private TableColumn<Movies,String> colenddate;
    @FXML
    private TableColumn<Movies,String> colincome;
    //@FXML public ImageView logoView;
    @FXML private ImageView movieNum1;
    @FXML private ImageView movieNum2;
    @FXML private ImageView movieNum3;
    @FXML private ImageView movieNum4;
    @FXML private ImageView movieNum5;
    @FXML private ImageView movieNum6;
    @FXML private Button btnhome;
    @FXML private Button btnreset;
    @FXML private Button btnsettings;
    @FXML private Button btnsignout;
    @FXML private Button btnaddMovies;
    @FXML private AnchorPane paneMovies;
    @FXML private Button btnupComing;
    @FXML private Label lblHeading;
    @FXML private Button btnsearch;
    @FXML private TextField txtsearch;

    @FXML public ListView listView;
    String[] out;
    @FXML public void search(){
        if(!txtsearch.getText().equals("")) {
            btnreset.setVisible(true);
            list.clear();
            tableView.getItems().clear();
            String movieName = txtsearch.getText();
            movieNames = homeController.movieAll.split(",");

            numOfMovies = Client.nummovies;

            for (int i = 0; i < numOfMovies; i++) {
                out = movieNames[i].split("~");

                String a = out[0].toLowerCase();
                String b = movieName.toLowerCase();

                if (a.contains(b)) {
                    list.add(new Movies(out[0], out[1], out[2], out[3]));
                }
            }
            tableView.getItems().setAll(list);
        }
    }

    public void initialColoumnValue()
    {
        colmovie.setCellValueFactory(new PropertyValueFactory<>("name"));
        colsdate.setCellValueFactory(new PropertyValueFactory<>("startdate"));
        colenddate.setCellValueFactory(new PropertyValueFactory<>("enddate"));
        colincome.setCellValueFactory(new PropertyValueFactory<>("income"));
        if(homeController.who.equals("user")){
            colincome.setText("TICKET PRICE");
        }
        else{
            colincome.setText("TOTAL INCOME");
        }
    }

    public void reset(){
        tableView.getItems().clear();
        list.clear();
        txtsearch.clear();
        btnreset.setVisible(false);
        set();
    }
    public void set(){
        movieNames=homeController.movieAll.split(",");
        numOfMovies=Client.nummovies;


        for(int i=0;i<numOfMovies;i++){
            out=movieNames[i].split("~");
            list.add(new Movies(out[0],out[1],out[2],out[3]));
        }
        tableView.getItems().setAll(list);

        if(homeController.who.equals("user")) {
        btnaddMovies.setVisible(false);
        }


        if(homeController.who.equals("admin")) {
            btnupComing.setVisible(false);
            lblHeading.setText("*********************ALL MOVIES********************");
        }
    }




    public void backgroundImage() {
        String imageURL="/Images/movies.jpg";
        loginController.stage.setBackground(paneMovies,imageURL,1200,800);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            btnreset.setVisible(false);
            initialColoumnValue();
            set();
            backgroundImage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML public void home() throws Exception {
        loginController.stage.loadNewStage("home","HOME",1200,800);
    }
    @FXML public void settings() throws Exception{
        loginController.stage.loadNewStage("settings","SETTINGS",1200,800);
    }

    @FXML public void signOut() throws Exception{
        loginController.stage.loadNewStage("login","LOGIN",600,400);
    }
/*    @FXML public void movieNum1() throws Exception{showMovieDetails(1);}
    @FXML public void movieNum2() throws Exception{showMovieDetails(2);}
    @FXML public void movieNum3() throws Exception{showMovieDetails(3);}
    @FXML public void movieNum4() throws Exception{showMovieDetails(4);}
    @FXML public void movieNum5() throws Exception{showMovieDetails(5);}
    @FXML public void movieNum6() throws Exception{showMovieDetails(6);}*/

    @FXML void seeDetails() throws Exception{
        if(!tableView.getSelectionModel().isEmpty()) {
            Movies movies = tableView.getSelectionModel().getSelectedItem();

            String movie = movies.getName();
            Client.outToServer2.println("#movieDetail#" + movie);
            Thread.sleep(500);
            Parent root = FXMLLoader.load(getClass().getResource("movieDetails.fxml"));
            showMovieDetailsStage.setTitle("MOVIE DETAILS");
            showMovieDetailsStage.setScene(new Scene(root, 521, 574));
            showMovieDetailsStage.show();
            showMovieDetailsStage.setResizable(false);

            movieIDgive = Client.m_idx;
        }

    }

/*    public void showMovieDetails(int k) throws Exception{
        movieIDgive=k;
        if(movieIDgive<=numOfMovies){
            Parent root = FXMLLoader.load(getClass().getResource("movieDetails.fxml"));
            showMovieDetailsStage.setTitle("MOVIE DETAILS");
            showMovieDetailsStage.setScene(new Scene(root, 521, 574));
            showMovieDetailsStage.show();
        }*/


    @FXML public void addMovies() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("addMoviesDetails.fxml"));
        showaddMovies.setTitle("ADD NEW MOVIE");
        showaddMovies.setScene(new Scene(root, 600, 574));
        showaddMovies.show();
        showaddMovies.setResizable(false);
    }
    @FXML public void upComing() throws Exception{
        Client.outToServer2.println("#upComingMovies");
        Thread.sleep(1000);
        Parent root = FXMLLoader.load(getClass().getResource("upComing.fxml"));
        upComingStage.setTitle("UPCOMING MOVIES");
        upComingStage.setScene(new Scene(root, 800, 500));
        upComingStage.show();
        upComingStage.setResizable(false);
    }

}
