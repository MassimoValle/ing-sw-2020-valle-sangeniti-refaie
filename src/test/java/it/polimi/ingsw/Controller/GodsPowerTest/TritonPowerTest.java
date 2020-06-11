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

import static org.junit.Assert.*;

public class TritonPowerTest {

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

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();


    }

    @After
    public void tearDown() {
        game = Game.getInstance();
    }


   @Test
   public void TritonPowerTest(){

       setupUtility.setup(masterController, 13,1, true );


       setupUtility.selectWorker(pl1,1);
        setupUtility.move(pl1, 2,4);
        assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

       setupUtility.move(pl1, 1,4);
       assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

       setupUtility.move(pl1, 2,4);
       assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

       setupUtility.move(pl1, 1,4);
       assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

       setupUtility.move(pl1, 0,3);
       assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());

       setupUtility.move(pl1, 1,2);
       assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());

       setupUtility.build(pl1, 1,3);
       setupUtility.endTurn(pl1);
    }

    @Test
    public void DontWantToMoveAgainOnPerimeterTest(){

        setupUtility.setup(masterController, 13,1, true );


        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 2,4);
        setupUtility.move(pl1, 1,4);

        setupUtility.endMove(pl1);

        setupUtility.build(pl1, 0,4);

        assertTrue(game.getGameMap().getSquare(0,4).hasBeenBuiltOver());

        setupUtility.endTurn(pl1);



    }






    @Test
    public void Triton1PowerTest() {

        setupUtility.setup(masterController, 13,1, true );




        MasterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );

        MasterController.dispatcher(
                new MoveRequest(pl1, new Position(1,2))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new BuildRequest(pl1, new Position(0,2))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        //player 2 con tritone
        MasterController.dispatcher(
                new SelectWorkerRequest(pl2, 1)
        );

        MasterController.dispatcher(
                new MoveRequest(pl2, new Position(4,3))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new MoveRequest(pl2, new Position(4,4))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new MoveRequest(pl2, new Position(3,4))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new MoveRequest(pl2, new Position(2,4))
        );game.getGameMap().printBoard();

        MasterController.dispatcher(
                new MoveRequest(pl2, new Position(1,4))
        );game.getGameMap().printBoard();
        
        //this checks if I can stop from moving on the perimeter
        MasterController.dispatcher(
                new EndMoveRequest(pl2)
        );

        MasterController.dispatcher(
                new BuildRequest(pl2, new Position(0,4))
        );

        MasterController.dispatcher(
                new EndTurnRequest(pl2)
        );


        game.getGameMap().printBoard();

    }


    @Test
    public void athenaNotBlocking() throws DomePresentException {

        setupUtility.setup(masterController, 2,13, true );

        setupUtility.addBlock(2,3);
        setupUtility.addBlock(1,3);
        setupUtility.addBlock(1,3);


        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1,3);
        setupUtility.build(pl1, 0,3);

        assertTrue(game.getGameMap().getSquare(0,3).hasBeenBuiltOver());

        setupUtility.addBlock(2,4);
        setupUtility.addBlock(4,4);
        setupUtility.addBlock(4,3);

        setupUtility.endTurn(pl1);

        setupUtility.selectWorker(pl2, 1);
        setupUtility.move(pl2, 3,4);

        //HE CAN MOVE BACK TO ITS INITIAL SPACE
        assertEquals(ActionOutcome.DONE_CAN_BE_DONE_AGAIN, setupUtility.getOutcome());



    }
    @Test
    public void athenaBlocking() throws DomePresentException {

        setupUtility.setup(masterController, 2,13, true );

        setupUtility.addBlock(2,3);
        setupUtility.addBlock(1,3);
        setupUtility.addBlock(1,3);


        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1,3);
        setupUtility.build(pl1, 0,3);

        assertTrue(game.getGameMap().getSquare(0,3).hasBeenBuiltOver());

        setupUtility.addBlock(2,4);
        setupUtility.addBlock(4,4);
        setupUtility.addBlock(4,3);

        setupUtility.endTurn(pl1);

        setupUtility.addBlock(3,3);

        setupUtility.selectWorker(pl2, 1);
        setupUtility.move(pl2, 3,4);

        //HE CANNOT MOVE AGAIN DUE TO ATHENA MOVING UP AND HE CAN ONLY MOVE UP
        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());



    }

}