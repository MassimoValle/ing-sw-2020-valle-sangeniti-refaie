package it.polimi.ingsw.network;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.message.server.ServerMessage;
import it.polimi.ingsw.network.message.clientrequests.Request;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Client {

    private static ClientManager clientManager;

    private static Socket socket;

    private static String ip;
    private static int port;

    private static final LinkedList<ServerMessage> queue = new LinkedList<>();

    private static ObjectInputStream socketIn;
    private static ObjectOutputStream socketOut;





    public Client(String ip, int port, ClientView clientView){

        Client.ip = ip;
        Client.port = port;

        clientManager = new ClientManager(clientView);

    }

    public static void sendRequest(Request request) {

        try {
            sendMessage(request);
        }catch (IOException e) {
            ClientManager.LOGGER.severe(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }


    private static void ping(){

        new Thread(() -> {
            try {
                if(!getKeepAlive()){
                    socket.close();
                }
            } catch (InterruptedException | IOException e) {
                ClientManager.LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }).start();

    }

    private static boolean getKeepAlive() throws SocketException, InterruptedException {

        while (!Thread.currentThread().isInterrupted()){

            //QUESTO NON E' UN PING

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
            ClientManager.LOGGER.severe(e.getMessage());
        }
    }





    public void run() throws IOException {

        startConnection();

        clientManager.login();

         
        while(!Thread.currentThread().isInterrupted()) {
            try{
                    receiveMessage();

            } catch(NoSuchElementException e){
                ClientManager.LOGGER.info("Connection closed from the client side");
                ClientManager.LOGGER.severe(e.getMessage());
            }

        }
        
    }

    public static synchronized void startConnection() throws  IOException{

        socket = new Socket(ip, port);
        ClientManager.LOGGER.info("Connection established");

        socket.setKeepAlive(true);
        ping();

        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());

    }


}
