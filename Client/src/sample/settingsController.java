package sample;

//import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import java.util.ResourceBundle;

public class settingsController implements Initializable {
    private Stage primaryStage=Main.mainStage;
    @FXML private Button btnhome;
    @FXML private Button btnmovies;
    @FXML private Button btnsettings;
    @FXML private Button btnsignout;
    @FXML private Button btnchangepass;
    @FXML private Button btnupdate;
    @FXML private PasswordField txtpass;
    @FXML private PasswordField txtuser;
    @FXML private Label txtresult;
    @FXML private Label labelpic;
    @FXML private Label selectmsg;
    @FXML private AnchorPane paneSettings;

    @FXML public void home() throws Exception {
        loginController.stage.loadNewStage("home","HOME",1200,800);
    }
    @FXML public void movies() throws Exception{
        Client.outToServer2.println("#MOVIES#"+homeController.who);
        Thread.sleep(1000);
        loginController.stage.loadNewStage("movies","MOVIES",1200,800);
    }
    @FXML public void signOut() throws Exception{
        loginController.stage.loadNewStage("login","LOGIN",600,400);
    }
    @FXML public void changepass() throws Exception{
        if (!(txtpass.getText().equals("")|| txtuser.getText().equals(""))) {
            loginController.person.changePassword(txtpass.getText(),txtuser.getText());

            Thread.sleep(1000);
            if(Client.passchangesuccess)
            txtresult.setText("Password change is successful!");
            else
                txtresult.setText("You have Entered Wrong Current PassWord!");
        }
        else{
            txtresult.setText("PLEASE ENTER VALID INPUT");
        }
        txtuser.clear();
        txtpass.clear();
    }

    public void backgroundImage() {
        String imageURL="/Images/settings.png";
        loginController.stage.setBackground(paneSettings,imageURL,1200,800);
    }

    String path="/Images/";
    File selectedFile;
    Random rand=new Random();
    int n = rand.nextInt(50) + 1;


    @FXML public void selectProPic() throws IOException {
        path="/Images/";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("SELECT ANY IMAGE");
        fileChooser.setInitialDirectory(new File("./src/Images/"));
        selectedFile=fileChooser.showOpenDialog(null);
       // System.out.println(selectedFile);
    /*    if(!(selectedFile.getName().equalsIgnoreCase(null))) {
            path = path.concat((selectedFile).getName());
        }*/

        //System.out.println(selectedFile.getName()+" "+selectedFile.exists()+" "+selectedFile.canRead());
        if(selectedFile==null){
            selectmsg.setText("You haven't selected any image!");
            btnupdate.setVisible(false);
        }
        else{
            String path2=path+loginController.person.getUserName()+".jpg";
            path=path+loginController.person.getUserName()+n+".jpg";
            selectmsg.setText("You have selected profile pic successfully.");
            btnupdate.setVisible(true);
        }
    }

    @FXML public void proPicUpdate() throws Exception{
        try {
            createProPic(selectedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginController.person.changeProPic(path);
        selectmsg.setVisible(false);
        labelpic.setText("Profile pic updated..");
        loginController.person.setProURL(path);
        //System.out.println(loginController.person.getProURL());
        btnupdate.setVisible(false);
        path="/Images/";

    }

    public void createProPic(File file) throws Exception{

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos=new FileOutputStream(System.getProperty("user.dir")+"\\src\\Images\\"+loginController.person.getUserName()+n+".jpg");

        //FileOutputStream fos= new FileOutputStream("\\src\\Images\\"+loginController.person.getUserName()+".jpg");

        int size= 0;
        size = fis.available();
        for(int i=0; i<size;i++){
           // System.out.println("creating");
            fos.write(fis.read());
        }

        fis.close();
        fos.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        backgroundImage();
        btnupdate.setVisible(false);
    }
}
