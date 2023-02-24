import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMovie {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String txtmovieName, txtdirectorName, txtCast, txtRating, txtPrice, path, startDate, endDate,result;


    public AddMovie(String txtmovieName, String txtdirectorName, String txtCast, String txtRating, String txtPrice, String path, String startDate, String endDate) {
        this.txtmovieName = txtmovieName;
        this.txtdirectorName = txtdirectorName;
        this.txtCast = txtCast;
        this.txtRating = txtRating;
        this.txtPrice = txtPrice;
        this.path = path;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        int num=1;
        Statement st=connection.createStatement();
        ResultSet res = st.executeQuery("SELECT MOVIE_ID FROM movies");
        while (res.next()) num++;
        //if(num>6) result="HALL FULL";
        int tktprc=Integer.valueOf(txtPrice);
        float rate= Float.parseFloat(txtRating);
        int income=0;


        String sql="INSERT INTO movies VALUES('"+num+"','"+txtmovieName+"','"+txtdirectorName+"','"+txtCast+"','"+rate+"','"+tktprc+"','"+path+"','"+startDate+"','"+endDate+"','"+income+"')";

        Statement statement=connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println(txtmovieName+" is added");


        outToClient.println("addMovie#");
        outToClient.flush();
    }


}
