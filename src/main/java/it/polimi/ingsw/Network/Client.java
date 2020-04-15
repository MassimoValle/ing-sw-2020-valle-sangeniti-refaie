package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.PlayerLoginRequest;
import it.polimi.ingsw.Network.Message.Request;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private Socket socket;

    private String username;
    private String ip;
    private int port;


    private Scanner in;
    private PrintStream out;

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;

        in = new Scanner(System.in);
        out = new PrintStream(System.out, true);
    }

    private String getUsername() {
        return this.username;
    }

    public void sendMessage(Message message) throws IOException {
        if (out != null) {

            // System.out.println(message.toString()); //printo sul client

            socketOut.writeObject(message); //printo sul server
            socketOut.reset();

        }
    }

    public void startConnection() throws IOException {
        try{
            socket = new Socket(ip, port);
            System.out.println("Connection established");

            username = "";

            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());


        } catch (IOException ex) {
            System.out.println("Impossible to connect to " + ip + port);
        }
    }

    //Ask the user to insert a username
    private void askUsername() {
        out.println("Enter your username:");

        //Setto il nome utente
        do {
            out.print(">>> ");


            if (in.hasNextLine()) {
                String currentUsername;
                currentUsername = in.nextLine();

                username = currentUsername;
            }
        } while (username == null);

        out.printf("Hi %s!%n", username);
        out.println("Have a nice day");

    }



    public void run() throws IOException {

        startConnection();
        askUsername();

        sendMessage(
                new PlayerLoginRequest(username)
        );

        String socketLine;
         
        while(!Thread.currentThread().isInterrupted()) {
            try{
                out.print(">>> ");
                socketLine = in.nextLine();
                sendMessage(
                        new Request(getUsername(), MessageContent.CHECK, socketLine)
                );

                while (true){
                    String inputLine = in.nextLine();
                    sendMessage(
                            new Request(getUsername(), MessageContent.CHECK, inputLine)
                    );
                }
            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            }

        }
        
    }
}
