package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class MasterControllerTest {

    MasterController masterController;
    SetUpGameManager setUpGameManager;
    Player player1, player2;
    Game game;

    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();

        player1 = new Player("nome1");
        player2 = new Player("nome2");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setUpGameManager = masterController._getSetUpGameController();
    }


    @Test
    public void checkDispatcher() {
        Request request = new Request(player1.getPlayerName(), Dispatcher.SETUP_GAME, MessageContent.CHECK, MessageStatus.OK);
        masterController.dispatcher(request);

        //request = new Request(player.getPlayerName(), Dispatcher.TURN, MessageContent.CHECK, MessageStatus.OK);
        //superMegaController.dispatcher(request);
    }


    @Test
    public void ArtemisPowerTest() {

        String pl1 = player1.getPlayerName();
        String pl2 = player2.getPlayerName();

        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());


        //Aggiungo i god alla partita
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(game.getDeck().getGod(0));
        chosenGod.add(game.getDeck().getGod(1));
        game.setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        player1.setPlayerGod(chosenGod.get(0));
        game.getChosenGodsFromDeck().get(0).setAssigned(true);
        player2.setPlayerGod(chosenGod.get(1));
        game.getChosenGodsFromDeck().get(1).setAssigned(true);

        //giocatore 1 piazza il primo worker
        Worker w1pl1 = player1.getPlayerWorkers().get(0);
        Square sq22 = game.getGameMap().getSquare(new Position(2, 2));

        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        game.getGameMap().printBoard();

        masterController._getActionManager().setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().nextTurn(player1);


        //Tocca al player1
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, w1pl1)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, w1pl2)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        //muovo la seconda volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4,3))
        );
        //costruisco
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 4))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

    }


    public void PoseidonPowerTest() {

        String pl1 = player1.getPlayerName();
        String pl2 = player2.getPlayerName();

        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());


        //Aggiungo i god alla partita
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(game.getDeck().getGod(12));
        chosenGod.add(game.getDeck().getGod(13));
        game.setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        player1.setPlayerGod(chosenGod.get(0));
        game.getChosenGodsFromDeck().get(0).setAssigned(true);
        player2.setPlayerGod(chosenGod.get(1));
        game.getChosenGodsFromDeck().get(1).setAssigned(true);

        //giocatore 1 piazza il primo worker
        Worker w1pl1 = player1.getPlayerWorkers().get(0);
        Square sq22 = game.getGameMap().getSquare(new Position(2, 2));

        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        game.getGameMap().printBoard();

        masterController._getActionManager().setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().nextTurn(player1);


        //Tocca al player1
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, w1pl1)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, w1pl2)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );
 /*       //muovo la seconda volta
        superMegaController.dispatcher(
                new MoveRequest(pl2, new Position(4,3))
        );*/
        //costruisco
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

    }
    @Test
    public void AthenaPowerTest() {

        String pl1 = player1.getPlayerName();
        String pl2 = player2.getPlayerName();

        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());


        //Aggiungo i god alla partita
        ArrayList<God> chosenGod = new ArrayList<>();
        chosenGod.add(game.getDeck().getGod(2));
        chosenGod.add(game.getDeck().getGod(1));
        game.setChosenGodsFromDeck(chosenGod);

        //assegno i god ai rispettivi giocatori
        player1.setPlayerGod(chosenGod.get(0));
        Game.getInstance().getChosenGodsFromDeck().get(1).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        player2.setPlayerGod(chosenGod.get(1));
        game.getChosenGodsFromDeck().get(1).setAssigned(true);

        //giocatore 1 piazza il primo worker
        Worker w1pl1 = player1.getPlayerWorkers().get(0);
        Square sq22 = game.getGameMap().getSquare(new Position(2, 2));

        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        game.getGameMap().printBoard();

        masterController._getActionManager().setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().nextTurn(player1);


        //Tocca al player1
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, w1pl1)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, w1pl2)
        );
        //lo muovo
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        game.getGameMap().printBoard();

        // 2 TUNRO PLAYER ATHENA
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, w1pl1)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(0, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, w1pl2)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 3))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );


    }

}