package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class ApolloPowerTest {

    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;
    SetupGameUtilityClass setupUtility;

    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();


        player1 = new Player("sas");
        player2 = new Player("sos");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);

        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 0, 1, true);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @Test
    public void ApolloPowerTest() {

        game.getGameMap().printBoard();

        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 3, 2);

        assertEquals(new Position(3, 2), player1.getPlayerWorkers().get(0).getWorkerPosition());
        assertEquals(new Position(2, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());

        setupUtility.build(pl1, 4, 2);

        setupUtility.endTurn(pl1);


        game.getGameMap().printBoard();


        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 1,2);

        assertEquals(new Position(1, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());
        assertTrue(game.getGameMap().getSquare(1,2).hasWorkerOn());
        assertFalse(game.getGameMap().getSquare(1,2).hasBeenBuiltOver());


        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 1,1);
        setupUtility.endTurn(pl2);

        game.getGameMap().printBoard();

    }

}
