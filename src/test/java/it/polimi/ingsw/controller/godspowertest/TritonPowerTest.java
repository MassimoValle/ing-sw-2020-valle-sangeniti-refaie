package it.polimi.ingsw.controller.godspowertest;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.network.message.clientrequests.*;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TritonPowerTest {

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

        masterController = new MasterController(game, null);
        masterController.init(player1);

        setupUtility = new SetupGameUtilityClass();

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();


    }

    @After
    public void tearDown() {
        game = null;
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




        masterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );

        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1,2))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0,2))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        //player 2 con tritone
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 1)
        );

        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4,3))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4,4))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new MoveRequest(pl2, new Position(3,4))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new MoveRequest(pl2, new Position(2,4))
        );game.getGameMap().printBoard();

        masterController.dispatcher(
                new MoveRequest(pl2, new Position(1,4))
        );game.getGameMap().printBoard();
        
        //this checks if I can stop from moving on the perimeter
        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );

        assertEquals(ActionOutcome.DONE, setupUtility.getOutcome());

        masterController.dispatcher(
                new BuildRequest(pl2, new Position(0,4))
        );

        masterController.dispatcher(
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