package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PanPowerTest {

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
        masterController.start(player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setupDifferentHeight(masterController, 7, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        game = null;
    }

    @Test
    public void PanPowerTest(){

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 1,3);

        assertEquals(ActionOutcome.WINNING_MOVE, setupUtility.getOutcome());

    }

    @Test
    public void DoesntActivateIfWorkerMovesDownLessThan2levelsTest(){


        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1,1,2);


        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());


        setupUtility.build(pl1, 1,3);
        setupUtility.endTurn(pl1);
    }

}
