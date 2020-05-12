package it.polimi.ingsw.Client.View.Cli;

import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Client;

import java.io.IOException;
import java.io.PrintStream;
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
    public void showDeck() {

    }

    @Override
    public void showChosenGods() {

    }

    @Override
    public void pickFromChosenGods() {

    }

    @Override
    public void placeWorker() {

    }
}
