import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class SignUp {
    BufferedReader inFromClient;
    PrintWriter outToClient;

    private String username,fullname,email,password;
    private int number,credit;
    public SignUp(String s)
    {
        String[] dataOfUser=s.split("#");

        username=dataOfUser[2];
        fullname=dataOfUser[3];
        email=dataOfUser[4];
        password=dataOfUser[5];
        try {
            number=Integer.parseInt(dataOfUser[6]);
            credit=Integer.parseInt(dataOfUser[7]);
        }catch (Exception e){
            number=123456789;
            credit=12345;
        }

       // System.out.println(username+fullname+email+password+number+credit);
    }

    public void updatedata(Socket s) throws Exception
    {
        Socket connectionSocket=s;
        String message=null;
        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(),true);

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();

        Random random=new Random();
        int balance=2000+random.nextInt(18000);

        try {
            Statement statement=connection.createStatement();
            String sql = "SELECT * FROM user WHERE name = '"+username+"';";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                message="R"+"#"+"ufailed"+"#Username is already taken!";
                outToClient.println(message);
                System.out.println("Username is already taken!");
            } else {

                String proPic="/Images/proPic.jpg";
                sql="INSERT INTO user VALUES('"+username+"','"+fullname+"','"+email+"','"+password+"','"+number+"','"+credit+"','"+balance+"','','"+proPic+"')";

                statement=connection.createStatement();
                statement.executeUpdate(sql);
                message="R"+"#"+"successful"+"#Register is Successful!";
                outToClient.println(message);
            }
        }
        catch (SQLException e) {
            message="R"+"#"+"invalid"+"#Register is UnSuccessful!";
            outToClient.println(message);
            e.printStackTrace();
        }

    }
}
