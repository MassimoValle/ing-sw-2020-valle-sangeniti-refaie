package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArtemisPowerTest {

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

        masterController = new MasterController(game, null);
        masterController.init(player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 1, 2, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        game = null;
    }

    @Test
    public void ArtemisPowerTest(){

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 3,1);


        assertTrue(game.getGameMap().getSquare(3,1).hasWorkerOn());
        assertFalse(game.getGameMap().getSquare(4,1).hasWorkerOn());


        //uso il potere di artemis
        setupUtility.move(pl1, 4,1);


        assertTrue(game.getGameMap().getSquare(4,1).hasWorkerOn());


        setupUtility.build(pl1,3,1);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void ArtemisCantMoveBackToInitialPlaceTest(){

        //da qualche parte non si resetta idk
        player1.getPlayerGod().getGodPower().resetPower();

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);

        assertTrue(game.getGameMap().getSquare(2,4).hasWorkerOn());


        setupUtility.move(pl1, 2,3);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());
        assertFalse(game.getGameMap().getSquare(2, 3).hasWorkerOn());


        //rifaccio la move dove mi posso muovere
        setupUtility.move(pl1,1,3);

        assertTrue(game.getGameMap().getSquare(1,3).hasWorkerOn());

        setupUtility.build(pl1,1,4);
        setupUtility.endTurn(pl1);
    }

    @Test
    public void ArtemisDoesntWantToUsePowerTest(){

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);

        
        assertTrue(game.getGameMap().getSquare(2,4).hasWorkerOn());


        setupUtility.endMove(pl1);
        setupUtility.build(pl1,1,4);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void ArtemisCannotBuildDomeLikeAtlasDoesTest(){

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);


        assertTrue(game.getGameMap().getSquare(2,4).hasWorkerOn());


        setupUtility.endMove(pl1);

        setupUtility.buildDome(pl1,1,4);


        assertFalse(game.getGameMap().getSquare(1,4).hasDome());
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        assertTrue(game.getGameMap().getSquare(1,4).hasBeenBuiltOver());


        setupUtility.endTurn(pl1);

    }
}
