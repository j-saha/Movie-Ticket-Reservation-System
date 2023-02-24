package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class FinalBox implements Initializable {

    @FXML private AnchorPane panefinal;
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Label msg;
    static Stage finalstage=new Stage();


    @FXML public void download(){
        progressIndicator.setVisible(true);
        Task<Void> task = new DownloadTask();
        progressIndicator.progressProperty().bind(task.progressProperty());

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    private class DownloadTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {

            File file = new File("the-file-name.txt");
            long fileLength = file.length();
            URLConnection connection=new URL(String.valueOf(file.toURL())).openConnection();

            try (InputStream is = connection.getInputStream();
                 OutputStream os = Files.newOutputStream(Paths.get("D:\\TICKET-of-"+movieDetailsController.mname +"-movie"+ ".txt"))) {
                File f=new File("D:\\TICKET-of-"+movieDetailsController.mname +"-movie"+ ".txt");

                long nread = 0L;
                byte[] buf = new byte[8192];
                int n;
                while ((n = is.read(buf)) > 0) {
                    os.write(buf, 0, n);
                    nread += n;
                    updateProgress(nread, fileLength);
                }

                convertTextToPDF(f);

            }

            return null;
        }

        @Override
        protected void failed() {
            msg.setText("DOWNLOAD FAILED");
            System.out.println("failed");
        }

        @Override
        protected void succeeded() {
            msg.setText("DOWNLOAD SUCCESSFUL");
            System.out.println("downloaded");
        }
    }

    public static boolean convertTextToPDF(File file) throws Exception {

        BufferedReader br = null;

        try {

            Document pdfDoc = new Document(PageSize.A4);
            String output_file = file.getName().replace(".txt", ".pdf");
            System.out.println("## writing to: " + output_file);
            PdfWriter.getInstance(pdfDoc, new FileOutputStream(output_file)).setPdfVersion(PdfWriter.VERSION_1_7);;

            pdfDoc.open();

            Font myfont = new Font();
            myfont.setStyle(Font.BOLDITALIC);
            myfont.setSize(12);

            pdfDoc.add(new Paragraph("\n"));

            if (file.exists()) {

                br = new BufferedReader(new FileReader(file));
                String strLine;

                while ((strLine = br.readLine()) != null) {
                    Paragraph para = new Paragraph(strLine + "\n", myfont);
                    para.setAlignment(Element.ALIGN_JUSTIFIED);
                    pdfDoc.add(para);
                }
            } else {
                System.out.println("no such file exists!");
                return false;
            }
            pdfDoc.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                br.close();
        }

        return true;
    }

    @FXML private void backHome(){
        moviesController.showMovieDetailsStage.close();
        finalstage.close();
    }
    public void backgroundImage() {
        String imageURL="/Images/congratsfinal.jpg";
        loginController.stage.setBackground(panefinal,imageURL,500,600);
    }
    @FXML
    public void showfinalbox() throws IOException {
        CardConfirmation.pinstage.close();
        Parent root = FXMLLoader.load(getClass().getResource("finalbox.fxml"));
        finalstage.setTitle("Final Box");
        finalstage.setScene(new Scene(root, 500, 600));
        finalstage.show();
        finalstage.setResizable(false);
    }
    private void close() throws Exception{
        CloseConfirm confirm=new CloseConfirm();
        confirm.showclosebox();
    }
    public void initialize(URL location, ResourceBundle resources) {
        progressIndicator.setVisible(false);
        backgroundImage();
        loginController.person.updateDetails();

    }
}
