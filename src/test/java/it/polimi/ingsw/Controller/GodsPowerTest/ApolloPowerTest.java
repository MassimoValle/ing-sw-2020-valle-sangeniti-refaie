package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
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

        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();



        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        Game.resetInstance();
    }


    @Test
    public void ApolloPowerSwapOnSameLevelTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, true );

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,0);
        //scambio posizione worker quando sono sullo stesso livello
        setupUtility.move(pl1, 3,3);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 4,2);
        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);
    }

    @Test
    public void SwapWhenOpponentWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,0);

        //scambio posizione worker quando apollo è più in basso di un livello
        setupUtility.move(pl1, 3,3);

        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl2);

        game.getGameMap().printBoard();

        setupUtility.build(pl1, 4,4);

        assertEquals(1, game.getGameMap().getSquare(4, 4).getHeight());

        game.getGameMap().printBoard();

        setupUtility.endTurn(pl1);

    }

    @Test
    public void DontSwapWhenOpponentWorkerIs2levelHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,0);

        //scambio posizione worker quando apollo è più in basso di 2 livelli (MOSSA NEGATA)

        setupUtility.move(pl1, 3,2);

        //assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w1pl1);
        //assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w2pl2);
        game.getGameMap().printBoard();
        setupUtility.move(pl1,2,1);
        assertTrue(game.getGameMap().getSquare(2,1).hasWorkerOn());
        setupUtility.build(pl1, 3,1);
        assertEquals(1, game.getGameMap().getSquare(3, 1).getHeight());
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

    }

    @Test
    public void SwapWhenApolloWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,1);
        //scambio posizione worker quando apollo è più in alto di un livello
        setupUtility.move(pl1, 3,2);
        game.getGameMap().printBoard();
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,3), setupUtility.w1pl2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 4,3);
        assertEquals(1, game.getGameMap().getSquare(4, 3).getHeight());
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);
    }

    @Test
    public void SwapWhenApolloWorkerIs2LevelHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 0,1, false );

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,1);
        //scambio posizione worker quando apollo è più in alto di un livello
        setupUtility.move(pl1, 3,3);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,3), setupUtility.w2pl2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 3,4);
        assertEquals(1, game.getGameMap().getSquare(3, 4).getHeight());
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

    }
}



