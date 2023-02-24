package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Search implements Initializable {
    static Stage searchStage=new Stage();


    @FXML public TextField txtmoviename;
    @FXML public Label result;
    @FXML public Button buyticket;

    String movie;
    @FXML public void  searchButton() throws Exception{
        movie=txtmoviename.getText();
        String data="#SEARCH#"+txtmoviename.getText();
        Client.outToServer2.println(data);
        Thread.sleep(1000);

        if(Client.searchsuccess.equalsIgnoreCase("true")){
            result.setText("MOVIE is available in the hall !!");
            buyticket.setVisible(true);
        }
        else if(Client.searchsuccess.equalsIgnoreCase("false")){
            buyticket.setVisible(false);
            result.setText("This movie was never available in the hall.");
        }
        else {
            buyticket.setVisible(false);
            result.setText("This movie was available in hall in the past.");
        }
    }

    @FXML
    public void action() throws Exception{
       // loginController.client=new Client("#movieDetail#"+Client.movieId);
        Client.outToServer2.println("#movieDetail#"+movie);
        Thread.sleep(500);
        movieDetailsController.mname=movie;
        moviesController.movieIDgive=Integer.parseInt(Client.movieId);
        searchStage.close();
        datatime d=new datatime();
        d.buyticket();
    }

    @FXML
    public void showhistory() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("search.fxml"));
        searchStage.setTitle("SEARCHING MOVIE");
        searchStage.setScene(new Scene(root, 600, 400));
        searchStage.show();
        searchStage.setResizable(false);
    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    @FXML private void back(){
        searchStage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        buyticket.setVisible(false);
        //backgroundImage();
    }
}
