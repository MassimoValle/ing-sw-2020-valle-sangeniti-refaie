package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ApolloPowerTest {


    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() throws DomePresentException {

        //Game.resetInstance();
        game = new Game();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        game.addPlayer(player1);
        game.addPlayer(player2);

        masterController = new MasterController(game);
        masterController.start(player1);
        setupUtility = new SetupGameUtilityClass();



        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        game = null;
    }


    @Test
    public void ApolloPowerSwapOnSameLevelTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, true );


        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 3,3);



        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl2);


        setupUtility.build(pl1, 4,2);


        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void SwapWhenOpponentWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 3,3);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl2);


        setupUtility.build(pl1, 4,4);


        assertEquals(1, game.getGameMap().getSquare(4, 4).getHeight());


        setupUtility.endTurn(pl1);

    }

    @Test
    public void DontSwapWhenOpponentWorkerIs2levelHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );


        setupUtility.selectWorker(pl1,0);


        //scambio posizione worker quando apollo è più in basso di 2 livelli (MOSSA NEGATA)
        setupUtility.move(pl1, 3,2);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl2);


        setupUtility.move(pl1,2,1);


        assertTrue(game.getGameMap().getSquare(2,1).hasWorkerOn());


        setupUtility.build(pl1, 3,1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void SwapWhenApolloWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );


        setupUtility.selectWorker(pl1,1);


        //scambio posizione worker quando apollo è più in alto di un livello
        setupUtility.move(pl1, 3,2);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,3), setupUtility.w1pl2);


        setupUtility.build(pl1, 4,3);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void SwapWhenApolloWorkerIs2LevelHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );


        setupUtility.selectWorker(pl1,1);

        //scambio posizione worker quando apollo è più in alto di un livello
        setupUtility.move(pl1, 3,3);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,3), setupUtility.w2pl2);


        setupUtility.build(pl1, 3,4);
        setupUtility.endTurn(pl1);

    }
}



