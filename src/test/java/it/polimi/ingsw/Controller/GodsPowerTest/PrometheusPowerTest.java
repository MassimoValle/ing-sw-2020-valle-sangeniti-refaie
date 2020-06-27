package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
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
import static org.junit.Assert.assertTrue;

public class PrometheusPowerTest {

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
    public void tearDown() {
        game = null;
    }

    @Test
    public void PrometheusPowerTest() throws DomePresentException {

        setupUtility.setup(masterController, 8, 1, true);


        setupUtility.selectWorker(pl1,0);
        setupUtility.powerButton(pl1);
        setupUtility.build(pl1, 1,1);


        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        assertTrue(game.getGameMap().getSquare(1,1).hasBeenBuiltOver());


        setupUtility.move(pl1, 1,1);


        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());


        setupUtility.move(pl1, 2,1);


        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        assertTrue(game.getGameMap().getSquare(2,1).hasWorkerOn());


        setupUtility.build(pl1, 1,2);


        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        assertTrue(game.getGameMap().getSquare(1,2).hasBeenBuiltOver());


        setupUtility.endTurn(pl1);

    }

    @Test
    public void CanClimbUpWhenDontUsePrometheusPowerTest(){

        setupUtility.setup(masterController, 8, 1, true);

        //player1 prometheus
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 1,1);
        setupUtility.build(pl1,1,2);
        setupUtility.endTurn(pl1);

        //player2 artemis
        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2,4,2);
        setupUtility.move(pl2, 4,3);
        setupUtility.build(pl2, 4,4);
        setupUtility.endTurn(pl2);

        //player1
        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 1,2);


        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());
        assertTrue(game.getGameMap().getSquare(1,2).hasWorkerOn());


        setupUtility.build(pl1,1,3);
        setupUtility.endTurn(pl1);

    }

    @Test
    public void PrometheusOneWay() throws DomePresentException {

        setupUtility.setup(masterController, 8,1, true);

        masterController.getGameInstance().getGameMap().getSquare(1,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(1,3).addBlock(false);


        //player1 prometheus
        setupUtility.selectWorker(pl1,0);
        setupUtility.powerButton(pl1);

        //CAN ONLY BUILD IN (1,3)
        //IF HE BUILDS IN (1,3) THEN HE CAN'T MOVE (he can't move up if chose to build first)
        setupUtility.build(pl1, 1,2);
        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());

        setupUtility.build(pl1, 1,3);
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());

        setupUtility.move(pl1, 1,2);
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());

        setupUtility.build(pl1, 1,3);
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());




    }
}