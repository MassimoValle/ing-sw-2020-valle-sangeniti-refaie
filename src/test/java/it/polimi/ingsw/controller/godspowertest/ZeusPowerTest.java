package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class ZeusPowerTest {


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
        setupUtility.setup(masterController, 12, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        game = null;
    }

    @Test
    public void ZeusBuildTest() {

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 2,1);
        setupUtility.build(pl1,2,1);

        assertEquals( new Position(2,1) ,player1.getPlayerWorkers().get(0).getPosition());
        assertEquals(1, game.getGameMap().getSquare(2,1).getHeight());
        assertTrue(game.getGameMap().getSquare(2,1).hasBeenBuiltOver());

        setupUtility.endTurn(pl1);
    }


    @Test
    public void ZeusCantBuildDomeUnderHimself() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 12, 1, true);

        Square sq24 = game.getGameMap().getSquare(2, 4);
        sq24.addBlock(false);
        sq24.addBlock(false);
        sq24.addBlock(false);


        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 2, 4);


        assertEquals(new Position(2, 4), player1.getPlayerWorkers().get(1).getPosition());
        assertEquals(3, game.getGameMap().getSquare(2, 4).getHeight());
        assertFalse(game.getGameMap().getSquare(2, 4).hasDome());

    }

    @Test
    public void ZeusCantWinBuildingUnderHimself() throws DomePresentException {

        setupUtility.setup(masterController, 12, 1, true);

        setupUtility.addBlock(2,2);
        setupUtility.addBlock(2,2);
        setupUtility.addBlock(2,2);
        setupUtility.addBlock(1,1);
        setupUtility.addBlock(1,1);
        setupUtility.addBlock(1,1);


        assertNull(setupUtility.getWinner());

        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,1);

        assertNull(setupUtility.getWinner());

    }

}
