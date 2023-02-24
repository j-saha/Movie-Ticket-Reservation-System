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

public class NumOfMoviesCheck {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String startDate,endDate,result;

    public NumOfMoviesCheck(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        int maxNoMovies=6;
        List<LocalDate> dates = new ArrayList<>();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        while (!start.equals(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }
        dates.add(start);


        int flag=0;
        for(LocalDate localDate:dates){
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            Statement statement = connection.createStatement();
            try {
                String query = "SELECT * FROM calendar";;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    if (String.valueOf(localDate).equals(String.valueOf(resultSet.getString(1)))) {
                        if(resultSet.getInt("NUM_OF_MOVIES")>=maxNoMovies){
                            result="successful";
                            flag=1;

                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(flag==0)result="failed";

        outToClient.println("numOfMovies#"+result);
        outToClient.flush();


    }
}


