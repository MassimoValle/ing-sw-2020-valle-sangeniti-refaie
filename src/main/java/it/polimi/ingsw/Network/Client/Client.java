package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;

public class Client {

    private static ClientManager clientManager;

    private Socket socket;

    private final String ip;
    private final int port;

    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;





    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;

        clientManager = ClientManager.getInstance();

        //Ping ping = new Ping(ip);
        //new Thread(() -> ping.run()).start();
    }





    public static class Ping implements Runnable{

        private final String ip;

        public Ping(String ip){
            this.ip = ip;
        }

        public void pingRequest() throws IOException {

            InetAddress inetAddress = InetAddress.getByName(ip);

            if (inetAddress.isReachable(3000))
                System.out.println("Server reachable!");
            else
                System.out.println("Server unreacheble!");

        }


        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){

                try {
                    pingRequest();
                    Thread.sleep(5000);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
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
            ping();

            socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketIn = new ObjectInputStream(socket.getInputStream());


        } catch (IOException ex) {
            System.out.println("Impossible to connect to " + ip + port);
            ex.printStackTrace();
        }
    }


}
