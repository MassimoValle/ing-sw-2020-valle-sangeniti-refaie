package it.polimi.ingsw.Controller.GodsPowerTest;


import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class ApolloPowerTest {


    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;
    SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();


        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);

        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 0, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @Test
    public void ApolloPower() {

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 3, 2);

        assertEquals(new Position(3, 2), player1.getPlayerWorkers().get(0).getWorkerPosition());
        assertEquals(new Position(2, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());

        setupUtility.build(pl1, 4, 2);

        setupUtility.endTurn(pl1);


        game.getGameMap().printBoard();

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 1,2);


        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

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



