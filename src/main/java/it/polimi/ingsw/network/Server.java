package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Enum.RequestContent;
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

    int tempLobbySize = -1;
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

        if (connections.contains(c))
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
        else if(tempLobbySize > 1) // nel caso in cui temp_lobbySize = 3 ma ci sono 2 giocatori in clientsConnected, quando arriva il 3o fa partire la lobby
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

        tempLobbySize = Integer.parseInt(request.getHowMany());

        Server.LOGGER.info("A new " + tempLobbySize + " players lobby has been created!");
        initLobby();

        if(clientsConnected.size() > 1){
            setUpLobbySize();
        }

    }


    /**
     * full up lobby and start game
     */
    public synchronized void initLobby() {

        if(clientsConnected.size() >= tempLobbySize){

            Game game = new Game();
            MasterController masterController = new MasterController(game);
            Player activePlayer = null;

            int loop = 0;

            Map<String, Connection> playersInLobby = new HashMap<>();


            Iterator < Map.Entry <String, Connection>> it = clientsConnected.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry <String, Connection> pair = it.next();

                if(loop < tempLobbySize){

                    Server.LOGGER.info(pair.getKey() + " = " + pair.getValue());

                    Player player = new Player( pair.getKey());
                    if(loop == 0) activePlayer = player;

                    RemoteView rvPlayer = new RemoteView(player, pair.getValue());
                    game.addObserver(rvPlayer);
                    rvPlayer.setController(masterController);

                    game.addPlayer(player);
                    playersInLobby.put(player.getPlayerName(), pair.getValue());

                    loop++;
                }

                it.remove(); // remove entry from clientsConnected
            }

            rooms.put(masterController, playersInLobby);
            tempLobbySize = -1;
            lobbySizeAsked = false;

            Server.LOGGER.info("New game created!");

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
        Server.LOGGER.info("Server listening on port: " + PORT);

        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverSocket.accept();
                newConnection(socket);


            } catch (IOException e){
                Server.LOGGER.info("Connection error!");
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

        if (request.getRequestContent() == RequestContent.LOGIN) {
            checkLogin((LoginRequest) request, connection);
        } else if (request.getRequestContent() == RequestContent.NUM_PLAYER) {
            setLobbySize((SetPlayersRequest) request);
        }

    }

    // Check if is the first connection or not
    private synchronized void checkLogin(LoginRequest request, Connection connection) {

        login(request.getMessageSender(), connection);

        setUpLobbySize();

    }

    // Check if there is a client with same name
    public synchronized void login(String username, Connection connection) {
        //UserName taken
        if( clientsConnected.containsKey(username) || username.equals("") ) {
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

        Server.LOGGER.info("Registered client:  " + username);

        connection.sendMessage(
                new ServerResponse(ResponseContent.LOGIN, MessageStatus.OK, "Connected! Ready to play!")
        );



    }

}
