package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.IOException;

public class myStage {

    public void loadNewStage (String fxml, String title, int width, int height){
        //Parent root = null;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml+".fxml"));
            Main.mainStage.setTitle(title);
            Main.mainStage.setScene(new Scene(root, width, height));
            Main.mainStage.show();
            Main.mainStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void setBackground(AnchorPane pane, String imageURL, int width, int height){
        BackgroundImage myBI= new BackgroundImage(new Image(imageURL,width,height,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        pane.setBackground(new Background(myBI));
    }
}
