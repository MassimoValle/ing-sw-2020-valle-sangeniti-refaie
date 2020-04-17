package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ClientManager {

    private static ClientManager instance = null;

    private static String username;
    public String getUsername() {
        return username;
    }

    private Scanner consoleIn;
    private PrintStream consoleOut;

    private ClientManager(){

        consoleIn = new Scanner(System.in);
        consoleOut = new PrintStream(System.out, true);

    }

    public static synchronized ClientManager getInstance() {

        if (instance == null) {
            instance = new ClientManager();
        }
        return instance;
    }

    public void handleMessageFromServer(Message message){

        String out = "SERVER: ";
        consoleOut.println(out + message.getMessageContent());

        switch (message.getMessageContent()){
            case FIRST_CONNECTION:
                break;
            case CONNECTION_RESPONSE:
                break;
            case LOGIN:
                break;
            case YOUR_TURN:
                break;
            case WORKER_CHOSEN:
                break;
            case WORKER_MOVED:
                break;
            case PLAYERS_HAS_BUILT:
                break;
            case END_OF_TURN:
                break;
            default: CHECK:
                break;
        }

    }

    //Ask the user to insert a username
    public String askUsername() {
        consoleOut.print("Enter your username: ");

        //Setto il nome utente
        do {
            if (consoleIn.hasNextLine()) {
                String currentUsername;
                currentUsername = consoleIn.nextLine();

                username = currentUsername;
            }
        } while (username == null);

        consoleOut.println("YOUR USERNAME: " + username);
        //consoleOut.println("Have a nice day");

        return username;

    }

    public void doTurn() throws IOException {

        // #YOUR_TURN
        beginPhase();
        // #WORKER_CHOSEN
        chooseWorker();
        // #WORKER_MOVED
        moveWorker();
        // #PLAYERS_HAS_BUILT
        buildWithWorker();
        // #END_OF_TURN
        endPhase();



        // #DEBUG
        debug();
    }

    private void debug() throws IOException {
        consoleOut.print("DEBUG: ");
        String inputLine = consoleIn.nextLine();
        Client.sendMessage(
                new Message(getUsername(), MessageContent.CHECK, inputLine)
        );

        Client.receiveMessage();
    }

    private void endPhase() {
    }

    private void buildWithWorker() {
    }

    private void moveWorker() {
    }

    private void chooseWorker() {
    }

    private void beginPhase() {
    }
}
