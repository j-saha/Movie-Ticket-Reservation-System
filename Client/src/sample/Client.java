package sample;

import javafx.fxml.Initializable;
import sun.applet.Main;


import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    public static int i=0,nummovies,num,m_idx;
    public static Socket clientSocket;
    public static boolean loginsuccess;
    public static boolean passchangesuccess;
    public static String signupmessage,searchsuccess;
    public static String sentence,movieId;
    public static String proPicURL;
    public static PrintWriter outToServer2;
    static {
        try {
            clientSocket = new Socket("localhost", 2730);
            System.out.println(clientSocket.isConnected());
        } catch (IOException e) {
           // e.printStackTrace();
            System.err.println("Server is offline.Please try later.");
            System.exit(1);
        }
    }


    Client(String sen) {
        sentence = sen;
        try {
            outToServer2 = new PrintWriter(Client.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessage sendMessage = new sendMessage();
        Thread threadSend = new Thread(sendMessage);
        threadSend.start();
        receiveMessage receiveMessage = new receiveMessage();
        Thread threadReceive = new Thread(receiveMessage);
        threadReceive.start();
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}

class sendMessage implements Runnable {

    @Override
    public void run() {
        try {
            PrintWriter outToServer = new PrintWriter(Client.clientSocket.getOutputStream(), true);
            outToServer.println(Client.sentence);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class receiveMessage implements Runnable {
    @Override
    public void run() {
        try {
            String modifiedSentence = null;
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(Client.clientSocket.getInputStream()));


            while (true) {
                modifiedSentence = inFromServer.readLine();
                String[] data = modifiedSentence.split("#");

                if (data[0].equalsIgnoreCase("L")) {//for checking login
                    if (data[1].equalsIgnoreCase("successful")) {
                        Client.loginsuccess = true;
                        homeController.who = "user";
                        System.out.println("Log in Successful!");
                        loginController.person.setUserName(data[2]);
                        loginController.person.setName(data[3]);
                        loginController.person.setEmailId(data[4]);
                        loginController.person.setPassword(data[5]);
                        loginController.person.setPhoneNumber(Integer.parseInt(data[6]));
                        loginController.person.setCreditCardNumber(Integer.parseInt(data[7]));
                        loginController.person.setBoughtMovies(data[8]);
                        loginController.person.setProURL(data[9]);
                    } else if (data[1].equalsIgnoreCase("failed")) {
                        Client.loginsuccess = false;
                        System.out.println("Log in failed!");
                        System.out.println("Username or Password is incorrect");
                    }
                }



                if (data[0].equalsIgnoreCase("AL")) {   //for checking login
                    if (data[1].equalsIgnoreCase("successful")) {
                        Client.loginsuccess = true;
                        homeController.who = "admin";
                        System.out.println("Log in Successful!");
                        loginController.person.setUserName(data[2]);
                        loginController.person.setName(data[3]);
                        loginController.person.setEmailId(data[4]);
                        loginController.person.setPassword(data[5]);
                        loginController.person.setPhoneNumber(Integer.parseInt(data[6]));
                        loginController.person.setProURL(data[7]);
                    } else if (data[1].equalsIgnoreCase("failed")) {
                        Client.loginsuccess = false;
                        System.out.println("Log in failed!");
                        System.out.println("Username or Password is incorrect");
                    }
                }

                else if (data[0].equalsIgnoreCase("movieDetail")) {
                    if (data[1].equalsIgnoreCase("successful")) {
                        movieDetailsController.movieData=modifiedSentence;
                        Client.m_idx=Integer.parseInt(data[2]);
                        // System.out.println("in Client "+data[9]+data[10]);
                    } else if (data[1].equalsIgnoreCase("failed")) {
                    }
                }


                else if (data[0].equalsIgnoreCase("checkAvailavleTickets")) {
                    if (data[1].equalsIgnoreCase("successful")) {
                        buyticketController.isAvailable=true;

                    } else if (data[1].equalsIgnoreCase("failed")) {
                        buyticketController.isAvailable=false;
                    }

                }

                else if (data[0].equalsIgnoreCase("numOfMovies")) {
                    if (data[1].equalsIgnoreCase("successful")) {
                        addMoviesDetailsController.isFull=true;
                    }
                    else if (data[1].equalsIgnoreCase("failed")) {
                        addMoviesDetailsController.isFull=false;
                    }

                }

                else if (data[0].equalsIgnoreCase("payment")) {
                    CardConfirmation.price=Integer.parseInt(data[1]);
                    Thread.sleep(1000);
                }



                else if (data[0].equalsIgnoreCase("R")) {  //for checking register
                    if (data[1].equalsIgnoreCase("successful")) {
                        Client.signupmessage = "successful";
                        System.out.println(data[2]);

                    } else if (data[1].equalsIgnoreCase("ufailed")) {
                        Client.signupmessage = "ufailed";
                        System.out.println(data[2]);
                    } else {
                        System.out.println(data[2]);
                        Client.signupmessage = "invalid";
                    }

                } else if (data[0].equalsIgnoreCase("P")) { //for password check
                    if (data[1].equalsIgnoreCase("successful")) {
                        Client.passchangesuccess = true;
                        System.out.println(data[2]);

                    } else {
                        Client.passchangesuccess = false;
                        System.out.println(data[2]);
                    }
                } else if (data[0].equalsIgnoreCase("PI")) //for profile pic
                {
                    String[] fname = data[1].split("/");
                    System.out.println(fname[2]);

                    try {
                        String strRecv=null;
                        try {
                             strRecv = inFromServer.readLine();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        int filesize = Integer.parseInt(strRecv);
                        byte[] contents = new byte[10000];

                        FileOutputStream fos = new FileOutputStream(fname[2]);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);

                        InputStream is = Client.clientSocket.getInputStream();


                        int bytesRead = 0;
                        int total = 0;

                        while (total != filesize) {

                            bytesRead = is.read(contents);
                            total += bytesRead;
                           // System.out.println(filesize + " " + total + " " + bytesRead);
                            bos.write(contents, 0, bytesRead);
                        }
                        bos.flush();
                        fos.close();
                        bos.close();
                        Client.proPicURL=fname[2];
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Could not transfer file.");
                    }

                }

                else if(data[0].equalsIgnoreCase("search"))  //for searching the movie
                {
                    if(data[1].equalsIgnoreCase("successful")){
                        Client.movieId=data[2];
                        Client.searchsuccess="true";
                    }
                    else if(data[1].equalsIgnoreCase("failed")) {
                        Client.searchsuccess="false";
                    }
                    else{
                        Client.searchsuccess="htrue";
                    }
                }

                else if(data[0].equalsIgnoreCase("MOVIES")){
                    homeController.movieAll=data[1];
                    System.out.println("Number of movies: "+data[2]);
                    Client.nummovies=Integer.parseInt(data[2]);
                }

                else if(data[0].equalsIgnoreCase("UPCOMINGMOVIES")){
                    upComingController.upMovieNames=data[1];
                    Client.num=Integer.parseInt(data[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}




