package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;

    @Before
    public void setUp() {

        game = new Game();


        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        game.addPlayer(player1);
        game.addPlayer(player2);

        masterController = new MasterController(game);
        masterController.init(player1);

        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 6,1, true );

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();


    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void MinotaurPower() {


        //player1 (Minotaur)
        setupUtility.selectWorker(pl1, 0);


        //test potere minotauro quando i 2 worker sono sullo stesso livello
        setupUtility.move(pl1, 3,2);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);


        setupUtility.build(pl1, 2, 2);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void PushWhenOpponentWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 6,1, false );


        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 3,3);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,4), setupUtility.w2pl2);


        setupUtility.build(pl1,3,4);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void PushWhenMinotaurWorkerIsHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 6,1, false );

        setupUtility.selectWorker(pl1, 1);

        setupUtility.move(pl1, 3,2);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,1), setupUtility.w1pl2);


        setupUtility.build(pl1,3,1);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void PushWhenBackwardSquareIs2LevelHigherThanOpponentWorkerTest() throws DomePresentException {

        //setup
        Square sq42 = game.getGameMap().getSquare(4,2);
        sq42.addBlock(false);
        sq42.addBlock(false);

        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 3,2);


        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);


        setupUtility.build(pl1,2,1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void PushWhenBackwardSquareIsLowerThanOpponentWorkerTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 6,1, false );

        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1, 3,3);

        assertEquals(game.getGameMap().getWorkerOnSquare(3,3), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,4), setupUtility.w2pl2);

        setupUtility.build(pl1,3,4);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void CantPushWhenOpponentWorkerIs2LevelHigherTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 6,1, false );

        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1, 3,2);

        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl2);

        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,4,1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void CantPushWhenBackwardSquareHasDome() throws DomePresentException {

        //setup
        Square sq42 = game.getGameMap().getSquare(4,2);
        sq42.addBlock(true);

        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 3,2);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl2);


        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,2,1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void  CantPushWhenBackwardSquareHasWorkerOn(){

        //player1 Minotaur
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,2,1);
        setupUtility.build(pl1,2,2);
        setupUtility.endTurn(pl1);

        //player2 Artemis
        setupUtility.selectWorker(pl2,1);
        setupUtility.move(pl2,4,3);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2,3,3);
        setupUtility.endTurn(pl2);

        //player1 Minotaur
        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1, 3,2);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,1), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl2);


        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,2,1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void CantPushHisOwnWorker(){

        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 2,3);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,3), setupUtility.w2pl1);

        setupUtility.move(pl1,1,3);
        setupUtility.build(pl1, 2, 2);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void OutsideMapPushTest() {

        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 3,2);

        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);


        setupUtility.build(pl1, 2, 2);
        setupUtility.endTurn(pl1);


        //player 2 (artemis)
        setupUtility.selectWorker(pl2, 0 );
        setupUtility.move(pl2, 4, 1);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2,4,0);
        setupUtility.endTurn(pl2);


        //player 1 Minotaur
        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1, 4,1);

        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,1), setupUtility.w1pl2);

        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,2,1);
        setupUtility.endTurn(pl1);

    }

}
