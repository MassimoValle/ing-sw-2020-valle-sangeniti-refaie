package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.Request;

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
                    printMessageFromServer((Response) message);
                    if(message.getMessageStatus() == MessageStatus.ERROR){
                        login();
                    }
                    break;
                case NUM_PLAYER:

                    consoleOut.print("lobby size [MIN: 2, MAX: 3]: ");
                    String input;
                    do{
                        input = consoleIn.nextLine();
                    }while (!(input.equals("2") || input.equals("3")));

                    try {
                        Client.sendMessage(
                                new Request(username, Dispatcher.SETUP_GAME, MessageContent.NUM_PLAYER, MessageStatus.OK, input)
                        );
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    break;

                case GODS_CHOSE:
                    printMessageFromServer((Response) message);
                    break;

                case GOD_SELECTION:
                    // printo la lista di gods
                    // scelgo che gods voglio
                    // invio gli indici dei god scelti al server
                    chooseGods((Response) message);
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
                    printMessageFromServer((Response) message);
                    break;
            }
        }

    }

    private void printMessageFromServer(Response message){
        String out = "#### [SERVER] ####\n";
        out += "Message content: " + message.getMessageContent() + "\n";
        out += "Message status: " + message.getMessageStatus() + "\n";
        out += "Message value: " + message.getGameManagerSays() + "\n";
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
                        new Request(username, Dispatcher.SETUP_GAME, MessageContent.LOGIN, MessageStatus.OK, username)
                );
            }catch (IOException e){
                e.printStackTrace();
            }

    }

    private void chooseGods(Response message)
    {
        Deck deck = null; // = message.getGameManagerSays();

        // print deck

        String input = consoleIn.nextLine();


        /*
        String[] gods = input.split(" ");
        ArrayList<God> godChosen = new ArrayList<>();

        for (int i = 0; i < gods.length; i++) {
            godChosen.add(deck.getGod(Integer.parseInt(gods[i])));
        }
        */


        try {
            Client.sendMessage(
                    new Request(username, Dispatcher.SETUP_GAME, MessageContent.GOD_SELECTION, MessageStatus.OK, input)
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
                    new Request(getUsername(), Dispatcher.SETUP_GAME, MessageContent.CHECK, MessageStatus.OK, inputLine)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
