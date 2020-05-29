package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArtemisPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() {

        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 1, 2, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        Game.resetInstance();
    }

    @Test
    public void ArtemisPowerTest(){

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 3,1);
        assertTrue(game.getGameMap().getSquare(3,1).hasWorkerOn());
        game.getGameMap().printBoard();
        //uso il potere di artemis
        setupUtility.move(pl1, 4,1);
        game.getGameMap().printBoard();
        assertTrue(game.getGameMap().getSquare(4,1).hasWorkerOn());
        setupUtility.build(pl1,3,1);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);
    }

    @Test
    public void ArtemisCantMoveBackToInitialPlaceTest(){

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);
        assertTrue(game.getGameMap().getSquare(2,4).hasWorkerOn());
        game.getGameMap().printBoard();
        //questa move deve fallire
        setupUtility.move(pl1, 2,3);
        game.getGameMap().printBoard();
        assertFalse(game.getGameMap().getSquare(2, 3).hasWorkerOn());
        //rifaccio la move dove mi posso muovere
        setupUtility.move(pl1,1,3);
        assertTrue(game.getGameMap().getSquare(1,3).hasWorkerOn());
        game.getGameMap().printBoard();
        setupUtility.build(pl1,1,4);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);
    }

    @Test
    public void ArtemisDoesntWantToUsePowerTest(){

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);
        assertTrue(game.getGameMap().getSquare(2,4).hasWorkerOn());
        game.getGameMap().printBoard();
        //non voglio usare il potere
        setupUtility.endMove(pl1);
        setupUtility.build(pl1,1,4);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

    }
    
}
