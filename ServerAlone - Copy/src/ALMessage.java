import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class ALMessage {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    private String username;
    private String password;
    private String type;

    public ALMessage() {
    }

    public ALMessage(String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    private boolean isMember() throws Exception {
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM admin WHERE name = '" + getUsername() + "' AND password = '" + getPassword() + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public void dataToClient(Socket s) throws Exception{
        Socket connectionSocket =s;
        String username,fullname,email,password,credit,number,movies,proPicurl;
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(),true);
        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        try {
            Statement statement=connection.createStatement();
            String sql = "SELECT * FROM admin WHERE name = '"+getUsername()+"' AND password = '"+getPassword()+"';";
            ResultSet resultSet = statement.executeQuery(sql);
            if(resultSet.next()){
                username=resultSet.getString(1);
                fullname=resultSet.getString(2);
                email=resultSet.getString(3);
                password=resultSet.getString(4);
                number=String.valueOf(resultSet.getInt(5));
                proPicurl=resultSet.getString(9);
                String message="AL"+"#"+"successful"+"#"+username+"#"+fullname+"#"+email+"#"+password+"#"+number+"#"+proPicurl;
                System.out.println(message);
                outToClient.println(message);
            }
            else
            {
                String message="AL"+"#"+"failed";
                System.out.println(message);
                outToClient.println(message);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
