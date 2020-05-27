package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DemeterPowerTest {


    private static Game game;
    private static SetupGameUtilityClass setupUtility;
    private static String pl1, pl2;


    @BeforeClass
    public static void setUp() {

        game = Game.getInstance();

        Player player1 = new Player("Simone");
        Player player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        MasterController masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 4, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @AfterClass
    public static void tearDown(){
        Game.resetInstance();
    }

    @Test
    public void DemeterPowerTest(){

        game.getGameMap().printBoard();

        //player 1 (demeter)
        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1,3,1);
        game.getGameMap().printBoard();

        setupUtility.build(pl1,2,2);
        game.getGameMap().printBoard();

        //uso potere detemetrio
        assertFalse(game.getGameMap().getSquare(4, 2).hasBeenBuiltOver());
        setupUtility.build(pl1,4,2);
        assertTrue(game.getGameMap().getSquare(4, 2).hasBeenBuiltOver());
        game.getGameMap().printBoard();

        setupUtility.endTurn(pl1);
    }

    @Test
    public void DemeterCannotBuildInSamePlaceTest(){

        game.getGameMap().printBoard();

        //player 1 (demeter)
        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1,3,1);
        game.getGameMap().printBoard();

        setupUtility.build(pl1,2,2);
        game.getGameMap().printBoard();

        //uso potere detemetrio nello stesso spazio (dovrebbe fallire la costruzione)
        setupUtility.build(pl1,2,2);
        assertEquals(1, game.getGameMap().getSquare(2, 2).getHeight());
        game.getGameMap().printBoard();

        setupUtility.build(pl1,4,2);
        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());
        game.getGameMap().printBoard();

        setupUtility.endTurn(pl1);
    }

    @Test
    public void DemeterNotWantToBuildSecondTimeTest(){

        game.getGameMap().printBoard();

        System.out.println("tocca al player1, primo turno");
        //player1 (demeter)
        setupUtility.selectWorker(pl1,0);

        setupUtility.move(pl1,3,1);
        game.getGameMap().printBoard();

        setupUtility.build(pl1,2,2);
        game.getGameMap().printBoard();

        setupUtility.endBuild(pl1);

        setupUtility.endTurn(pl1);

        //player2 (artemis)
        System.out.println("tocca al player2, primo turno");
        setupUtility.selectWorker(pl2, 0);

        setupUtility.move(pl2, 2,2);
        game.getGameMap().printBoard();

        setupUtility.endMove(pl2);

        setupUtility.build(pl2, 3,2);
        game.getGameMap().printBoard();

        setupUtility.endTurn(pl2);

        System.out.println("tocca al player1, secondo turno");
        //player 1 seleziona worker
        setupUtility.selectWorker(pl1, 0);

        setupUtility.move(pl1, 2,1);
        game.getGameMap().printBoard();


        setupUtility.build(pl1,1,1);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 3,2);
        //assertEquals(2, game.getGameMap().getSquareHeight(new Position(3,2)));
        //game.getGameMap().printBoard();

        setupUtility.endTurn(pl1);






    }
}
