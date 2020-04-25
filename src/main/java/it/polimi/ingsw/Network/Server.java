package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Response;
import it.polimi.ingsw.View.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT= 8080;
    private ServerSocket serverSocket;

    private ExecutorService executor = Executors.newFixedThreadPool(128);

    private List<Connection> connections = new ArrayList<Connection>();
    private Map<String, Connection> clientsConnected = new HashMap<>();
    private Map<String, Connection> playersInLobby = new HashMap<>();

    // Controller of the game
    GameManager gameManager;

    // field set by a user
    private int lobbySize = 2;




    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        for (Map.Entry<String, Connection> entry : playersInLobby.entrySet())
            if(entry.getValue().equals(c))
                playersInLobby.remove(entry);
    }



    // full up lobby and start game
    public synchronized void lobby() {

        if(clientsConnected.size() == lobbySize){

            Game game = new Game();


            for (Map.Entry<String, Connection> entry : clientsConnected.entrySet())
            {
                RemoteView player = new RemoteView(new Player(entry.getKey()), entry.getValue());
                game.addObserver(player);
                //player.addObserver(controller);
                playersInLobby.put(entry.getKey(), entry.getValue());

                game.addPlayer(entry.getKey());
            }


            Map.Entry<String,Connection> activePlayer = clientsConnected.entrySet().iterator().next();;
            gameManager = new GameManager(game, new Player(activePlayer.getKey()));

            clientsConnected.clear();

            System.out.println("new game!");

        }

    }





    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        System.out.println("Server listening on port: " + PORT);

        while(true){
            try {
                Socket socket = serverSocket.accept();
                newConnection(socket);//Blocking call --server code will stop here and waiting for connection


            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }

    // Create connnection, register it and execute via executor
    private void newConnection(Socket socket) {
        Connection connection = new Connection(socket, this);
        registerConnection(connection);
        executor.submit(connection);
    }




    //Methods that has to call the GameManager to handle the request by the client
    //(the message can be whatever)
    //For example: the Username asked at the beginning, a place where to move, ecc
    public void handleMessage(Request message, Connection connection) {

        //Do something if MessageStatus.ERROR
        if (message.getMessageStatus() == MessageStatus.ERROR) {
            connection.sendMessage(
                    new Response("[SERVER]", MessageContent.LOGIN, MessageStatus.CLIENT_ERROR, "Errore nel server")
            );
        }

        //Register Client if MessageContent.LOGIN
        if ( message.getMessageContent() == MessageContent.LOGIN) {

            if(connections.size() == 1) {
                registerClient(message.getMessageSender(), connection);
            } else {
                login(message.getMessageSender(), connection);
            }

        }

        //Everything else is handled by the GameManager
        if(gameManager != null)
            gameManager.handleMessage(message);
        else
            System.out.println("gameManager non inizializzato!");

    }

    // Check if there is a client with same name
    public void login(String username, Connection connection) {
        //UserName taken
        if( clientsConnected.containsKey(username) ) {
            connection.sendMessage(
                    new Response("[SERVER]", MessageContent.LOGIN, MessageStatus.ERROR, "Username taken")
            );
        } else {
            registerClient(username, connection);
        }
    }

    // Register client in clientsConnected
    public void registerClient(String username, Connection connection){

        clientsConnected.put(username, connection);
        connection.setName(username);
        lobby();

        System.out.println("CLIENT REGISTRATO: " + username);

        connection.sendMessage(
                new Response("[SERVER]", MessageContent.LOGIN, MessageStatus.OK, "Connected! Ready to play!")
        );




    }


}
