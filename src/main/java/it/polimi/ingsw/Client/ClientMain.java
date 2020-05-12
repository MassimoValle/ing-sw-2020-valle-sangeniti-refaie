package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Client.View.Gui.GUI;
import it.polimi.ingsw.Network.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class ClientMain {

    private static final Scanner consoleIn = new Scanner(System.in);
    private static final PrintStream consoleOut = new PrintStream(System.out, true);

    public static void main(String[] args) {

        ClientView clientView = welcome();

        clientView.start();

    }

    private static ClientView welcome() {

        consoleOut.println("\n" +
                "Welcome to santorini!\n" +
                "What would you like to play with?\n" +
                "1) CLI\n" +
                "2) GUII\n" );


        int choice = 0;
        ClientView clientView = null;

        do {

            if (consoleIn.hasNextLine()) {
                choice = Integer.parseInt(consoleIn.nextLine());

                if (choice == 1) {
                    clientView =  new CLI();
                } else if (choice == 2) {
                    clientView =  new GUI();
                }

            }

        } while (clientView == null);

        return clientView;

    }
}
