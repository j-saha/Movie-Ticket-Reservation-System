import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Payment {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String movieID;
    String numoftickets;
    int cost;

    public Payment(String movieID, String numoftickets) {
        this.movieID = movieID;
        this.numoftickets = numoftickets;
    }

    public void dataToClient(Socket s) throws Exception {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);


        int price;
        int income=0;
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        try {
            Statement statement=connection.createStatement();
            String query="SELECT * from movies where MOVIE_ID='"+movieID+"'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                price= resultSet.getInt("TICKET_PRICE");
                income=resultSet.getInt(10);
                System.out.println("in it "+movieID);
                System.out.println(price);
                System.out.println(Integer.parseInt(numoftickets));
                System.out.println(cost);

                cost= (price*Integer.parseInt(numoftickets));
            }
            else {
                System.out.println(movieID);
            }
            String update="UPDATE movies SET income='"+(income+cost)+"' where MOVIE_ID='"+movieID+"'";
            statement.executeUpdate(update);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        outToClient.println("payment#"+String.valueOf(cost));
        outToClient.flush();

    }
}
