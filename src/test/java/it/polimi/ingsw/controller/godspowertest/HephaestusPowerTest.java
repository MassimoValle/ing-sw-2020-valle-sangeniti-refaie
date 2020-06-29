package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HephaestusPowerTest {


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
        setupUtility.setup(masterController, 5,1, true );

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();


    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void HephaestusPowerTest(){

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,1,2);
        setupUtility.build(pl1, 2,2);

        setupUtility.build(pl1,2,2);


        assertEquals(2, game.getGameMap().getSquare(2, 2).getHeight());


        setupUtility.endTurn(pl1);
    }


    @Test
    public void CannotBuildAgainInDifferentSquareTest(){

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,1,2);
        setupUtility.build(pl1, 2,2);

        setupUtility.build(pl1,2,1);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertFalse(game.getGameMap().getSquare(2, 1).hasBeenBuiltOver());


        setupUtility.build(pl1, 2,2);

        assertEquals(2, game.getGameMap().getSquare(2, 2).getHeight());

        setupUtility.endTurn(pl1);
    }

    @Test
    public void CannotUseHephaestusPowerToBuildDomeTest() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 5,1, false );

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 2,1);
        setupUtility.build(pl1, 1,2);

        assertFalse(setupUtility.getOutcome() == ActionOutcome.DONE_CAN_BE_DONE_AGAIN);

        setupUtility.endTurn(pl1);

    }

    @Test
    public void DontWantToUseHephaestusPowerTest(){

        //player1 Hephaestus
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,1,2);
        setupUtility.build(pl1, 2,2);

        setupUtility.endBuild(pl1);

        setupUtility.endTurn(pl1);

        //player2 Artemis
        setupUtility.selectWorker(pl2,1);
        setupUtility.move(pl2,4,3);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2,3,3);
        setupUtility.endTurn(pl2);


        //player1 Hephaestus
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,1,1);

        setupUtility.build(pl1, 2,2);


        assertTrue(setupUtility.getOutcome() == ActionOutcome.DONE_CAN_BE_DONE_AGAIN);


        setupUtility.build(pl1, 2,2);


        assertEquals(3, game.getGameMap().getSquare(2, 2).getHeight());


        setupUtility.endTurn(pl1);

    }

    @Test
    public void CannotUseHephaestusPowerWhenSquareIsLevel3() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 5,1, false );

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 1,1);
        setupUtility.build(pl1, 0,1);


        assertFalse(setupUtility.getOutcome() == ActionOutcome.DONE_CAN_BE_DONE_AGAIN);


        setupUtility.endTurn(pl1);

    }

}
