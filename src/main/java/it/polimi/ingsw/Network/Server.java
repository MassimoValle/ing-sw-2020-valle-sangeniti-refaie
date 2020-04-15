package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameManager;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageStatus;
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
    private Map<Connection, Connection> playingConnection = new HashMap<>();
    private Map<String, Connection> playersInLobby = new HashMap<>();

    private int clientsNum;

    private GameManager gameManager;


    //Register connection
    private synchronized void registerConnection(Connection c){
        connections.add(c);
    }

    //Deregister connection
    public synchronized void deregisterConnection(Connection c){
        connections.remove(c);
        Connection opponent = playingConnection.get(c);
        if(opponent != null){
            opponent.closeConnection();
            playingConnection.remove(c);
            playingConnection.remove(opponent);
            //Iterator<String> iterator = waitingConnection.keySet().iterator();
            //while(iterator.hasNext()){
            //    if(waitingConnection.get(iterator.next())==c){
            //        iterator.remove();
            //    }
            //}
        }
    }

    public synchronized void lobby(Connection c, String name){
        clientsConnected.put(name, c);
        if(clientsConnected.size() == 2){
            List<String> keys = new ArrayList<>(clientsConnected.keySet());
            Connection c1 = clientsConnected.get(keys.get(0));
            Connection c2 = clientsConnected.get(keys.get(1));
            RemoteView player1 = new RemoteView(new Player(keys.get(0)), keys.get(1), c1, true);
            RemoteView player2 = new RemoteView(new Player(keys.get(1)), keys.get(0), c2, false);
            Game game1 = new Game();
            //Controller controller = new Controller(model);
            game1.addObserver(player1);
            game1.addObserver(player2);
            //player1.addObserver(controller);
            //player2.addObserver(controller);
            playingConnection.put(c1, c2);
            playingConnection.put(c2, c1);
            clientsConnected.clear();
        }

    }

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);

        this.clientsNum = 0;
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

    private void newConnection(Socket socket) {
        clientsNum++;
        Connection connection = new Connection(socket, this);
        registerConnection(connection);
        executor.submit(connection);
    }



    //Methods that has to call the GameManager to handle the request by the client
    //(the message can be whatever)
    //For exemple: the Username asked at the beginning, a place where to move, ecc
    public void handleMessage(Message message) {
        gameManager.handleMessage(message);
        switch (message.getMessageContent()) {
            case LOGIN:
                System.out.println("Il client " + message.getMessageSender() + "ha inviato lo username");
                System.out.println("Username scelto: " + message.getMessageSender());


            case CHECK:
                System.out.println(message.toString());
                //Invio sulla connessione aperta il messaggio di risposta
                connections.get(0).sendMessage(
                        new Response("Hai inviato un messaggio di tipo CHECK", MessageStatus.ERROR)
                );
        }
    }

    //Metodo che controlla se il nome immesso dal player è valido/già in uso ecc
    public void login(String username, Connection connection) {
        //UserName taken
        if( playersInLobby.containsKey(username) ) {
            connection.sendMessage(
                    new Response("Username taken", MessageStatus.ERROR)
            );
        } else {
            playersInLobby.put(username, connection);

            connection.sendMessage(
                    new Response("Connected! Ready to play!", MessageStatus.OK)
            );
        }
    }


}
