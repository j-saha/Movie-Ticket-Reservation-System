import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.sql.Connection;

public class PassChange {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    public void changePassword(String newpass, String oldpass,String name, Socket s) throws Exception{
        Socket connectionSocket=s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(),true);

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        String sql = "SELECT * FROM user WHERE name = '"+name+"' AND password = '"+oldpass+"';";
        String update="UPDATE user SET password='"+newpass+"' where name='"+name+"'";

        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);

        if(resultSet.next()) {
            statement.executeUpdate(update);
            String message="P"+"#successful#"+"Password is changed successfully!";
            outToClient.println(message);
        }
        else{
            String message="P#failed#Old Password is wrong!";
            outToClient.println(message);
            System.out.println("Old password is not correct");
        }
    }
}
