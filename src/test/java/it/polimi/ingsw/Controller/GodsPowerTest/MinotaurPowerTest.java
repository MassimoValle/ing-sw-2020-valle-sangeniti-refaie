package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Network.Message.ClientRequests.*;
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

import static org.junit.Assert.assertEquals;

public class MinotaurPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;
    protected Worker w1pl1, w2pl1, w1pl2, w2pl2;

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

        setupUtility.setup(masterController, 6,1, true );


    }

    @After
    public void tearDown() {
        game = Game.getInstance();
    }

    @Test
    public void MinotaurPower() {


        //player1 (Minotaur) inizia
        //seleziona worker
        setupUtility.selectWorker(pl1, 0);

        //test potere minotauro quando i 2 worker sono sullo stesso livello
        setupUtility.move(pl1, 3,2);
        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);


        game.getGameMap().printBoard();
        setupUtility.build(pl1, 2, 2);

        game.getGameMap().printBoard();

        setupUtility.endTurn(pl1);


        //player 2 (artemis) seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl2, 1 ));
        MasterController.dispatcher(new MoveRequest(pl2, new Position(2,2)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndMoveRequest(pl2));
        MasterController.dispatcher(new BuildRequest(pl2,new Position(1,3)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndTurnRequest(pl2));

        System.out.println("tocca al player1, secondo turno");
        //player 1 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl1,1));
        //minotaur test spinta con minotauro livello più basso del worker da spingere
        MasterController.dispatcher(new MoveRequest(pl1, new Position(2,2)));
        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,1), setupUtility.w2pl2);
        game.getGameMap().printBoard();
        MasterController.dispatcher(new BuildRequest(pl1, new Position(1,3)));
        game.getGameMap().printBoard();
        MasterController.dispatcher(new EndTurnRequest(pl1));

        System.out.println("tocca al player2, secondo turno");
        //player 2 seleziona worker
        MasterController.dispatcher(new SelectWorkerRequest(pl2, 0));
        MasterController.dispatcher(new MoveRequest(pl2, new Position(3,3)));
        game.getGameMap().printBoard();
        //muovi ancora potere artemis








    }

}
