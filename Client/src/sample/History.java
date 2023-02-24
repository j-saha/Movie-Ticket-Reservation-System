package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class History implements Initializable {
    static Stage historyStage=new Stage();
    private String movienames=loginController.person.getBoughtMovies();
    ObservableList<moviehistory> list= FXCollections.observableArrayList();
    @FXML
    private TableView<moviehistory> tableView;
    @FXML
    private TableColumn<moviehistory,String> colid;
    @FXML
    private TableColumn<moviehistory,String> colname;
    @FXML
    private TableColumn<moviehistory,String> coldate;
    @FXML
    private TableColumn<moviehistory,String> coltime;
    @FXML
    private TableColumn<moviehistory,String> colnum;
    @FXML
    public void showhistory() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("history.fxml"));
        historyStage.setTitle("HISTORY");
        historyStage.setScene(new Scene(root, 700, 400));
        historyStage.show();
        historyStage.setResizable(false);
    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    public void initialColoumnValue()
    {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colnum.setCellValueFactory(new PropertyValueFactory<>("number"));
    }

    public void allshow(){
        int k=0;
        String[] out=loginController.person.getBoughtMovies().split(",");
        for(int i=0;i<out.length;i++) {
            if (!out[i].equals("")) {
                k++;
                String[] data = out[i].split("~");
                list.add(new moviehistory(String.valueOf(k), data[0], data[1], data[2], data[3]));
            }
        }
        tableView.getItems().setAll(list);

    }
    @FXML private void back(){
        historyStage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            initialColoumnValue();
            allshow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
