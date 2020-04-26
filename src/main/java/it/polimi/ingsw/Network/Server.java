package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Controller.TurnManager;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
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
    private final ServerSocket serverSocket;

    private final ExecutorService executor = Executors.newFixedThreadPool(128);

    private final List<Connection> connections = new ArrayList<Connection>();
    private final Map<String, Connection> clientsConnected = new HashMap<>();
    private final Map<String, Connection> playersInLobby = new HashMap<>();

    // Controller of the game
    GameManager gameManager;
    TurnManager turnManager;

    // field set by a user
    private int lobbySize = 0;




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

        if(clientsConnected.size() >= lobbySize && lobbySize != 0){

            Game game = new Game();
            Player activePlayer = null;

            int loop = 0;

            /*
            Map<String, String> map = new HashMap<String, String>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }*/

            for (Map.Entry<String, Connection> entry : clientsConnected.entrySet()) {

                if(loop < lobbySize){

                    System.out.println(entry.getKey() + " = " + entry.getValue());

                    Player player = new Player(entry.getKey());
                    if(loop == 0) activePlayer = player;

                    RemoteView rvplayer = new RemoteView(player, entry.getValue());
                    game.addObserver(rvplayer);

                    game.addPlayer(player);
                    playersInLobby.put(player.getPlayerName(), entry.getValue());

                    clientsConnected.remove(entry);  // remove entry from clientsConnected
                    loop++;
                }

            }


            /*Iterator it = clientsConnected.entrySet().iterator();

            while (it.hasNext() && loop < lobbySize) {
                Map.Entry<String, Connection> entry = (Map.Entry)it.next();

                Player player = new Player(entry.getKey());
                if(loop == 0) activePlayer = player;

                RemoteView rvplayer = new RemoteView(player, entry.getValue());
                game.addObserver(rvplayer);

                game.addPlayer(player);
                playersInLobby.put(player.getPlayerName(), entry.getValue());

                it.remove();  // remove entry from clientsConnected
                loop++;
            }*/

            // PROBLEMA: hashmap al contrario XC


            System.out.println("new game!");
            gameManager = new GameManager(game, activePlayer);
            turnManager = new TurnManager(game);

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


    // handle message
    public void handleMessage(Request message, Connection connection) {

        switch (message.getMessageContent()){
            case LOGIN -> checkLogin(message, connection);
            case NUM_PLAYER -> setLobbySize(message);

            //Everything else is handled in GameManager or TurnManager
            default -> handleControllerMessage(message);
        }

    }

    private void handleControllerMessage(Request message) {

        if(gameManager != null && turnManager != null){

            switch (message.getMessageDispatcher()) {
                case GAME -> gameManager.handleMessage(message);
                case TURN -> turnManager.handleMessage(message);
            }
        }

        else System.out.println("gameManager non inizializzato!");
    }



    // lobby size
    public void askLobbySize(Connection connection){
        connection.sendMessage(
                new Response("[SERVER]", MessageContent.NUM_PLAYER, MessageStatus.OK, "Lobby size?")
        );
    }

    private void setLobbySize(Request message) {

            lobbySize = Integer.parseInt(message.getClientManagerSays());
            lobby();

    }



    // login
    private void checkLogin(Request message, Connection connection) {

        if (message.getMessageStatus() == MessageStatus.ERROR) {
            connection.sendMessage(
                    new Response("[SERVER]", MessageContent.LOGIN, MessageStatus.CLIENT_ERROR, "Errore nel server")
            );
        }

        else if ( message.getMessageStatus() == MessageStatus.OK) {

            if(connections.size() == 1) {
                registerClient(message.getMessageSender(), connection);
                askLobbySize(connection);

            } else {
                login(message.getMessageSender(), connection);
            }

        }
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

        connection.setName(username);
        clientsConnected.put(username, connection);

        System.out.println("CLIENT REGISTRATO: " + username);

        connection.sendMessage(
                new Response("[SERVER]", MessageContent.LOGIN, MessageStatus.OK, "Connected! Ready to play!")
        );

        lobby();


    }

}
