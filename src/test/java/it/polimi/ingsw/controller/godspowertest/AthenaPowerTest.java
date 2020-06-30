package it.polimi.ingsw.controller.godspowertest;


import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.controller.MasterController;
import it.polimi.ingsw.server.model.action.ActionOutcome;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

public class AthenaPowerTest {

    private MasterController masterController;
    private Player player1, player2;
    private String pl1, pl2;
    private Game game;
    private SetupGameUtilityClass setupUtility;


    @Before
    public void setUp() throws DomePresentException {


        game = new Game();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        game.addPlayer(player1);
        game.addPlayer(player2);

        masterController = new MasterController(game, null);
        masterController.init(player1);

        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 2, 1, true);


        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();


        Square sq31 = game.getGameMap().getSquare(new Position(3, 1));
        sq31.addBlock(false);

    }

    @After
    public void tearDown() {
        game = null;
    }


    @Test
    public void AthenaPowerLastsOnlyOneRoundTest() {

        //Tocca al player1 (athena)
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 3, 1);


        assertEquals(game.getGameMap().getWorkerOnSquare(3, 1), setupUtility.w1pl1);


        setupUtility.build(pl1, 2, 1);
        setupUtility.endTurn(pl1);



        //Tocca al player2 (artemis)
        System.out.println("tocca al player 2");
        setupUtility.selectWorker(pl2, 0);


        //la move non viene effettuata a causa del potere di athena
        setupUtility.move(pl2, 2, 1);
        assertEquals(ActionOutcome.NOT_DONE, setupUtility.getOutcome());



        assertNotEquals(game.getGameMap().getWorkerOnSquare(2, 1), setupUtility.w1pl2);


        setupUtility.move(pl2, 2, 2);


        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 3, 2);
        setupUtility.endTurn(pl2);


        System.out.println("tocca al player 1, secondo round");
        setupUtility.selectWorker(pl1, 1);
        setupUtility.move(pl1, 2, 4);
        setupUtility.build(pl1, 1, 4);
        setupUtility.endTurn(pl1);


        System.out.println("tocca al player 2, secondo round");
        setupUtility.selectWorker(pl2, 1);

        //faccio salire il worker per verificare che il potere di athena non sia attivo
        setupUtility.move(pl2, 3, 2);


        assertEquals(game.getGameMap().getWorkerOnSquare(3, 2), setupUtility.w2pl2);


        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 4, 2);
        setupUtility.endTurn(pl2);
    }

}
