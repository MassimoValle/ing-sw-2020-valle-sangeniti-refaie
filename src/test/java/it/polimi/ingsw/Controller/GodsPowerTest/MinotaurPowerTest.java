package it.polimi.ingsw.Controller.GodsPowerTest;

import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurPowerTest {

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


        setupUtility.build(pl1, 2, 2);
        setupUtility.endTurn(pl1);


        //player 2 (artemis) seleziona worker
        setupUtility.selectWorker(pl2, 1 );
        setupUtility.move(pl2, 2, 2);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2,1,3);
        setupUtility.endTurn(pl2);


        //System.out.println("tocca al player1, secondo turno");
        //player 1 seleziona worker
        setupUtility.selectWorker(pl1,1);

        //minotaur test spinta con minotauro livello più basso del worker da spingere
        setupUtility.move(pl1, 2,2);

        assertEquals(game.getGameMap().getWorkerOnSquare(2,2), setupUtility.w2pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2,1), setupUtility.w2pl2);

        setupUtility.build(pl1, 1,3);

    }

    @Test
    public void OutsideMapPushTest() {

        //player1 (Minotaur)
        setupUtility.selectWorker(pl1, 0);

        //test potere minotauro quando i 2 worker sono sullo stesso livello
        setupUtility.move(pl1, 3,2);

        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,2), setupUtility.w1pl2);


        setupUtility.build(pl1, 2, 2);
        setupUtility.endTurn(pl1);


        //player 2 (artemis) seleziona worker
        setupUtility.selectWorker(pl2, 0 );
        setupUtility.move(pl2, 4, 1);
        setupUtility.endMove(pl2);
        setupUtility.build(pl2,4,0);
        setupUtility.endTurn(pl2);


        //player 1 seleziona worker
        setupUtility.selectWorker(pl1,0);
        setupUtility.move(pl1, 4,1);

        assertEquals(game.getGameMap().getWorkerOnSquare(3,2), setupUtility.w1pl1);
        assertNotEquals(game.getGameMap().getWorkerOnSquare(4,1), setupUtility.w1pl1);
        assertEquals(game.getGameMap().getWorkerOnSquare(4,1), setupUtility.w1pl2);


    }

}
