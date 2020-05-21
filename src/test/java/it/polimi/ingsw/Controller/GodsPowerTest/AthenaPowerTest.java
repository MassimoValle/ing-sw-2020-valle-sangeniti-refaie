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

import static org.junit.Assert.assertEquals;

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

        new SetupGameUtilityClass().setup(masterController, 2,1, true );

        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();
    }

    @Test
    public void AthenaPowerTest() {

        game.getGameMap().printBoard();
        //Tocca al player1
        //seleziona un worker...
        masterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );
        //lo muovo...
        masterController.dispatcher(
                new MoveRequest(pl1, new Position(1, 2))
        );
        //e costruisco
        masterController.dispatcher(
                new BuildRequest(pl1, new Position(0, 2))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl1)
        );

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 2))
        );

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );

        masterController.dispatcher(
                new BuildRequest(pl2, new Position(4, 3))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        game.getGameMap().printBoard();

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

        game.getGameMap().printBoard();

        //Tocca al player2
        masterController.dispatcher(
                new SelectWorkerRequest(pl2, 0)
        );
        //lo muovo la prima volta
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(4, 3))
        );


        game.getGameMap().printBoard();
        //La move request da error perchè athena nel turno precedente è salita
        //di conseguenza sono costretto a muovere in un altra posizine
        masterController.dispatcher(
                new MoveRequest(pl2, new Position(3, 2))
        );

        game.getGameMap().printBoard();

        masterController.dispatcher(
                new EndMoveRequest(pl2)
        );
        masterController.dispatcher(
                new BuildRequest(pl2, new Position(3, 1))
        );
        //passo il turno
        masterController.dispatcher(
                new EndTurnRequest(pl2)
        );

        game.getGameMap().printBoard();
        //FUNZIONAAAAA
    }
}
