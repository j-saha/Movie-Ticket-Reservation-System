import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class MovieDetails {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    private String ID;


    public MovieDetails() {
    }

    public MovieDetails(String Id) {
        this.ID= Id;
    }

    public String getID() {
        return ID;
    }

    public void dataToClient(Socket s) throws Exception{
        Socket connectionSocket =s;
        String movieID,movieName,directorName,cast,rating,price,poster,startDate,endDate;
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(),true);
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        try {
            Statement statement=connection.createStatement();
            String sql = "SELECT * FROM movies WHERE MOVIE_NAME = '"+getID()+"';";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                movieID=resultSet.getString(1);
                movieName=resultSet.getString(2);
                directorName=resultSet.getString(3);
                cast=resultSet.getString(4);
                rating=String.valueOf(resultSet.getInt(5));
                price=resultSet.getString(6);
                poster=resultSet.getString(7);
                startDate=resultSet.getString(8);
                endDate=resultSet.getString(9);

                String message="movieDetail"+"#"+"successful"+"#"+movieID+"#"+movieName+"#"+directorName+"#"+cast+"#"+rating+"#"+price+"#"+poster+"#"+startDate+"#"+endDate;
                //System.out.println(message);
                outToClient.println(message);
            }
            else
            {
                String message="movieDetail"+"#"+"failed";
                System.out.println(message);
                outToClient.println(message);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
