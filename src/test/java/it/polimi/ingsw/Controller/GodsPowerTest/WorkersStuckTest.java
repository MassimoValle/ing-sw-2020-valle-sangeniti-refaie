package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkersStuckTest {

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

        pl1 = player1.getPlayerName();
        pl2 = player2.getPlayerName();
    }

    @After
    public void tearDown(){
        Game.resetInstance();
    }

    @Test
    public void workerSurrounded() throws DomePresentException {

        setupUtility.setupSurrounded(masterController, 0, 1, false);

        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 1, 4);
        setupUtility.build(pl1, 2, 4);
        setupUtility.endTurn(pl1);

        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 3,0);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 2,0);

        //chiudo l'altro worker, adesso sono entrambi bloccati
        setupUtility.addDome(0,4);
        setupUtility.addDome(0,3);
        setupUtility.addDome(1,3);
        setupUtility.addDome(2,3);
        setupUtility.addDome(2,4);

        setupUtility.endTurn(pl2);

        //il giocatore perde

    }
}
