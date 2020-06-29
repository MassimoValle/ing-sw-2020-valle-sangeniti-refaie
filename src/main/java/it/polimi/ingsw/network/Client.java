package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.Server.ServerMessage;
import it.polimi.ingsw.network.message.clientrequests.Request;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Client {

    private static ClientManager clientManager;

    private Socket socket;

    private final String ip;
    private final int port;

    private static LinkedList<ServerMessage> queue;

    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;





    public Client(String ip, int port, ClientView clientView){

        this.ip = ip;
        this.port = port;

        queue = new LinkedList();

        clientManager = new ClientManager(clientView);

    }

    public static void sendRequest(Request request) {

        try {
            sendMessage(request);
        }catch (IOException e) {
            e.printStackTrace();
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
                Thread.currentThread().interrupt();
            }
        }).start();

    }

    private boolean getKeepAlive() throws SocketException, InterruptedException {

        while (!Thread.currentThread().isInterrupted()){

            //QUESTO NON E' UN PING
            //System.out.println("Socket keep alive: " + socket.getKeepAlive());

            if(!socket.getKeepAlive()) return false;

            Thread.sleep(5000);
        }

        return true;
    }





    private static void sendMessage(Message message) throws IOException {

            socketOut.writeObject(message);
            socketOut.reset();

    }

    private static void receiveMessage() throws IOException{
        ServerMessage received;
        try {
            received = (ServerMessage) socketIn.readObject();

            queue.add(received);

            if(!queue.isEmpty())
                clientManager.handleMessageFromServer(queue.pop());

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

    public void startConnection() throws  IOException{

        socket = new Socket(ip, port);
        System.out.println("Connection established");

        socket.setKeepAlive(true);
        //ping();

        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());

    }


}
