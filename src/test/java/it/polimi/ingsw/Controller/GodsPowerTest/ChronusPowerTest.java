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
import static org.junit.Assert.assertNull;

public class ChronusPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() throws DomePresentException {

        Game.resetInstance();
        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setupCompleteTowers(masterController, 9, 1);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        Game.resetInstance();
    }


    @Test
    public void ChronusPowerTest(){

        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1,0,3);
        setupUtility.build(pl1, 0,4);


        assertEquals(player1, setupUtility.getWinner());



    }

    @Test
    public void CanWinAlsoWhen5FullTowersAreBuiltDuringOpponentsTurn(){

        //player1 Chronus
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 0,1);
        setupUtility.build(pl1, 0,2);
        setupUtility.endTurn(pl1);

        //player2 Artemis
        setupUtility.selectWorker(pl2, 1);
        setupUtility.move(pl2, 3,4);
        setupUtility.endMove(pl2);

        assertNull(setupUtility.getWinner());

        setupUtility.build(pl2, 4,4);

        assertEquals(player1, setupUtility.getWinner());


        //assertEquals(ActionOutcome.WINNING_MOVE, setupUtility.getOutcome());



    }




}
