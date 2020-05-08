package it.polimi.ingsw.Network.Client;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.Requests.PickGodRequest;
import it.polimi.ingsw.Network.Message.Requests.PlaceWorkerRequest;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Responses.ShowDeckResponse;
import it.polimi.ingsw.Network.Message.Responses.PickGodResponse;
import it.polimi.ingsw.Network.Message.Responses.PlaceWorkerResponse;
import it.polimi.ingsw.Network.Message.Responses.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientManager implements ClientManagerListener{

    private static ClientManager instance = null;

    private static String username;
    public String getUsername() {
        return username;
    }

    private final Scanner consoleIn;
    private final PrintStream consoleOut;





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

        printMessageFromServer((Response) message);

        if(((Response) message).getResponseContent() != null) {
            switch (((Response) message).getResponseContent()){

                case LOGIN:
                    if(message.getMessageStatus() == MessageStatus.OK) return;
                    else login();
                    break;

                case NUM_PLAYER:
                    chooseNumPlayers();
                    break;

                case SHOW_DECK:
                    chooseGodFromDeck((ShowDeckResponse) message);
                    break;

                case PICK_GOD:
                    pickGod((PickGodResponse) message);
                    break;

                case PLACE_WORKER:
                    placeWorker((PlaceWorkerResponse) message);
                    break;

                case START_TURN:
                    break;
                case SELECT_WORKER:
                    break;
                case MOVE_WORKER:
                    break;
                case BUILD:
                    break;
                case END_TURN:
                    break;
                case PLAYER_WON:
                    break;
                default: CHECK:
                    printMessageFromServer((Response) message);
                    break;
            }
        }

    }





    // functions
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
                        new Request(username, Dispatcher.SETUP_GAME, RequestContent.LOGIN, MessageStatus.OK, username)
                );
            }catch (IOException e){
                e.printStackTrace();
            }

    }

    private void chooseNumPlayers(){
        String input;
        do{
            consoleOut.print("lobby size [MIN: 2, MAX: 3]: ");
            input = consoleIn.nextLine();
        }while (!(input.equals("2") || input.equals("3")));

        try {
            Client.sendMessage(
                    new Request(username, Dispatcher.SETUP_GAME, RequestContent.NUM_PLAYER, MessageStatus.OK, input)
            );
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void chooseGodFromDeck(ShowDeckResponse response){

        Deck deck = Deck.getInstance();
        String serverSays = response.getGameManagerSays();
        int howMany = response.getHowMany();

        consoleOut.println(deck.toString());
        ArrayList<God> godChoosen = new ArrayList<>();

        consoleOut.println("choose " + serverSays + " index:");

        //FIX

        for (int i = 0; i < howMany; i++) {
            int index = Integer.parseInt(consoleIn.nextLine());
            godChoosen.add(deck.getGod(index));
        }

        try {

            Client.sendMessage(
                    new ChoseGodsRequest(username, godChoosen)
            );

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    private void pickGod(PickGodResponse message) {

        ArrayList<God> hand = message.getGods();

        consoleOut.println(hand.toString());

        int index = Integer.parseInt(consoleIn.nextLine());

        God picked = hand.get(index);


        try {
            Client.sendMessage(
                    new PickGodRequest(username, picked)
            );
        }catch (IOException e){
            e.printStackTrace();
        }



    }

    private void placeWorker(PlaceWorkerResponse message) {

        consoleOut.println("MY WORKER: ");
        message.getWorker().toString();

        consoleOut.print("row: ");
        int row = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        consoleOut.print("col: ");
        int col = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        Position p = new Position(row, col);

        try {
            Client.sendMessage(
                    new PlaceWorkerRequest(username, message.getWorker(), p)
            );
        }catch (IOException e){
            e.printStackTrace();
        }

    }





    @Override
    public void update(Response response) { }





    // test
    private void printMessageFromServer(Response message){
        String out = "#### [SERVER] ####\n";
        out += "Message content: " + message.getResponseContent() + "\n";
        out += "Message status: " + message.getMessageStatus() + "\n";
        out += "Message value: " + message.getGameManagerSays() + "\n";
        out += "________________\n";

        consoleOut.println(out);
    }
}
