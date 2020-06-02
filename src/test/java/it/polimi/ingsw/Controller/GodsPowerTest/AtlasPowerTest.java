package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Network.Message.ClientRequests.BuildDomeRequest;
import it.polimi.ingsw.Server.Controller.ActionManager;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AtlasPowerTest {


    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;


    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 3, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){
        Game.resetInstance();
    }


    @Test
    public void AtlasBuildDomeGroundLevelTest(){


        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 2,1);
        setupUtility.buildDome(pl1, 2,2);


        assertTrue(game.getGameMap().getSquare(2,2).hasDome());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void AtlasBuildDomeLevel1Test() throws DomePresentException {

        //setup
        Square sq21 = game.getGameMap().getSquare(new Position(2,1));
        sq21.addBlock(false);


        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,1);


        setupUtility.buildDome(pl1, 2,1);


        assertTrue(game.getGameMap().getSquare(2,1).hasDome());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void AtlasBuildDomeLevel2Test() throws DomePresentException {

        //setup
        Square sq13 = game.getGameMap().getSquare(new Position(1,3));
        sq13.addBlock(false);
        sq13.addBlock(false);


        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,2);


        setupUtility.buildDome(pl1, 1,3);


        assertTrue(game.getGameMap().getSquare(1,3).hasDome());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void AtlasBuildDomeLevel3Test() throws DomePresentException {

        //setup
        Square sq11 = game.getGameMap().getSquare(new Position(1,1));
        sq11.addBlock(false);
        sq11.addBlock(false);
        sq11.addBlock(false);


        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,2);

        setupUtility.buildDome(pl1, 1,1);


        assertTrue(game.getGameMap().getSquare(1,1).hasDome());


        setupUtility.endTurn(pl1);
    }

    @Test
    public void AtlasCannotBuildDomeAboveDome() throws DomePresentException {

        //setup
        Square sq11 = game.getGameMap().getSquare(new Position(1,1));
        sq11.addBlock(true);


        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,2);


        setupUtility.buildDome(pl1, 1,1);


        setupUtility.buildDome(pl1,0,1);

        assertTrue(game.getGameMap().getSquare(0,1).hasDome());

        setupUtility.endTurn(pl1);
    }
}
