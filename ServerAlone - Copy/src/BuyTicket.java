import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class BuyTicket {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String h;
    String date;
    int seats;
    String result;
    String x;

    public BuyTicket(String h, String date, String x) {
        this.h = h;
        this.date = date;
        this.x = x;
    }

    public void dataToClient(Socket s) throws Exception {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);



        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        String query = "SELECT * FROM calendar where DATES='"+date+"'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        if(rs.next()){
            seats=rs.getInt(h);
            System.out.println(seats);
            if(Integer.parseInt(x)<=seats){
                result="successful";
            }
            else {
                result="failed";
            }
        }

        outToClient.println("checkAvailavleTickets#"+result);
        outToClient.flush();

    }
}
