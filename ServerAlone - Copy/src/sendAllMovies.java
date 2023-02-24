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

public class sendAllMovies {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String who;

    public sendAllMovies(String who) {
        this.who = who;
    }

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


            if (who.equals("admin")) {
                System.out.println("in admin");
                for (int i = 0; rs.next(); i++) {
                    String startdate=rs.getString(8);
                    String enddate=rs.getString(9);
                    int income=rs.getInt(10);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate endDate = LocalDate.parse(enddate, formatter);
                    LocalDate startDate= LocalDate.parse(startdate,formatter);

                    String mov=rs.getString("MOVIE_NAME");
                   /* int len=mov.length();
                    for(int k=0;k<40-len;k++) {
                        mov = mov + " ";
                    }*/
                    movies[i] = mov + "~" + startdate + "~" + enddate + "~" + income;

                    //movies[i] = rs.getString("MOVIE_NAME")+"                  From "+startdate+"              To "+enddate+"     Total income= "+income;
                    //movies[i] = rs.getString("MOVIE_NAME");
                    //System.out.println(movies[i]);
                    numOfMovies++;
                }
            } else {
                System.out.println("in user");
                for (int i = 0,j=0; rs.next(); i++) {
                    String startdate=rs.getString(8);
                    String enddate=rs.getString(9);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                    LocalDate endDate = LocalDate.parse(enddate, formatter);
                    LocalDate startDate= LocalDate.parse(startdate,formatter);
                    LocalDate today=LocalDate.now();
                    if((today.isAfter(startDate) || today.isEqual(startDate)) && today.isBefore(endDate) || today.isEqual(endDate)){
                        String mov=rs.getString("MOVIE_NAME");
                        int price=rs.getInt(6);
                        int len=mov.length();
                        for(int k=0;k<40-len;k++) {
                            mov = mov + " ";
                        }
                        movies[j] =mov+"~"+startdate+"~"+enddate+"~"+price;
                        //System.out.println("Every data : "+movies[j]);
                        j++;
                        numOfMovies++;
                    }

                }
            }
            st.close();

            String moviesTogether = String.join(",", movies);
            String data="MOVIES#"+moviesTogether+"#"+String.valueOf(numOfMovies);
            //System.out.println("Final list to client:  "+ data);
            outToClient.println(data);
            outToClient.flush();
        }
        catch (Exception e){

        }
    }
}
