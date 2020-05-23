package it.polimi.ingsw.Controller.GodsPowerTest;


import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ApolloPowerTest {


    MasterController masterController;
    Player player1, player2;
    Game game;



    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();


        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
    }

    @Test
    public void ApolloPower() {

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
        Game.getInstance().getChosenGodsFromDeck().get(0).setAssigned(true);
        //game.getChosenGodsFromDeck().get(0).setAssigned(true);
        player2.setPlayerGod(chosenGod.get(1));
        game.getChosenGodsFromDeck().get(1).setAssigned(true);

        //giocatore 1 piazza il primo worker
        Worker w1pl1 = player1.getPlayerWorkers().get(0);
        Square sq22 = game.getGameMap().getSquare(new Position(2, 2));

        w1pl1.setPosition(new Position(2, 2));
        sq22.setWorkerOn(w1pl1);
        w1pl1.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);


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


        System.out.println("tocca al player1, primo turno");
        //tocca al player1
        //seleziona un worker...
        MasterController.dispatcher( new SelectWorkerRequest(pl1, 0) );
        //test potere di apollo quando i 2 worker sono sullo stesso livello
        MasterController.dispatcher(new MoveRequest(pl1, new Position(3,3)));
        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), w2pl2);
        game.getGameMap().printBoard();
        //player1 costruisce
        MasterController.dispatcher(new BuildRequest(pl1, new Position(4, 2)));
        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());
        game.getGameMap().printBoard();
        //player 1 passa il turno
        MasterController.dispatcher(new EndTurnRequest(pl1));

        System.out.println("tocca al player2");
        //player 2 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl2, 0));
        //player 2 muove
        MasterController.dispatcher(new MoveRequest(pl2, new Position(3, 1)));
        assertEquals(game.getGameMap().getWorkerOnSquare(3,1), w1pl2);
        game.getGameMap().printBoard();
        //player 2 muove ancora grazie ad Artemis
        MasterController.dispatcher(new MoveRequest(pl2, new Position(4, 2)));
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), w1pl2);
        game.getGameMap().printBoard();
        //player 2 costruisce
        MasterController.dispatcher(new BuildRequest(pl2, new Position(3, 2) ));
        assertEquals(1, game.getGameMap().getSquare(3, 2).getHeight());
        game.getGameMap().printBoard();
        //player 2 passa il turno
        MasterController.dispatcher(new EndTurnRequest(pl2) );

        System.out.println("tocca al player1, secondo turno");
        //player 1 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl1, 0));
        //player 1 muove, TEST potere apollo dal basso verso l'alto
        MasterController.dispatcher(new MoveRequest(pl1, new Position(4, 2)));
        game.getGameMap().printBoard();
        //player 1 costruisce
        MasterController.dispatcher(new BuildRequest(pl1, new Position(3,2)));
        game.getGameMap().printBoard();
        //player 1 passa il turno
        MasterController.dispatcher(new EndTurnRequest(pl1) );

        System.out.println("tocca al player2");
        //player 2 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl2, 0));
        //player 2 muove
        MasterController.dispatcher((new MoveRequest(pl2, new Position(4,3))));
        game.getGameMap().printBoard();
        //plauer 2 ha artemis quindi deve fare la endMove
        MasterController.dispatcher(new EndMoveRequest(pl2));
        //player 2 costruisce
        MasterController.dispatcher(new BuildRequest(pl2, new Position(3, 3)));
        game.getGameMap().printBoard();
        //player 2 passa il turno
        MasterController.dispatcher(new EndTurnRequest(pl2));

        System.out.println("tocca al player1, terzo turno");
        //player 1 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl1, 0));
        MasterController.dispatcher(new MoveRequest(pl1, new Position(3,2)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new BuildRequest(pl1, new Position(3,3)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndTurnRequest(pl1));

        System.out.println("tocca al player2");
        //player 2 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl2, 0));
        MasterController.dispatcher(new MoveRequest(pl2, new Position(3,4)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndMoveRequest(pl2));
        MasterController.dispatcher(new BuildRequest(pl2, new Position(3,3)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndTurnRequest(pl2));

        System.out.println("tocca al player1, quarto turno");
        //player1 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl1, 0));
        MasterController.dispatcher(new MoveRequest(pl1, new Position(2,2)));
        game.getGameMap().printBoard();












    }




}



