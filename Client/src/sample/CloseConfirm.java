package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CloseConfirm {

    static Stage closestage=new Stage();


    @FXML public void yesbutton(){
        Search.searchStage.close();
        History.historyStage.close();
        hallController.hallstage.close();
        datatime.buyTicketstage.close();
        CardConfirmation.pinstage.close();
        buyticketController.buyTicketstage.close();
        FinalBox.finalstage.close();
        Main.mainStage.close();
        closestage.close();
        System.exit(1);
    }
    @FXML public void nobutton(){
        closestage.close();
    }

    @FXML
    public void showclosebox() throws IOException {
        CardConfirmation.pinstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("CloseConfirm.fxml"));
        closestage.setTitle("Close Box");
        closestage.setScene(new Scene(root, 400, 200));
        closestage.show();
        closestage.setResizable(false);
    }
}
