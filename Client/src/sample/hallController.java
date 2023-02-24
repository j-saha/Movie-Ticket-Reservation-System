package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class hallController implements Initializable {
    public static String hall;
    public static Stage hallstage=new Stage();
    @FXML private AnchorPane panehall;

    private void backgroundImage() {
        String imageURL="/Images/home.jpg";
        loginController.stage.setBackground(panehall,imageURL,500,400);
    }
    @FXML private void hall1() throws Exception{
        hall="hall1_seat";
        buyticketController bticket = new buyticketController();
        bticket.buyticket();
    }
    @FXML private void hall2() throws Exception{
        hall="hall2_seat";
        buyticketController bticket = new buyticketController();
        bticket.buyticket();
    }
    @FXML private void hall3() throws Exception{
        hall="hall3_seat";
        buyticketController bticket = new buyticketController();
        bticket.buyticket();
    }
    @FXML
    public void selecthall() throws IOException {
        datatime.buyTicketstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("hall.fxml"));
        hallstage.setTitle("SELECT HALL ROOM");
        hallstage.setScene(new Scene(root, 500, 400));
        hallstage.show();
        hallstage.setResizable(false);
    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backgroundImage();

    }
}
