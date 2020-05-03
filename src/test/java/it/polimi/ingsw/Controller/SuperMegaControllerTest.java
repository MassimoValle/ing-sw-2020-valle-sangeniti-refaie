package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.Requests.Request;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class SuperMegaControllerTest {

    SuperMegaController superMegaController;
    SetUpGameController setUpGameController;
    Game game;
    Player player1, player2;

    @Before
    public void setUp() {

        player1 = new Player("client1");
        player2 = new Player("client2");
        game = new Game();
        game.addPlayer(player1);
        game.addPlayer(player2);

        superMegaController = new SuperMegaController(game, player1);
        setUpGameController = superMegaController._getSetUpGameController();
    }


    @Test
    public void checkDispatcher(){
        Request request = new Request(player1.getPlayerName(), Dispatcher.SETUP_GAME, MessageContent.CHECK, MessageStatus.OK);
        superMegaController.dispatcher(request);

        //request = new Request(player.getPlayerName(), Dispatcher.TURN, MessageContent.CHECK, MessageStatus.OK);
        //superMegaController.dispatcher(request);
    }



    @Test
    public void gameFlowTest() {

        assertEquals(2, superMegaController.getGameInstance().getNumberOfPlayers());



        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(superMegaController.getGameInstance().getDeck().getGod(0));
        chosenGod.add(superMegaController.getGameInstance().getDeck().getGod(1));
        chosenGod.add(superMegaController.getGameInstance().getDeck().getGod(2));



        //ATTENDO UNA CHOSEGODSREQUEST CONTENENTI I GOD CHE IL GIOCATORE HA SCELTO PER LA PARTITA
        superMegaController.dispatcher(
                new ChoseGodsRequest(player1.getPlayerName(), chosenGod)
        );

        //ADESSO A TURNO I GIOCATORI SCELGONO IL GOD TRA QUELLI SCELTI DAL GODLIKE PLAYER

        /*
        //PRIMA MASSIMO
        gameManager.handleMessage(
                new PickGodRequest(massimo.getPlayerName(), chosenGod.get(0))
        );

        //POI MAGDY
        gameManager.handleMessage(
                new PickGodRequest(magdy.getPlayerName(), chosenGod.get(1))
        );

        //INFINE SIMONE
        gameManager.handleMessage(
                new PickGodRequest(simone.getPlayerName(), chosenGod.get(2))
        );

        //gamestate == FILLING_BOARD
        //A TURNO I GIOCATORI SELEZIONANO E PIAZZANO I WORKER

        //MASSIMO SELEZIONA...
        gameManager.handleMessage(
                new SelectWorkerRequest(massimo.getPlayerName(), massimo.getPlayerWorkers().get(0))
        );

        //E PIAZZA IL WORKER N°1
        gameManager.handleMessage(
                new PlaceWorkerRequest(massimo.getPlayerName(), massimo.getPlayerWorkers().get(0), new Position(0,0))
        );

        //MASSIMO SELEZIONA...
        gameManager.handleMessage(
                new SelectWorkerRequest(massimo.getPlayerName(), massimo.getPlayerWorkers().get(1))
        );

        //E PIAZZA IL WORKER N°2
        gameManager.handleMessage(
                new PlaceWorkerRequest(massimo.getPlayerName(), massimo.getPlayerWorkers().get(1), new Position(0,1))
        );

        //Tocca a Magdy piazzare i propri worker
        gameManager.handleMessage(
                new SelectWorkerRequest(magdy.getPlayerName(), magdy.getPlayerWorkers().get(0))
        );
        gameManager.handleMessage(
                new PlaceWorkerRequest(magdy.getPlayerName(), magdy.getPlayerWorkers().get(0), new Position(1,0))
        );
        gameManager.handleMessage(
                new SelectWorkerRequest(magdy.getPlayerName(), magdy.getPlayerWorkers().get(1))
        );
        gameManager.handleMessage(
                new PlaceWorkerRequest(magdy.getPlayerName(), magdy.getPlayerWorkers().get(1), new Position(1,1))
        );

        //Tocca a simone piazzare i worker
        gameManager.handleMessage(
                new SelectWorkerRequest(simone.getPlayerName(), simone.getPlayerWorkers().get(0))
        );
        gameManager.handleMessage(
                new PlaceWorkerRequest(simone.getPlayerName(), simone.getPlayerWorkers().get(0), new Position(2,0))
        );
        gameManager.handleMessage(
                new SelectWorkerRequest(simone.getPlayerName(), simone.getPlayerWorkers().get(1))
        );
        gameManager.handleMessage(
                new PlaceWorkerRequest(simone.getPlayerName(), simone.getPlayerWorkers().get(1), new Position(2,1))
        );

        //WORKER PIAZZATI, INIZIA LA PARTITA VERA E PROPRIA
        gameManager.getGameInstance().getGameMap().printBoard();

        //TURNO DI MAX, SELEZIONA UN WORKER...
        gameManager.handleMessage(
                new SelectWorkerRequest(massimo.getPlayerName(), massimo.getPlayerWorkers().get(1))
        );

        //LO MUOVE...
        gameManager.handleMessage(
                new MoveRequest(massimo.getPlayerName(), new Position(0, 2))
        );

        //E COSTRUISCE
        gameManager.handleMessage(
                new BuildRequest(massimo.getPlayerName(), new Position(0,3))
        );

        gameManager.getGameInstance().getGameMap().printBoard();


        //TURNO DI MAGDY, SELEZIONA UN WORKER...
        gameManager.handleMessage(
                new SelectWorkerRequest(magdy.getPlayerName(), magdy.getPlayerWorkers().get(1))
        );

        //LO MUOVE...
        gameManager.handleMessage(
                new MoveRequest(magdy.getPlayerName(), new Position(1, 2))
        );

        //E COSTRUISCE
        gameManager.handleMessage(
                new BuildRequest(magdy.getPlayerName(), new Position(1,3))
        );

        gameManager.getGameInstance().getGameMap().printBoard();

        //TURNO DI SIMONE, SELEZIONA UN WORKER...
        gameManager.handleMessage(
                new SelectWorkerRequest(simone.getPlayerName(), simone.getPlayerWorkers().get(1))
        );

        //LO MUOVE...
        gameManager.handleMessage(
                new MoveRequest(simone.getPlayerName(), new Position(2, 2))
        );

        //E COSTRUISCE
        gameManager.handleMessage(
                new BuildRequest(simone.getPlayerName(), new Position(2,3))
        );

        gameManager.getGameInstance().getGameMap().printBoard();
        */

    }

}