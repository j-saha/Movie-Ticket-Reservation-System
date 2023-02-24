
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;
    public Connection getConnection(){

        String dbname="tutorial";
        String username="root";
        String password="";

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

             connection= DriverManager.getConnection("jdbc:mysql://localhost/"+dbname,username,password);

        }
        catch (Exception e){
            e.printStackTrace();
        }


        return connection;
    }
}
