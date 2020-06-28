package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.ClientRequests.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class MasterControllerTest {

    MasterController masterController;
    Player player1, player2;
    Game game;

    @Before
    public void setUp() {

        game = new Game();



        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        game.addPlayer(player1);
        game.addPlayer(player2);

        masterController = new MasterController(game);
        masterController.init(player1);
    }


    @Test
    public void checkDispatcher() {
        Request request = new Request(player2.getPlayerName(), Dispatcher.SETUP_GAME, RequestContent.CHECK, MessageStatus.OK);
        masterController.dispatcher(request);
        assertEquals(Dispatcher.SETUP_GAME, request.getMessageDispatcher());

        request = new Request(player1.getPlayerName(), Dispatcher.TURN, RequestContent.CHECK, MessageStatus.OK);
        masterController._getActionManager()._setActivePlayer(player1);
        masterController.dispatcher(request);
        assertEquals(Dispatcher.TURN, request.getMessageDispatcher());
    }


    @Test
    public void matchTestFromScratch() {

        game.printGameInfo();

        ArrayList<God> godsChosen = new ArrayList<>();

        Deck deck = game.getDeck();

        godsChosen.add(deck.getGod(0));
        godsChosen.add(deck.getGod(1));

        //il godlike player (player1) invia i god scelti
        masterController.dispatcher(
                new ChoseGodsRequest(player1.getPlayerName(), godsChosen)
        );

        int i=0;
        for (God god: game.getChosenGodsFromDeck()) {
            assertEquals(god, godsChosen.get(i));
            i++;
        }

        //player2 sceglie il suo god
        masterController.dispatcher(
                new PickGodRequest(player2.getPlayerName(), deck.getGod(0))
        );

        assertEquals(deck.getGod(0), player2.getPlayerGod());


        //player1 sceglie il suo god
        masterController.dispatcher(
                new PickGodRequest(player1.getPlayerName(), deck.getGod(1))
        );

        assertEquals(deck.getGod(1), player1.getPlayerGod());



        //player2 piazza i suoi god
        masterController.dispatcher(
                new PlaceWorkerRequest(player2.getPlayerName(), player2.getPlayerWorkers().get(0).getNumber(), new Position(0,0) )
        );

        assertTrue(game.getGameMap().getSquare(0,0).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(0,0), player2.getPlayerWorkers().get(0));


        masterController.dispatcher(
                new PlaceWorkerRequest(player2.getPlayerName(), player2.getPlayerWorkers().get(1).getNumber(), new Position(0,1) )
        );

        assertTrue(game.getGameMap().getSquare(0,1).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(0,1), player2.getPlayerWorkers().get(1));


        //player1 piazza i suoi god
        masterController.dispatcher(
                new PlaceWorkerRequest(player1.getPlayerName(), player1.getPlayerWorkers().get(0).getNumber(), new Position(2,0) )
        );

        assertTrue(game.getGameMap().getSquare(2,0).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,0), player1.getPlayerWorkers().get(0));

        masterController.dispatcher(
                new PlaceWorkerRequest(player1.getPlayerName(), player1.getPlayerWorkers().get(1).getNumber(), new Position(2,1) )
        );

        assertTrue(game.getGameMap().getSquare(2,1).hasWorkerOn());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,1), player1.getPlayerWorkers().get(1));

        game.getGameMap().printBoard();



        //turno del player 2
        masterController.dispatcher(
                new SelectWorkerRequest(player2.getPlayerName(), 1)
        );

        masterController.dispatcher(
                new MoveRequest(player2.getPlayerName(), new Position(0,2))
        );

        assertEquals(game.getGameMap().getWorkerOnSquare(0,2), player2.getPlayerWorkers().get(1));

        masterController.dispatcher(
                new BuildRequest(player2.getPlayerName(), new Position(0,3))
        );

        masterController.dispatcher(
                new EndTurnRequest(player2.getPlayerName())
        );

        //turno del player1
        masterController.dispatcher(
                new SelectWorkerRequest(player1.getPlayerName(), 1)
        );

        masterController.dispatcher(
                new MoveRequest(player1.getPlayerName(), new Position(2,2))
        );

        //il player 2 avendo arthemis puo decidere di muoversi ancora, ma deve specificare al servere che invece vuole costruire
        masterController.dispatcher(
                new EndMoveRequest(player1.getPlayerName())
        );

        masterController.dispatcher(
                new BuildRequest(player1.getPlayerName(), new Position(2,3))
        );

        masterController.dispatcher(
                new EndTurnRequest(player1.getPlayerName())
        );

        game.printGameInfo();


    }


}