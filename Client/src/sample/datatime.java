package sample;

//import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class datatime implements Initializable {
    public static Stage buyTicketstage=new Stage();
    public static String date,time,startdate,enddate;
    @FXML private Button btnconfirm;
    @FXML private Button btnback;
    @FXML DatePicker datePicker;
    @FXML private Label labelmsg;
    @FXML private AnchorPane panedate;
    @FXML ChoiceBox<String> selectTime;
    ObservableList list= FXCollections.observableArrayList();

    public void backgroundImage() {
        String imageURL="/Images/date.jpg";
        loginController.stage.setBackground(panedate,imageURL,700,400);
    }

    @FXML
    public void buyticket() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("data_time.fxml"));
        moviesController.showMovieDetailsStage.close();
        buyTicketstage.setTitle("SELECT DATE & TIME");
        buyTicketstage.setScene(new Scene(root, 700, 400));
        buyTicketstage.show();
        buyTicketstage.setResizable(false);
    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    @FXML public void confirm() throws  Exception{
        date=String.valueOf(datePicker.getValue());
        time=String.valueOf(selectTime.getValue());
        System.out.println(date+" "+time);
        if((!date.equals("null")) && (!time.equals("null"))) {
            hallController halls=new hallController();
            halls.selecthall();
        }
        else{
            if(date.equals("null")){
                labelmsg.setText("Please select the date.");
            }
            else if(time.equals("null")){
                labelmsg.setText("Please select the time.");
            }
            else{
                labelmsg.setText("Please Select the date & time!");
            }
        }
    }
    @FXML public void back() throws Exception{
        buyTicketstage.close();
    }
    private void initUI() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(enddate, formatter);
        LocalDate localDate1= LocalDate.parse(startdate,formatter);
        datePicker.setValue(LocalDate.now());

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                String x=String.valueOf(datePicker.getValue());

                             /*   String []out=x.split("-");
                                  int  date1=Integer.parseInt(out[2]);*/
                                if(item.isAfter(localDate)){
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (item.isBefore(localDate1)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                if (item.isBefore(LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
    }
     private void loaddata(){
        list.removeAll(list);
        String a="10:30 AM";
        String b="01:30 PM";
        String c="04:00 PM";
        String d="07:30 PM";
        list.addAll(a,b,c,d);
        selectTime.getItems().addAll(list);
    }

    private static void moviedate() throws Exception{
        String[] data=movieDetailsController.movieData.split("#");
        //System.out.println("in moviedate "+data[9]+data[10]);
        startdate=data[9];
        enddate=data[10];
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            moviedate();
        } catch (Exception e){
            e.printStackTrace();
        }
        backgroundImage();
        initUI();
        loaddata();
    }

}
