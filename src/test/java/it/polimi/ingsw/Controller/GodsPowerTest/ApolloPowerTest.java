package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Network.Message.ClientRequests.MoveRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.SelectWorkerRequest;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ApolloPowerTest {

    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;

    @Before
    public void setUp() {

        Game.resetInstance();
        game = Game.getInstance();


        player1 = new Player("sas");
        player2 = new Player("sos");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);

        new SetupGameUtilityClass().setup(masterController, 0, 1, true );
        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @Test
    public void ApolloPowerTest() {

        game.getGameMap().printBoard();

        MasterController.dispatcher(
                new SelectWorkerRequest(pl1, 0)
        );

        MasterController.dispatcher(
                new MoveRequest(pl1, new Position(3,2))
        );

        assertEquals(new Position(3, 2), player1.getPlayerWorkers().get(0).getWorkerPosition());
        assertEquals(new Position(2, 2), player2.getPlayerWorkers().get(0).getWorkerPosition());

        game.getGameMap().printBoard();

    }

}
