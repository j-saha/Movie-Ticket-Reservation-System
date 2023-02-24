import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class sendUpComings {
    BufferedReader inFromClient;
    PrintWriter outToClient;


    public void dataToClient(Socket s) throws Exception {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        int numOfMovies = 0;
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String[] movies = new String[100];
        ObservableList observableList = null;
        try {
            String query = "SELECT * FROM movies";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            for (int i = 0; rs.next(); i++) {
                String startdate=rs.getString(8);
                String enddate=rs.getString(9);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate endDate = LocalDate.parse(enddate, formatter);
                LocalDate startDate= LocalDate.parse(startdate,formatter);
                LocalDate today=LocalDate.now();
                if((today.isBefore(startDate)) && today.isBefore(endDate)){
                    movies[numOfMovies] = rs.getString("MOVIE_NAME")+"~"+startdate+"~"+enddate;
                  //  System.out.println(movies[numOfMovies]);
                    numOfMovies++;
            }
        }

            st.close();

            String moviesTogether = String.join(",", movies);
            String data="UPCOMINGMOVIES#"+moviesTogether+"#"+String.valueOf(numOfMovies);
           // System.out.println(data);
            outToClient.println(data);
            outToClient.flush();
        }
        catch (Exception e){

        }
    }
}
