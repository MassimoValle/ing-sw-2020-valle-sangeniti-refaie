package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.ClientManager;
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

    public static boolean receiveMessage() throws IOException{
        Message received;
        try {
            received = (Message) socketIn.readObject();

            if(!checkStatus(received)){
                System.out.println("SERVER: " + received.getMessageContent() + " ERROR");
                return false;
            }

            clientManager.handleMessageFromServer(received);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return true;
    }

    private static boolean checkStatus(Message message){
        if(message.getMessageStatus() == MessageStatus.ERROR)
            return false;

        return true;
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

        do{
            String username = clientManager.askUsername();

            sendMessage(
                    new Message(username, MessageContent.LOGIN, username)
            );
        }while (!receiveMessage());


         
        while(!Thread.currentThread().isInterrupted()) {
            try{

                while (true){
                    receiveMessage();
                }

            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            }

        }
        
    }
}
