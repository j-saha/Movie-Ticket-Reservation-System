import javafx.collections.ObservableList;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;

public class Server
{


    public static void main(String argv[]) throws Exception
    {
        int workerThreadCount = 0;
        ServerSocket welcomeSocket = new ServerSocket(2730);
        int id=0;
        System.out.println("Server has started");
        while(true)
        {

            Socket connectionSocket = welcomeSocket.accept();
            WorkerThread wt = new WorkerThread(connectionSocket, id);
            Thread t = new Thread(wt);
            t.start();
            workerThreadCount++;

            System.out.println("Client [" + id + "] is now connected. No. of worker threads = " + workerThreadCount);
            id++;
        }
    }
}
class WorkerThread implements Runnable
{


    public static String[] loggedInUsers=new String[10000];
    public static String[] messages=new String[10000];
    public static int[] receivers=new int[10000];
    public static int numOfMessages=0;
    public static int numOfActiveUser=0;


    public Socket connectionSocket;
    BufferedReader inFromClient;
    PrintWriter outToClient2;
    public LMessage lMessage;
    public ALMessage alMessage;
    public MovieDetails movieDetails;

    private int id;
    private int checkSignIn;
    private int checkAdmin;
    public WorkerThread(Socket s, int id)
    {
        this.connectionSocket = s;
        this.id = id;
        checkAdmin=0;
        checkSignIn=0;
    }


    public void run()
    {
        while(true)
        {
            String clientSentence;
            try
            {
                inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                outToClient2 = new PrintWriter(connectionSocket.getOutputStream(),true);

                clientSentence = inFromClient.readLine();
                String[] str;
                str=clientSentence.split("#");

                if(str[1].equals("L")){     //Checking L for Login Check
                    lMessage=new LMessage(str[2],str[4],str[6]);
                    lMessage.dataToClient(connectionSocket);
                    if(checkSignIn==0) {
                        loggedInUsers[id]=lMessage.getUsername();
                        numOfActiveUser++;
                        checkSignIn=1;
                        if(lMessage.getType().equalsIgnoreCase("admin")) checkAdmin=1;
                    }
                    str[1]=null;
                }
                else if(str[1].equals("AL")){     //Checking L for Admin Login Check
                    alMessage=new ALMessage(str[2],str[4],str[6]);
                    alMessage.dataToClient(connectionSocket);
                    if(checkSignIn==0) {
                        loggedInUsers[id]=alMessage.getUsername();
                        numOfActiveUser++;
                        checkSignIn=1;
                        if(alMessage.getType().equalsIgnoreCase("admin")) checkAdmin=1;
                    }
                    str[1]=null;
                }

                else if(str[1].equals("movieDetail")){

                    movieDetails=new MovieDetails(str[2]);
                    movieDetails.dataToClient(connectionSocket);
                    str[1]=null;

                }

                else if(str[1].equals("checkAvailavleTickets")){
                    BuyTicket buyTicket=new BuyTicket(str[2],str[3],str[4]);
                    buyTicket.dataToClient(connectionSocket);
                    str[1]=null;

                }

                else if(str[1].equals("payment")){
                    Payment payment=new Payment(str[2],str[3]);
                    payment.dataToClient(connectionSocket);
                    str[1]=null;

                }
                else if(str[1].equals("addMovie")){
                    AddMovie addMovie=new AddMovie(str[2],str[3],str[4],str[5],str[6],str[7],str[8],str[9]);
                    addMovie.dataToClient(connectionSocket);
                    str[1]=null;
                }
                else if(str[1].equals("addMovieToUser")){
                    AddMovieToUser addMovieToUser=new AddMovieToUser(str[2],str[3],str[4],str[5],str[6],str[7],str[8]);
                    addMovieToUser.dataToClient(connectionSocket);
                    str[1]=null;
                }

                else if(str[1].equals("dateAdder")){
                    DateAdder dateAdder=new DateAdder(str[2],str[3]);
                    System.out.println(str[2]+str[3]);
                    System.out.println("date is add");
                    dateAdder.dataToClient(connectionSocket);
                    str[1]=null;
                }

                else if(str[1].equals("dateDelete")){
                    DateDelete dateDelete=new DateDelete(str[2]);
                    System.out.println(str[2]);
                    System.out.println("date is deleted");
                    dateDelete.dataToClient(connectionSocket);
                    str[1]=null;
                }

                else if(str[1].equals("numOfMovies")){
                    NumOfMoviesCheck numOfMoviesCheck=new NumOfMoviesCheck(str[2],str[3]);
                    numOfMoviesCheck.dataToClient(connectionSocket);
                    str[1]=null;
                }

                else if(str[1].equals("deleteMovie")){
                    DeleteMovie deleteMovie=new DeleteMovie(str[2],str[3]);
                    deleteMovie.dataToClient(connectionSocket);
                    str[1]=null;
                }


                else if(str[1].equals("R")){   //checking R for register
                    SignUp signUp=new SignUp(clientSentence);
                    signUp.updatedata(connectionSocket);
                    str[1]=null;
                }




                else if(str[1].equals("P")) //checking P for password change
                {
                    //System.out.println("In the passwordchange");
                    PassChange passChange=new PassChange();
                    passChange.changePassword(str[2],str[3],str[4],connectionSocket);
                    str[1]=null;
                }
              /*  else if(str[1].equals("PI"))  //for sending profile pic
                {
                    System.out.println(str[2]);
                    ProfilePic profilePic=new ProfilePic();
                    profilePic.sentProPic(str[2],connectionSocket);
                    str[1]=null;
                }*/

                else if(str[1].equalsIgnoreCase("MOVIES")){
                    System.out.println("in movies");
                    sendAllMovies sendAllMovies=new sendAllMovies(str[2]);
                    sendAllMovies.dataToClient(connectionSocket);
                    str[1]=null;

                }
                else if(str[1].equals("upComingMovies")){
                    sendUpComings sendUpComings=new sendUpComings();
                    sendUpComings.dataToClient(connectionSocket);
                    str[1]=null;

                }
                else if(str[1].equalsIgnoreCase("search")){
                        SearchMovie searchMovie=new SearchMovie();
                        searchMovie.Search(str[2],connectionSocket);
                }

                else if(str[1].equalsIgnoreCase("changePropic")){
                    ChangeProPic changeProPic=new ChangeProPic();
                    changeProPic.changePic(str[2],str[3],str[4],connectionSocket);
                }
            }
            catch(Exception e)
            {

                e.printStackTrace();
                break;
            }
        }
    }
}
