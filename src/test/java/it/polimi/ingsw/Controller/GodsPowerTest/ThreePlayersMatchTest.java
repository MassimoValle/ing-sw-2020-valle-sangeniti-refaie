package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThreePlayersMatchTest {



    private MasterController masterController;
    private Player player1, player2, player3;
    private String pl1, pl2, pl3;
    private Game game;
    private SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() throws DomePresentException {


        game = new Game();

        player1 = new Player("UNO");
        player2 = new Player("DUE");
        player3 = new Player("TRE");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);

        masterController = new MasterController(game);
        masterController.init(player1);
        setupUtility = new SetupGameUtilityClass();

        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
        pl3 = player3.getPlayerName();
    }

    @After
    public void tearDown(){
        game = null;
    }

    @Test
    public void playerSetup() throws DomePresentException {
        setupUtility.setup3players(masterController, 1, 0, 2, true);

        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1,2);
        setupUtility.endMove(pl1);
        setupUtility.build(pl1, 2,2);

        masterController.getGameInstance().getGameMap().getSquare(2,2).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(2,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(3,1).addBlock(true);
        masterController.getGameInstance().getGameMap().getSquare(4,1).addBlock(true);

        setupUtility.endTurn(pl1);

        setupUtility.selectWorker(pl2, 1);
        setupUtility.move(pl2, 3,4);
        setupUtility.build(pl2, 3,3);

        masterController.getGameInstance().getGameMap().getSquare(3,3).addBlock(true);

        setupUtility.endTurn(pl2);

        setupUtility.selectWorker(pl3, 0);
        Assert.assertFalse(setupUtility.w1pl3.isSelected());

        setupUtility.selectWorker(pl3, 1);
        Assert.assertTrue(setupUtility.w2pl3.isSelected());




    }


}
