import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMovieToUser {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String movies;
    String movieID;
    String date;
    String time;
    String username;
    String hall;
    String numoftickets;

    public AddMovieToUser(String movies, String movieID, String date, String time, String username, String hall, String numoftickets) {
        this.movies = movies;
        this.movieID = movieID;
        this.date = date;
        this.time = time;
        this.username = username;
        this.hall = hall;
        this.numoftickets = numoftickets;
    }



    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        String name=null;
        //String movies = person.getBoughtMovies();

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        try {
            String query = "SELECT * from movies where MOVIE_ID='" + movieID + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                name = resultSet.getString("MOVIE_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name = movies + "," + name + "~" + date + "~" + time+"~"+numoftickets;

        String update1 = "UPDATE user SET buytickets='" + name + "' where name='" + username + "'";
        statement.executeUpdate(update1);
        System.out.println(hall);
        System.out.println(numoftickets);


        int hallSeat=0;
        String query = "SELECT * from calendar where DATES='" + date + "'";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()) hallSeat=resultSet.getInt(hall);

        String update="UPDATE calendar SET "+hall+"='"+(hallSeat-Integer.parseInt(numoftickets))+"' where DATES='"+date+"'";
        statement.executeUpdate(update);
    }
}
