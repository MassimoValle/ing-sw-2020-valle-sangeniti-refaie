package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Message.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private Socket socket;

    private String username;
    private String ip;
    private int port;


    private Scanner consoleIn;
    private PrintStream consoleOut;

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;

        consoleIn = new Scanner(System.in);
        consoleOut = new PrintStream(System.out, true);
    }

    private String getUsername() {
        return this.username;
    }

    public void sendMessage(Message message) throws IOException {
        if (consoleOut != null) {

            // System.out.println(message.toString()); //printo sul client

            socketOut.writeObject(message); //printo sul server
            socketOut.reset();

        }
    }

    public boolean receiveMessage() throws IOException{
        Message received;
        try {
            received = (Message) socketIn.readObject();
            return handleMessage((Response) received);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return false;
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
        consoleOut.print("Enter your username: ");

        //Setto il nome utente
        do {
            if (consoleIn.hasNextLine()) {
                String currentUsername;
                currentUsername = consoleIn.nextLine();

                username = currentUsername;
            }
        } while (username == null);

        consoleOut.println("YOUR USERNAME: " + username);
        //consoleOut.println("Have a nice day");

    }

    private void doTurn() throws IOException{
        String inputLine = consoleIn.nextLine();
        sendMessage(
                new Request(getUsername(), MessageContent.CHECK, inputLine)
        );

        receiveMessage();


    }

    private boolean handleMessage(Response message){

        String out = "SERVER: ";

        switch (message.getMessageStatus()){
            case OK:
                consoleOut.println(out + message.getMessage());
                return true;
            default: ERROR:
                consoleOut.println(out + "error");
                return false;
        }

    }



    public void run() throws IOException {

        startConnection();

        do{
            askUsername();

            sendMessage(
                    new PlayerLoginRequest(username)
            );
        }while (!receiveMessage());




        //String socketLine;
         
        while(!Thread.currentThread().isInterrupted()) {
            try{
                /*consoleOut.print(">>> ");
                socketLine = consoleIn.nextLine();
                sendMessage(
                        new Request(getUsername(), MessageContent.CHECK, socketLine)
                );*/

                while (true){
                    doTurn();
                }
            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            }

        }
        
    }
}
