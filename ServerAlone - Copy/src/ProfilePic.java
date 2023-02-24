import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfilePic {

    public void sentProPic(String username,Socket s) throws Exception {
        Socket connectSocket=s;
        String pro="./Images/proPic.jpg";;

        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        String query = "SELECT profile_pic FROM user where name='" + username + "'";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);

        if (rs.next()) {
             pro = rs.getString("profile_pic");
             pro="."+pro;
            System.out.println(pro);
            //proPic.setImage(new Image(pro));
        } else {
            pro="./Images/proPic.jpg";
            //proPic.setImage(new Image("/Images/proPic.jpg"));
        }

        sentfile(connectSocket,pro);
        Thread.sleep(1000);
    }
    private void sentfile(Socket clientsocket, String fname){
        try
        {
            PrintWriter outToServer = new PrintWriter(clientsocket.getOutputStream(), true);

            String data="PI#"+fname;
            outToServer.println(data);

            File file = new File(fname);
            System.out.println(file.canRead());
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = clientsocket.getOutputStream();
            byte[] contents;
            long fileLength = file.length();
            outToServer.println(String.valueOf(fileLength));
            System.out.println(fileLength);
            long current = 0;

            Thread.sleep(50);
            while(current!=fileLength){
                int size = 10000;
                if(fileLength - current >= size)
                    current += size;
                else{
                    size = (int)(fileLength - current);
                    current = fileLength;
                }
                contents = new byte[size];
                bis.read(contents, 0, size);
                os.write(contents);
            }
            os.flush();
            System.out.println("File sent successfully!");
        }
        catch(Exception e)
        {
            System.err.println("Could not transfer file.");
        }
    }

}
