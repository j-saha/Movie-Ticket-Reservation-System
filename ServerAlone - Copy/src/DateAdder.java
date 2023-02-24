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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DateAdder {

    BufferedReader inFromClient;
    PrintWriter outToClient;
    String startDate,endDate;

    public DateAdder(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        List<LocalDate> dates = new ArrayList<>();

        System.out.println(startDate);


        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        while (!start.equals(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }
        dates.add(start);

        for(LocalDate localDate:dates){
            int found=0;
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            Statement statement = connection.createStatement();
            try {
                String query = "SELECT * FROM calendar";;
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    if (String.valueOf(localDate).equals(String.valueOf(resultSet.getDate(1)))) {
                        found = 1;
                        System.out.println(resultSet.getDate(1));
                        query="UPDATE calendar SET NUM_OF_MOVIES='"+(resultSet.getInt(2)+1)+"' where DATES='"+resultSet.getDate(1)+"'";
                        statement=connection.createStatement();
                        statement.executeUpdate(query);
                        break;
                    }
                }
                if(found==0){
                    int hall=100;
                    query="INSERT INTO calendar VALUES('"+localDate+"','"+1+"','"+hall+"','"+hall+"','"+hall+"','"+hall+"')";

                    statement=connection.createStatement();
                    statement.executeUpdate(query);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
