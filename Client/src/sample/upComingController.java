package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class upComingController implements Initializable {

    ObservableList<UpComingMovies> list= FXCollections.observableArrayList();
    @FXML
    private TableView<UpComingMovies> tableView;
    @FXML
    private TableColumn<UpComingMovies,String> colmovie;
    @FXML
    private TableColumn<UpComingMovies,String> colsdate;
    @FXML
    private TableColumn<UpComingMovies,String> colenddate;

    public static String upMovieNames;
    public void initialColoumnValue()
    {
        colmovie.setCellValueFactory(new PropertyValueFactory<>("name"));
        colsdate.setCellValueFactory(new PropertyValueFactory<>("startdate"));
        colenddate.setCellValueFactory(new PropertyValueFactory<>("enddate"));
    }


    public void set(){
        tableView.getItems().clear();

        String[] movieNames=upMovieNames.split(",");
        int numOfMovies=Client.num;
        for(int i=0;i<numOfMovies;i++) {
            String[] out=movieNames[i].split("~");
            list.add(new UpComingMovies(out[0],out[1],out[2]));
        }
        tableView.getItems().setAll(list);
    }
    @FXML public void back(){
        moviesController.upComingStage.close();
    }

    public void initialize(URL location, ResourceBundle resources){
        initialColoumnValue();
        set();

    }
}

