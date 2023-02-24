import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMovie {
    BufferedReader inFromClient;
    PrintWriter outToClient;
    String movieID,numOfMovies;

    public DeleteMovie(String movieID, String numOfMovies) {
        this.movieID = movieID;
        this.numOfMovies = numOfMovies;
    }


    public void dataToClient(Socket s) throws SQLException, IOException {
        Socket connectionSocket = s;

        inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

        ConnectionClass connectionClass=new ConnectionClass();
        Connection connection=connectionClass.getConnection();
        String sql="DELETE FROM movies where MOVIE_ID='"+movieID+"'";

        Statement statement=connection.createStatement();
        statement.executeUpdate(sql);
        System.out.println("MOVIE DELETED");
        try {

            for(int i = Integer.parseInt(movieID)+1; i<= Integer.valueOf(numOfMovies); i++){
                String update="UPDATE movies SET MOVIE_ID='"+(i-1)+"' where MOVIE_ID='"+(i)+"'";

                statement=connection.createStatement();
                statement.executeUpdate(update);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
