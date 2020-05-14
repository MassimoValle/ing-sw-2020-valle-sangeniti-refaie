package it.polimi.ingsw.Client.View.Cli;

import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Position;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI extends ClientView {

    private final Scanner consoleIn;
    private final PrintStream consoleOut;

    public CLI(){
        consoleIn = new Scanner(System.in);
        consoleOut = new PrintStream(System.out, true);

    }


    @Override
    public void start() {

        String ip = askIpAddress();

        Client client = new Client(ip, 8080, this);

        try {
            client.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public String askIpAddress() {
        consoleOut.print("IP Address: ");

        String ip = consoleIn.nextLine();

        if (ip.equals(""))
            return "localhost";

        return ip;
    }


    @Override
    public String askUserName() {

        String username = "";
        consoleOut.println("Enter your username: ");

        //Setto il nome utente
        do {
            if (consoleIn.hasNextLine()) {
                username = consoleIn.nextLine();
            }
        } while (username == null);

        consoleOut.println("YOUR USERNAME: " + username);

        return username;
    }

    @Override
    public int askNumbOfPlayer() {
        int input;
        do{
            consoleOut.print("lobby size [MIN: 2, MAX: 3]: ");
            input = Integer.parseInt(consoleIn.nextLine());
        }while (input != 2 && input != 3);

        return input;
    }

    @Override
    public ArrayList<God> selectGodsFromDeck(int howMany, String serverSays) {

        Deck deck = Deck.getInstance();
        consoleOut.println(deck.toString());


        ArrayList<God> godChoosen = new ArrayList<>();

        consoleOut.print("choose " + serverSays + " index: ");


        int index;

        for (int i = 0; i < howMany; i++) {

            do{
                index = Integer.parseInt(consoleIn.nextLine());
            }while (index >= deck.size());

            godChoosen.add(deck.getGod(index));
        }

        return godChoosen;
    }

    @Override
    public God pickFromChosenGods(ArrayList<God> hand) {

        consoleOut.println(hand.toString());
        int index;

        do {

            consoleOut.print("select index: ");
            index = Integer.parseInt(consoleIn.nextLine());

        }while (index >= hand.size());

        return hand.get(index);
    }

    @Override
    public Position placeWorker(String worker) {
        consoleOut.print("MY WORKER: ");
        consoleOut.println(worker);

        consoleOut.print("row: ");
        int row = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        consoleOut.print("col: ");
        int col = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        return new Position(row, col);
    }

    @Override
    public void debug(Response response) {

        printMessageFromServer(response);

    }


    private void printMessageFromServer(Response message){
        String out = "#### [SERVER] ####\n";
        out += "Message content: " + message.getResponseContent() + "\n";
        out += "Message status: " + message.getMessageStatus() + "\n";
        out += "Message value: " + message.getGameManagerSays() + "\n";
        out += "________________\n";

        consoleOut.println(out);
    }
}
