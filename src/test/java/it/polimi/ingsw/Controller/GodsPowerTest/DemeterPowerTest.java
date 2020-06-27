package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import org.junit.*;

import static org.junit.Assert.*;

public class DemeterPowerTest {


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
        setupUtility.setup(masterController, 4, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }


    @After
    public void tearDown(){
        game = null;
    }

    @Test
    public void DemeterPowerTest(){


        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,2,2);


        assertFalse(game.getGameMap().getSquare(4, 2).hasBeenBuiltOver());


        //uso potere detemetrio
        setupUtility.build(pl1,4,2);


        assertTrue(game.getGameMap().getSquare(4, 2).hasBeenBuiltOver());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void DemeterCannotBuildInSamePlaceTest(){


        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,3,1);


        setupUtility.build(pl1,2,2);


        //uso potere detemetrio nello stesso spazio (dovrebbe fallire la costruzione)
        setupUtility.build(pl1,2,2);


        assertEquals(1, game.getGameMap().getSquare(2, 2).getHeight());


        setupUtility.build(pl1,4,2);


        assertEquals(1, game.getGameMap().getSquare(4, 2).getHeight());



        setupUtility.endTurn(pl1);
    }

    @Test
    public void DemeterDoesntWantToBuildSecondTimeTest(){


        //player1 (demeter)
        System.out.println("tocca al player1, primo turno");
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1,3,1);
        setupUtility.build(pl1,2,2);

        setupUtility.endBuild(pl1);

        setupUtility.endTurn(pl1);


        //player2 (artemis)
        System.out.println("tocca al player2, primo turno");
        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 2,2);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 3,2);
        setupUtility.endTurn(pl2);


        //player 1 seleziona worker
        System.out.println("tocca al player1, secondo turno");
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 2,1);


        setupUtility.build(pl1,1,1);


        assertEquals(1, game.getGameMap().getSquareHeight(new Position(1,1)));


        setupUtility.endBuild(pl1);
        setupUtility.endTurn(pl1);
    }
}
