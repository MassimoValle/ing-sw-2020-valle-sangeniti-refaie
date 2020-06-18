package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;

public class CLImain {
    public static void main(String[] args) {

        ClientView clientView = new CLI();
        Thread myThread = new Thread(clientView);
        myThread.start();
    }
}
