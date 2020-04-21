package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Message.*;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;

public class Client {

    private static ClientManager clientManager;

    private Socket socket;

    private String ip;
    private int port;

    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;

        clientManager = ClientManager.getInstance();
    }


    public static void sendMessage(Message message) throws IOException {

            socketOut.writeObject(message); //printo sul server
            socketOut.reset();

    }

    public static void receiveMessage() throws IOException{
        Message received;
        try {
            received = (Message) socketIn.readObject();

            clientManager.handleMessageFromServer(received);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void startConnection() {

        try{
            socket = new Socket(ip, port);
            System.out.println("Connection established");

            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());


        } catch (IOException ex) {
            System.out.println("Impossible to connect to " + ip + port);
            ex.printStackTrace();
        }
    }


    public void run() throws IOException {

        startConnection();

        clientManager.login();

         
        while(!Thread.currentThread().isInterrupted()) {
            try{

                while (true){
                    //clientManager.debug();
                    receiveMessage();
                }

            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            }

        }
        
    }
}
