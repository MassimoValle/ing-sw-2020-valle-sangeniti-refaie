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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;



public class ApolloPowerTest {


    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;
    SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() {

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

    @After
    public void tearDown(){
        Game.resetInstance();
    }


    @Test
    public void ApolloPowerTest() {

        game.getGameMap().printBoard();

        System.out.println("tocca al player1, primo turno");
        //tocca al player1
        //seleziona un worker...
        setupUtility.selectWorker(pl1,0);
        //test potere di apollo quando i 2 worker sono sullo stesso livello
        setupUtility.move(pl1, 3,3);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl2);
        game.getGameMap().printBoard();
        //player1 costruisce
        setupUtility.build(pl1, 4,2);
        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());
        game.getGameMap().printBoard();
        //player 1 passa il turno
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player2, primo turno");
        //player 2 seleziona worker
        setupUtility.selectWorker(pl2,0);
        //player 2 muove
        setupUtility.move(pl2, 3,1);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,1), setupUtility.w1pl2);
        game.getGameMap().printBoard();
        //player 2 muove ancora grazie ad Artemis
        setupUtility.move(pl2, 4,2);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);
        game.getGameMap().printBoard();
        //player 2 costruisce
        setupUtility.build(pl2,3,2);
        assertEquals(1, game.getGameMap().getSquare(3, 2).getHeight());
        game.getGameMap().printBoard();
        //player 2 passa il turno
        setupUtility.endTurn(pl2);

        System.out.println("tocca al player1, secondo turno");
        //player 1 seleziona worker
        setupUtility.selectWorker(pl1, 0);
        //player 1 muove, TEST potere apollo dal basso verso l'alto
        setupUtility.move(pl1, 4,2);
        game.getGameMap().printBoard();
        //player 1 costruisce
        setupUtility.build(pl1, 3,2);
        game.getGameMap().printBoard();
        //player 1 passa il turno
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player2");
        //player 2 seleziona worker
        setupUtility.selectWorker(pl2, 0);
        //player 2 muove
        setupUtility.move(pl2, 4,3);
        game.getGameMap().printBoard();
        //plauer 2 ha artemis quindi deve fare la endMove
        setupUtility.endMove(pl2);
        //player 2 costruisce
        setupUtility.build(pl2,3,3);
        game.getGameMap().printBoard();
        //player 2 passa il turno
        setupUtility.endTurn(pl2);

        System.out.println("tocca al player1, terzo turno");
        //player 1 seleziona worker
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 3,2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 3,3);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player2");
        //player 2 seleziona worker
        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2,3 ,4);
        game.getGameMap().printBoard();
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 3,3);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl2);














    }




}



