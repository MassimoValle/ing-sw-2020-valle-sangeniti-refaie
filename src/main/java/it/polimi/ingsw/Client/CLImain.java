package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CLImain {

    //private static final ExecutorService executor = Executors.newFixedThreadPool(128);

    public static void main(String[] args) {

        ClientView clientView = new CLI();
        Thread myThread = new Thread(clientView);
        myThread.start();
        //executor.submit(clientView);
        //clientView.run();

    }
}
