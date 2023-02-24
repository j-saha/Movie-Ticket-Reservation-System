import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchMovie {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    public void Search(String movie_name,Socket s) throws Exception {
        Socket connectionSocket =s;
        outToClient = new PrintWriter(connectionSocket.getOutputStream(),true);

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM movies WHERE MOVIE_NAME = '" + movie_name + "';";
            ResultSet resultSet = statement.executeQuery(sql);


            if(resultSet.next()){
                int x=resultSet.getInt(1);
                String startdate=resultSet.getString(8);
                String enddate=resultSet.getString(9);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate endDate = LocalDate.parse(enddate, formatter);
                LocalDate startDate= LocalDate.parse(startdate,formatter);
                LocalDate today=LocalDate.now();


                if( today.isBefore(endDate) &&(today.isAfter(startDate) || today.isEqual(startDate))){
                        String message="Search#Successful#"+String.valueOf(x);
                        outToClient.println(message);
                }
                else{
                    String message ="Search#ufailed";
                    outToClient.println(message);
                }
            }
            else{
                String message="Search#Failed";
                outToClient.println(message);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
