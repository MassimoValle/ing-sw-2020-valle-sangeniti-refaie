package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.MessageStatus;

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

        if(message.getMessageContent() != null) {
            switch (message.getMessageContent()){
                case FIRST_CONNECTION:
                    break;
                case CONNECTION_RESPONSE:
                    break;
                case LOGIN:
                    printMessageFromServer(message);
                    if(message.getMessageStatus() == MessageStatus.ERROR){
                        login();
                    }

                    // do something

                    break;
                case GOD_SELECTION:
                    // printo la lista di gods
                    // scelgo che gods voglio
                    // invio gli indici dei god scelti al server
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
                    printMessageFromServer(message);
                    break;
            }
        }

    }

    private void printMessageFromServer(Message message){
        String out = "#### [SERVER] ####\n";
        out += "Message content: " + message.getMessageContent() + "\n";
        out += "Message status: " + message.getMessageStatus() + "\n";
        out += "Message value: " + message.getMessage() + "\n";
        out += "________________\n";

        consoleOut.println(out);
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

    public void login(){
            String username = askUsername();

            try {
                Client.sendMessage(
                        new Message(username, MessageContent.LOGIN, username)
                );
            }catch (IOException e){
                e.printStackTrace();
            }

    }

    public void debug() {
        // #DEBUG
        try {
            consoleOut.print("DEBUG: ");
            String inputLine = consoleIn.nextLine();
            Client.sendMessage(
                    new Message(getUsername(), MessageContent.CHECK, inputLine)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
