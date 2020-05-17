package it.polimi.ingsw.Network;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

public class Client {

    private static ClientManager clientManager;

    private Socket socket;

    private final String ip;
    private final int port;

    private static LinkedList queue;

    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;





    public Client(String ip, int port, ClientView clientView){

        this.ip = ip;
        this.port = port;

        queue = new LinkedList();

        clientManager = new ClientManager(clientView);

    }





    private void ping(){

        new Thread(() -> {
            try {
                if(!getKeepAlive()){
                    socket.close();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    private boolean getKeepAlive() throws SocketException, InterruptedException {

        while (!Thread.currentThread().isInterrupted()){

            System.out.println("Socket keep alive: " + socket.getKeepAlive());

            if(!socket.getKeepAlive()) return false;

            Thread.sleep(5000);
        }

        return true;
    }





    public static void sendMessage(Message message) throws IOException {

            socketOut.writeObject(message); //printo sul server
            socketOut.reset();

    }

    public static void receiveMessage() throws IOException{
        Message received;
        try {
            received = (Message) socketIn.readObject();

            queue.add(received);

            if(!queue.isEmpty())
                clientManager.handleMessageFromServer(received);

        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }





    public void run() throws IOException {

        startConnection();

        clientManager.login();

         
        while(!Thread.currentThread().isInterrupted()) {
            try{
                    receiveMessage();

            } catch(NoSuchElementException e){
                System.out.println("Connection closed from the client side");
            }

        }
        
    }

    public void startConnection() {

        try{
            socket = new Socket(ip, port);
            System.out.println("Connection established");

            socket.setKeepAlive(true);
            //ping();

            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());


        } catch (IOException ex) {
            System.out.println("Impossible to connect to " + ip + port);
            ex.printStackTrace();
        }
    }


}
