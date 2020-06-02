package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChronusPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;



    @Before
    public void setUp() throws DomePresentException {

        Game.resetInstance();
        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);
        setupUtility = new SetupGameUtilityClass();
        setupUtility.setupCompleteTowers(masterController, 9, 1);


        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){

        Game.resetInstance();
    }


    @Test
    public void ChronusPowerTest(){

        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1,0,3);
        setupUtility.build(pl1, 0,4);
        setupUtility.endTurn(pl1);


    }




}

