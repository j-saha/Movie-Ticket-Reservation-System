import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateDelete {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String movieID;

    public DateDelete(String movieID) {
        this.movieID = movieID;
    }

    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);


        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM movies where MOVIE_ID='"+movieID+"'";
        ResultSet resultSet = statement.executeQuery(query);

        LocalDate start=null;
        LocalDate end=null;
        List<LocalDate> dates = new ArrayList<>();
        if(resultSet.next()){
            start = LocalDate.parse(resultSet.getString("START_DATE"));
            end = LocalDate.parse(resultSet.getString("END_DATE"));
        }

        while (!start.equals(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }
        dates.add(start);

        for(LocalDate localDate:dates){

            try {
                connectionClass = new ConnectionClass();
                connection = connectionClass.getConnection();
                statement = connection.createStatement();
                query = "SELECT * FROM calendar";;
                ResultSet result = statement.executeQuery(query);

                while (result.next()){
                    if (String.valueOf(localDate).equals(String.valueOf(result.getDate(1)))) {
                        query="UPDATE calendar SET NUM_OF_MOVIES='"+(result.getInt(2)-1)+"' where DATES='"+result.getDate(1)+"'";
                        statement=connection.createStatement();
                        statement.executeUpdate(query);
                        break;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
