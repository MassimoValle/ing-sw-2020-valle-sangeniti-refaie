package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.controller.Enum.PossibleGameState;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.server.model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkersStuckTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;


    @Before
    public void setUp() throws DomePresentException {


        game = new Game();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        game.addPlayer(player1);
        game.addPlayer(player2);

        masterController = new MasterController(game);
        masterController.init(player1);
        setupUtility = new SetupGameUtilityClass();

        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void workerSurroundedApollo() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 0, 1, false);

        masterController.getGameInstance().getGameMap().printBoard();
        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1, 4);
        setupUtility.build(pl1, 2, 4);
        setupUtility.endTurn(pl1);

        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 3, 0);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 2, 0);

        //chiudo l'altro worker, adesso sono entrambi bloccati
        setupUtility.addDome(0, 4);
        setupUtility.addDome(0, 3);
        setupUtility.addDome(1, 3);
        setupUtility.addDome(2, 3);
        setupUtility.addDome(2, 4);

        setupUtility.endTurn(pl2);

        //il giocatore perde

    }

    @Test
    public void workersSurrounded() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 2, 3, false);

        masterController.getGameInstance().getGameMap().printBoard();
        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1, 4);
        setupUtility.build(pl1, 2, 4);
        setupUtility.endTurn(pl1);

        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 3, 0);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 2, 0);

        //chiudo l'altro worker, adesso sono entrambi bloccati
        setupUtility.addDome(0, 4);
        setupUtility.addDome(0, 3);
        setupUtility.addDome(1, 3);
        setupUtility.addDome(2, 3);
        setupUtility.addDome(2, 4);

        setupUtility.endTurn(pl2);

        //il giocatore perde
        assertEquals(PossibleGameState.GAME_OVER, setupUtility.getGameState());
        assertEquals(1, masterController._getTurnManager().getInGamePlayers().size());
        assertTrue(player1.isEliminated());
        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());

    }

    @Test
    public void forcedToSwapUpApollo() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 0, 2, true);

        masterController.getGameInstance().getGameMap().printBoard();

        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,2).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,3).addBlock(false);

        masterController.getGameInstance().getGameMap().printBoard();

        assertTrue( ((ApolloPower) player1.getPlayerGod().getGodPower()).canSwapOnlyGoingUp(masterController.getGameInstance().getGameMap(), setupUtility.w1pl1));
        setupUtility.selectWorker(pl1, 0);
        assertTrue(setupUtility.w1pl1.isSelected());

    }

    @Test
    public void forcedToMoveUpApolloButTooHigh() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 0, 2, true);

        masterController.getGameInstance().getGameMap().printBoard();

        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,2).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,2).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,3).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,3).addBlock(false);

        masterController.getGameInstance().getGameMap().printBoard();

        setupUtility.selectWorker(pl1, 0);
        assertFalse(setupUtility.w1pl1.isSelected());

    }

    @Test
    public void forcedToSwapUpApolloButAthenaPrevents() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 2, 0, true);

        masterController.getGameInstance().getGameMap().getSquare(1,2).addBlock(false);

        masterController.getGameInstance().getGameMap().printBoard();

        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,2);
        setupUtility.build(pl1, 2,2);

        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(4,1).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(4,2).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(4,3).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(4,4).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(3,4).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(2,4).addBlock(false);
        masterController.getGameInstance().getGameMap().getSquare(2,3).addBlock(false);

        masterController.getGameInstance().getGameMap().printBoard();

        setupUtility.endTurn(pl1);


        setupUtility.selectWorker(pl2, 0);
        assertFalse(setupUtility.w1pl2.isSelected());

        //il giocatore 2 perde
        assertEquals(PossibleGameState.GAME_OVER, setupUtility.getGameState());
        assertEquals(1, masterController._getTurnManager().getInGamePlayers().size());
        assertTrue(player2.isEliminated());
        assertEquals(2, masterController.getGameInstance().getNumberOfPlayers());


    }


    @Test
    public void minotaurNotStuck() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 6, 0, true);

        masterController.getGameInstance().getGameMap().printBoard();

        setupUtility.selectWorker(pl1, 0);
        assertTrue(setupUtility.w1pl1.isSelected());
        setupUtility.move(pl1, 1, 2);
        setupUtility.build(pl1, 2, 2);
    }

    @Test
    public void minotaurCanPush() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 6, 0, true);


        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);

        masterController.getGameInstance().getGameMap().printBoard();


        setupUtility.selectWorker(pl1, 0);
        assertTrue(setupUtility.w1pl1.isSelected());

    }

    @Test
    public void minotaurUniqueWay() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 6, 0, true);


        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,4).addBlock(true);

        masterController.getGameInstance().getGameMap().printBoard();


        setupUtility.selectWorker(pl1, 0);
        assertFalse(setupUtility.w1pl1.isSelected());

        setupUtility.selectWorker(pl1, 1);
        assertTrue(setupUtility.w2pl1.isSelected());

    }


    @Test
    public void minotaurCannotPush() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 6, 0, true);


        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,4).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,3).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,4).addBlock(true);

        masterController.getGameInstance().getGameMap().printBoard();


        setupUtility.selectWorker(pl1, 1);
        assertTrue(setupUtility.w2pl1.isSelected());

    }

}
