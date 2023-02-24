import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;

public class ChangeProPic {
    BufferedReader inFromClient;
    PrintWriter outToClient;

    public void changePic(String path, String username, String who, Socket s) throws Exception {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        if(who.equals("user")){
            String update="UPDATE user SET profile_pic='"+path+"' where name='"+username+"'";

            Statement statement=connection.createStatement();
            statement.executeUpdate(update);
        }
        else{
            String update="UPDATE admin SET profile_pic='"+path+"' where name='"+username+"'";

            Statement statement=connection.createStatement();
            statement.executeUpdate(update);
        }

    }
}