package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Message.ClientRequests.LoginRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.SetPlayersRequest;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;
import it.polimi.ingsw.Server.View.RemoteView;

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
    MasterController masterController;

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


        if(lobbySize == 0){
            askLobbySize(connections.get(0));
        }
    }



    // full up lobby and start game
    public synchronized void lobby() {

        if(clientsConnected.size() >= lobbySize && lobbySize != 0){

            Game game = Game.getInstance();
            Player activePlayer = null;

            int loop = 0;


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

            // PROBLEMA: hashmap al contrario XC


            System.out.println("new game!");
            masterController = new MasterController(game, activePlayer);

        }

    }





    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public void run(){
        System.out.println("Server listening on port: " + PORT);

        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                newConnection(socket);


            } catch (IOException e){
                System.err.println("Connection error!");
            }
        }
    }


    private void newConnection(Socket socket) {
        Connection connection = new Connection(socket, this);
        registerConnection(connection);
        executor.submit(connection);
    }



    // handle message
    public synchronized void handleMessage(Request request, Connection connection) {


        switch (request.getRequestContent()){
            case LOGIN -> checkLogin((LoginRequest) request, connection);
            case NUM_PLAYER -> setLobbySize((SetPlayersRequest) request);

        }

    }



    // lobby size
    public void askLobbySize(Connection connection){
        connection.sendMessage(
                new ServerResponse(ResponseContent.NUM_PLAYER, MessageStatus.OK, "Lobby size?")
        );
    }

    private void setLobbySize(SetPlayersRequest request) {

            lobbySize = Integer.parseInt(request.getHowMany());
            lobby();

    }


    private synchronized void checkLogin(LoginRequest request, Connection connection) {


        if(connections.size() == 1) {
            registerClient(request.getMessageSender(), connection);
            askLobbySize(connection);

        } else {
            login(request.getMessageSender(), connection);
        }


    }

    // Check if there is a client with same name
    public synchronized void login(String username, Connection connection) {
        //UserName taken
        if( clientsConnected.containsKey(username) ) {
            connection.sendMessage(
                    new ServerResponse(ResponseContent.LOGIN, MessageStatus.ERROR, "Username taken")
            );
        } else {
            registerClient(username, connection);
        }
    }

    // Register client in clientsConnected
    public synchronized void registerClient(String username, Connection connection){

        connection.setName(username);
        clientsConnected.put(username, connection);

        System.out.println("Registered client:  " + username);

        connection.sendMessage(
                new ServerResponse(ResponseContent.LOGIN, MessageStatus.OK, "Connected! Ready to play!")
        );

        lobby();


    }

}
