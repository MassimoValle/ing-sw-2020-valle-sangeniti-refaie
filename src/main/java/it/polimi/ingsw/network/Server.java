package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.clientrequests.LoginRequest;
import it.polimi.ingsw.network.message.clientrequests.SetPlayersRequest;
import it.polimi.ingsw.network.message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.clientrequests.Request;
import it.polimi.ingsw.server.view.RemoteView;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {

    private static final int PORT= 8080;
    private final ServerSocket serverSocket;

    private final ExecutorService executor = Executors.newFixedThreadPool(128);

    private static final List<Connection> connections = new ArrayList<>();
    private final Map<String, Connection> clientsConnected = new HashMap<>();
    private static final Map<MasterController, Map<String, Connection>> rooms = new HashMap<>();

    public static final Logger LOGGER = Logger.getLogger("Server");

    int temp_lobbySize = -1;
    boolean lobbySizeAsked = false;


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    private static synchronized void deregisterConnection(Connection c){

        connections.remove(c);

    }



    // if there is an exception by client connection
    public static void clientConnectionException(Connection c) {

        deregisterConnection(c);

        MasterController masterController = searchConnectionInRooms(c);

        assert masterController != null;
        masterController.clientConnectionException();

    }

    private static MasterController searchConnectionInRooms(Connection c) {

        for (Map.Entry<MasterController, Map<String, Connection>> entry : rooms.entrySet()){

            for (Map.Entry<String, Connection> entry1 : entry.getValue().entrySet()){

                if(entry1.getValue().equals(c))
                    return entry.getKey();

            }
        }

        return null;
    }





    private synchronized void setUpLobbySize() {

        // richiede al primo utente nella clientsConnected il numero di giocatori
        if(!lobbySizeAsked) {

            Map.Entry<String, Connection> entry = clientsConnected.entrySet().iterator().next();
            Connection value = entry.getValue();
            askLobbySize(value);
        }
        else if(temp_lobbySize > 1) // nel caso in cui temp_lobbySize = 3 ma ci sono 2 giocatori in clientsConnected, quando arriva il 3o fa partire la lobby
            initLobby();
    }

    // lobby size
    public synchronized void askLobbySize(Connection connection){
        connection.sendMessage(
                new ServerResponse(ResponseContent.NUM_PLAYER, MessageStatus.OK, "Lobby size?")
        );

        lobbySizeAsked = true;
    }

    private synchronized void setLobbySize(SetPlayersRequest request) {

        temp_lobbySize = Integer.parseInt(request.getHowMany());
        initLobby();

        if(clientsConnected.size() > 1){
            setUpLobbySize();
        }

    }


    /**
     * full up lobby and start game
     */
    public synchronized void initLobby() {

        if(clientsConnected.size() >= temp_lobbySize){

            Game game = new Game();
            MasterController masterController = new MasterController(game);
            Player activePlayer = null;

            int loop = 0;

            Map<String, Connection> playersInLobby = new HashMap<>();


            Iterator it = clientsConnected.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();

                if(loop < temp_lobbySize){

                    System.out.println(pair.getKey() + " = " + pair.getValue());

                    Player player = new Player((String) pair.getKey());
                    if(loop == 0) activePlayer = player;

                    RemoteView rvPlayer = new RemoteView(player, (Connection) pair.getValue());
                    game.addObserver(rvPlayer);
                    rvPlayer.setController(masterController);

                    game.addPlayer(player);
                    playersInLobby.put(player.getPlayerName(), (Connection) pair.getValue());

                    loop++;
                }

                it.remove(); // remove entry from clientsConnected
            }

            rooms.put(masterController, playersInLobby);
            temp_lobbySize = -1;
            lobbySizeAsked = false;

            System.out.println("new game!");

            final Player finalActivePlayer = activePlayer;
            new Thread(() -> masterController.init(finalActivePlayer)).start();

        }

    }



    // game over
    public static void cleanLobby(MasterController masterController){

        removeConnectionInRooms(masterController);
    }

    private static void removeConnectionInRooms(MasterController masterController){

        Map<String, Connection> room = rooms.get(masterController);

        for (Map.Entry<String, Connection> entryLobby : room.entrySet()){

            Connection conn = entryLobby.getValue();
            deregisterConnection(conn);

        }

        rooms.remove(masterController);

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


    // Register new connection
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

    // Check if is the first connection or not
    private synchronized void checkLogin(LoginRequest request, Connection connection) {

        login(request.getMessageSender(), connection);

        setUpLobbySize();

        /*if(connections.size() == 1) {
            registerClient(request.getMessageSender(), connection);

        } else {
            login(request.getMessageSender(), connection);

            setUpLobbySize();

        }*/

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

        clientsConnected.put(username, connection);

        System.out.println("Registered client:  " + username);

        connection.sendMessage(
                new ServerResponse(ResponseContent.LOGIN, MessageStatus.OK, "Connected! Ready to play!")
        );



    }

}
