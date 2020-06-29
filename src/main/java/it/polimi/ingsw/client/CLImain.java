package it.polimi.ingsw.client;


import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.ClientView;

public class CLImain {
    public static void main(String[] args) {

        ClientView clientView = new CLI();
        Thread myThread = new Thread(clientView);
        myThread.start();
    }
}
