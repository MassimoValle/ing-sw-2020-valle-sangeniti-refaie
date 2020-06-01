package it.polimi.ingsw.Client;


import it.polimi.ingsw.Client.Model.ImageDecorator;
import it.polimi.ingsw.Client.View.Cli.CLI;
import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.scene.image.Image;

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


        ImageDecorator imageDecorator = new ImageDecorator(new Square(0,0), new Image("/imgs/board/1row/0x0.png"));
        ((Square) imageDecorator.getObject()).freeSquare();

    }
}
