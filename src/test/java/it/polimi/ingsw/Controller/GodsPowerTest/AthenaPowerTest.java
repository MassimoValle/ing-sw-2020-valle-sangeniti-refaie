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
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AthenaPowerTest {

    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;

    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();



        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);

        new SetupGameUtilityClass().setup(masterController, 2,0, true );

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();
    }

    @Test
    public void AthenaPowerTest() {

        //Tocca al player1
        //seleziona un worker...

        assertFalse(player1.getPlayerWorkers().get(0).isSelected());


        masterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );

        assertTrue(player1.getPlayerWorkers().get(0).isSelected());



        //giocatore 2 piazza il primo worker
        Worker w1pl2 = player2.getPlayerWorkers().get(0);
        Square sq32 = game.getGameMap().getSquare(new Position(3, 2));

        w1pl2.setPosition(new Position(3, 2));
        sq32.setWorkerOn(w1pl2);
        w1pl2.setPlaced(true);

        //giocatore 1 piazza il secondo worker
        Worker w2pl1 = player1.getPlayerWorkers().get(1);
        Square sq23 = game.getGameMap().getSquare(new Position(2, 3));

        w2pl1.setPosition(new Position(2, 3));
        sq23.setWorkerOn(w2pl1);
        w2pl1.setPlaced(true);

        //giocatore 2 piazza il seoondo worker
        Worker w2pl2 = player2.getPlayerWorkers().get(1);
        Square sq33 = game.getGameMap().getSquare(new Position(3, 3));

        w2pl2.setPosition(new Position(3, 3));
        sq33.setWorkerOn(w2pl2);
        w2pl2.setPlaced(true);

        game.getGameMap().printBoard();

        masterController._getActionManager().setGameState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().updateTurnState(PossibleGameState.START_ROUND);
        masterController._getTurnManager().nextTurn(player1);


        //Tocca al player1
        //seleziona un worker...
        MasterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );

        assertEquals(player1.getPlayerWorkers().get(0), game.getGameMap().getWorkerOnSquare(1,2));
        assertTrue(game.getGameMap().getSquare(1,2).hasWorkerOn());
        assertFalse(game.getGameMap().getSquare(1,2).hasBeenBuiltOver());


        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );

        assertFalse(game.getGameMap().getSquare(1,2).hasBeenBuiltOver());

        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );


        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        // 2 TUNRO PLAYER ATHENA
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(0, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 3))
        );

        assertFalse(game.getGameMap().getSquare(new Position(4,3)).hasWorkerOn());
        assertEquals(new Position(4, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());


        masterController.dispatcher(
                new MoveRequest(pl2, new Position(3, 2))
        );

        assertTrue(game.getGameMap().getSquare(new Position(3,2)).hasWorkerOn());
        assertEquals(new Position(3, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());


        assertFalse(game.getGameMap().getSquare(3,1).hasBeenBuiltOver());

        masterController.dispatcher(
                new BuildRequest(pl2, new Position(3, 1))
        );

        assertTrue(game.getGameMap().getSquare(3,1).hasBeenBuiltOver());

        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

    }
}
