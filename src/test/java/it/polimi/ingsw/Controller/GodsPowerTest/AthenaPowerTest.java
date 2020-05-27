package it.polimi.ingsw.Controller.GodsPowerTest;


import it.polimi.ingsw.Server.Controller.MasterController;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.*;

public class AthenaPowerTest {

    MasterController masterController;
    Player player1, player2;
    String pl1, pl2;
    Game game;
    SetupGameUtilityClass setupUtility;


    @Before
    public void setUp() {

        game = Game.getInstance();

        player1 = new Player("Simone");
        player2 = new Player("Massimo");
        Game.getInstance().addPlayer(player1);
        Game.getInstance().addPlayer(player2);

        masterController = new MasterController(game, player1);

        setupUtility = new SetupGameUtilityClass();
        setupUtility.setup(masterController, 2, 1, true);


        pl2 = player2.getPlayerName();
        pl1 = player1.getPlayerName();
    }

    @After
    public void tearDown() { Game.resetInstance(); }


    @Test
    public void AthenaArtemisPowerTest() {

        game.getGameMap().printBoard();
        System.out.println("tocca al player 1, primo turno");
        //Tocca al player1 (athena)
        //seleziona un worker...
        setupUtility.selectWorker(pl1, 0);
        //lo muovo...
        setupUtility.move(pl1, 1, 2);
        assertEquals(game.getGameMap().getWorkerOnSquare(1, 2), setupUtility.w1pl1);
        game.getGameMap().printBoard();
        //e costruisco
        setupUtility.build(pl1, 0, 2);
        game.getGameMap().printBoard();
        assertTrue(game.getGameMap().getSquare(0, 2).hasBeenBuiltOver());
        //passo il turno
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player 2, primo turno");
        //Tocca al player2 (artemis)
        setupUtility.selectWorker(pl2, 0);
        setupUtility.move(pl2, 2, 1);
        assertEquals(game.getGameMap().getWorkerOnSquare(2, 1), setupUtility.w1pl2);
        game.getGameMap().printBoard();
        //player 2 sceglie di NON usare il potere
        setupUtility.endMove(pl2);
        //player 2 costruisce
        setupUtility.build(pl2, 2, 2);
        assertTrue(game.getGameMap().getSquare(2, 2).hasBeenBuiltOver());
        game.getGameMap().printBoard();
        //passo il turno
        setupUtility.endTurn(pl2);

        System.out.println("tocca al player 1, secondo turno");
        setupUtility.selectWorker(pl1, 0);
        //lo muovo... FACCIO SALIRE ATHENA AL LIVELLO 1
        setupUtility.move(pl1, 0, 2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 1, 2);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player 2, secondo turno");
        setupUtility.selectWorker(pl2, 0);
        //la move dovrebbe essere impedita dal potere di Athena
        setupUtility.move(pl2, 2, 2);
        assertFalse(game.getGameMap().getSquare(2, 2).hasWorkerOn());
        game.getGameMap().printBoard();
        //rifaccio una move concessa
        setupUtility.move(pl2, 3, 2);
        assertEquals(game.getGameMap().getWorkerOnSquare(3, 2), setupUtility.w1pl2);
        game.getGameMap().printBoard();
        //uso potere artemis e provo a salire (verifico che potere di athena è ancora attivo)
        setupUtility.move(pl2, 2, 2);
        assertFalse(game.getGameMap().getSquare(2, 2).hasWorkerOn());
        game.getGameMap().printBoard();
        //uso potere artemis per tornare nello spazio iniziale (DEVE RESTITUIRE ERRORE)
        setupUtility.move(pl2, 2, 1);
        assertFalse(game.getGameMap().getSquare(2, 1).hasWorkerOn());
        game.getGameMap().printBoard();
        //uso potere artemis per fare una mossa consentita
        setupUtility.move(pl2, 3, 1);
        assertTrue(game.getGameMap().getSquare(3, 1).hasWorkerOn());
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl2);

        System.out.println("tocca al player 1, terzo turno");
        setupUtility.selectWorker(pl1, 0);
        setupUtility.move(pl1, 1, 2);
        game.getGameMap().printBoard();
        setupUtility.build(pl1, 2, 1);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl1);

        System.out.println("tocca al player 2, terzo turno");
        setupUtility.selectWorker(pl2, 0);
        //il player2 dovrebbe poter salire dato che il potere di athena si è annullato
        setupUtility.move(pl2, 2, 1);
        assertTrue(game.getGameMap().getSquare(2, 1).hasWorkerOn());
        game.getGameMap().printBoard();
        setupUtility.endMove(pl2);
        setupUtility.build(pl2, 2, 2);
        game.getGameMap().printBoard();
        setupUtility.endTurn(pl2);
    }
}
