package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Exceptions.SantoriniException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HestiaPowerTest {

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
    public void HestiaPowerTest(){

        setupUtility.setup(masterController, 11, 1, true);

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1,2,4);
        setupUtility.build(pl1, 1,4);

        assertTrue(game.getGameMap().getSquare(1,4).hasBeenBuiltOver());

        setupUtility.build(pl1, 1,3);

        assertTrue(game.getGameMap().getSquare(1,3).hasBeenBuiltOver());

        setupUtility.endTurn(pl1);
    }

    @Test
    public void CannotUseHestiaPowerOnPerimetralSquareTest(){

        setupUtility.setup(masterController, 11, 1, true);

        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1,4);
        setupUtility.build(pl1, 0,4);

        setupUtility.build(pl1, 0,3);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());


        setupUtility.build(pl1, 1,3);


        assertTrue(game.getGameMap().getSquare(1,3).hasBeenBuiltOver());


        setupUtility.endTurn(pl1);

    }

    @Test
    public void DoesntWantToUseHestiaPowerTest(){

        setupUtility.setup(masterController, 11, 1, true);

        //player1 Hestia
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1,1,2);
        setupUtility.build(pl1, 0,3);

        setupUtility.endBuild(pl1);

        setupUtility.endTurn(pl1);


        //player2 Artemis
        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2,4,2);
        setupUtility.move(pl2, 4,1);
        setupUtility.build(pl2, 4,2);
        setupUtility.endTurn(pl2);


        //player1 Hestia
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,1);
        setupUtility.build(pl1, 0,1);

        assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

        setupUtility.build(pl1, 2,1);

        assertTrue(game.getGameMap().getSquare(2,1).hasBeenBuiltOver());

        setupUtility.endTurn(pl1);

    }

    @Test
    public void canBuildOneMoreTime() throws DomePresentException {

        setupUtility.setup(masterController, 11, 1, true);

        setupUtility.addBlock(1,4);
        setupUtility.addBlock(1,4);
        setupUtility.addBlock(1,4);
        setupUtility.addDome(3,4);

        //player1 Hestia
        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1,2,4);
        setupUtility.build(pl1, 1,4);
        assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());
        setupUtility.endMove(pl1);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void cannotBuildOneMoreTime() throws DomePresentException {

        setupUtility.setup(masterController, 11, 1, true);

        setupUtility.addBlock(1,4);
        setupUtility.addDome(3,4);

        //player1 Hestia
        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1,2,4);

        setupUtility.addDome(2,3);
        setupUtility.addDome(1,3);

        setupUtility.build(pl1, 1,4);
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        setupUtility.endTurn(pl1);


    }


}
