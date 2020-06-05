package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Network.Message.ClientRequests.BuildRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.EndTurnRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.MoveRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.SelectWorkerRequest;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Controller.MasterController;
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


import static org.junit.Assert.*;

public class ZeusPowerTest {


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
        setupUtility.setup(masterController, 12, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        Game.resetInstance();
    }

    @Test
    public void ZeusBuildTest() {

        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 2,1);
        setupUtility.build(pl1,2,1);

        assertEquals( new Position(2,1) ,player1.getPlayerWorkers().get(0).getWorkerPosition());
        assertEquals(1, game.getGameMap().getSquare(2,1).getHeight());
        assertTrue(game.getGameMap().getSquare(2,1).hasBeenBuiltOver());

        setupUtility.endTurn(pl1);
    }


    @Test
    public void ZeusCantBuildDomeUnderHimself() throws DomePresentException {

        setupUtility.setupDifferentHeight(masterController, 12, 1, true);

        Square sq24 = game.getGameMap().getSquare(2,4);
        sq24.addBlock(false);
        sq24.addBlock(false);
        sq24.addBlock(false);


        setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);


        assertEquals( new Position(2,4) ,player1.getPlayerWorkers().get(1).getWorkerPosition());
        assertEquals(3, game.getGameMap().getSquare(2,4).getHeight());
        assertFalse(game.getGameMap().getSquare(2,4).hasDome());



        setupUtility.build(pl1,2,4);


        assertEquals( new Position(2,4) ,player1.getPlayerWorkers().get(1).getWorkerPosition());
        assertEquals(3, game.getGameMap().getSquare(2,4).getHeight());
        assertFalse(game.getGameMap().getSquare(2,4).hasDome());


        setupUtility.endTurn(pl1);



    }
}
