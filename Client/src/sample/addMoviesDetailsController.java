package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.sun.deploy.cache.Cache.copyFile;
import static sample.loginController.person;

public class addMoviesDetailsController implements Initializable {
    private Stage primaryStage=Main.mainStage;
    @FXML private TextField txtmovieName;
    @FXML private Label result;
    @FXML private TextField txtdirectorName;
    @FXML private TextField txtRating;
    @FXML private TextField txtCast;
    @FXML private TextField txtPrice;
    @FXML private Button btnAdd;
    @FXML private Button btnPoster;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private AnchorPane paneaddMoviesDetails;


    public static boolean isFull;
    String path="/Images/";
    public void selectPoster() throws IOException{
        path="/Images/";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        fileChooser.setInitialDirectory(new File("./src/Images/"));
        File selectedFile=fileChooser.showOpenDialog(null);
        //File destFile=new File("");
        //System.out.println("copy");
        //copyFile(selectedFile,destFile);
        if(selectedFile==null){
                result.setText("Please Select The Poster");
        }
        else {
            path = path.concat((selectedFile).getName());
        }
    }

    public void add() throws Exception {
        if(!txtmovieName.getText().equals("") && !txtdirectorName.getText().equals("") && !txtCast.getText().equals("") && !txtRating.getText().equals("") && !txtPrice.getText().equals("") && !path.equals("/Images/")) {
           try{
               int a=Integer.parseInt(txtPrice.getText());
               int b=Integer.parseInt(txtRating.getText());
            isMovieFull();
            Thread.sleep(1000);
            if (!isFull) {
                dateGenerator();
                System.out.println(isFull);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("In hereeee");

                loginController.person.addMovie(txtmovieName.getText(), txtdirectorName.getText(), txtCast.getText(), txtRating.getText(), txtPrice.getText(), path, String.valueOf(startDate.getValue()), String.valueOf(endDate.getValue()));
                result.setText("Movie is added Successfully!");
            } else {
                result.setText("YOU CAN NOT ADD MORE THAN 6 MOVIES");
                System.out.println("DATE FULL");
            }
        } catch (Exception e){
               result.setText("Please Input Number in Price & Rating");
           }
        }
        else{
            result.setText("Please fill up all fields");
        }
    }

    public void dateGenerator() throws SQLException{
        Client.outToServer2.println("#dateAdder#"+startDate.getValue().toString()+"#"+endDate.getValue().toString());
        Client.outToServer2.flush();

    }
    public void isMovieFull() throws SQLException{
        Client.outToServer2.println("#numOfMovies#"+startDate.getValue().toString()+"#"+endDate.getValue().toString());
        Client.outToServer2.flush();
    }

    public void backgroundImage() {
        String imageURL="/Images/addMovie.jpg";
        loginController.stage.setBackground(paneaddMoviesDetails,imageURL,600,574);
    }

    private void initUI() {
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        startDate.setDayCellFactory(dayCellFactory);
        endDate.setDayCellFactory(dayCellFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        initUI();
        backgroundImage();
    }

}
