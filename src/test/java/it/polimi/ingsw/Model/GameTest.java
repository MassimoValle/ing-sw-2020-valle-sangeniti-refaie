package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game gameTest;

    //Sono un deficiente ma non mi è venuto altro in mente
    Position pos00 = new Position(0,0);
    Position pos01 = new Position(0,1);
    Position pos02 = new Position(0,2);
    Position pos03 = new Position(0,3);
    Position pos04 = new Position(0,4);
    Position pos10 = new Position(1,0);
    Position pos11 = new Position(1,1);
    Position pos12 = new Position(1,2);
    Position pos13 = new Position(1,3);
    Position pos14 = new Position(1,4);
    Position pos20 = new Position(2,0);
    Position pos21 = new Position(2,1);
    Position pos22 = new Position(2,2);
    Position pos23 = new Position(2,3);
    Position pos24 = new Position(2,4);
    Position pos30 = new Position(3,0);
    Position pos31 = new Position(3,1);
    Position pos32 = new Position(3,2);
    Position pos33 = new Position(3,3);
    Position pos34 = new Position(3,4);
    Position pos40 = new Position(4,0);
    Position pos41 = new Position(4,1);
    Position pos42 = new Position(4,2);
    Position pos43 = new Position(4,3);
    Position pos44 = new Position(4,4);


    @Before
    public void setUp() throws Exception {
        gameTest = new Game();
    }

    @Test
    public void gameNotNull(){
        assertNotNull(gameTest);
    }

    @Test
    public void ensureEverythingIsInitialized() {
        assertNotNull("Game deck", gameTest.getDeck());
        assertNotNull("Game Map", gameTest.getGameMap());
        assertEquals(0, gameTest.getPlayers().size());
        assertNull("No one is active", gameTest.getPlayerActive());
        assertEquals(0, gameTest.getChosenGodsFromDeck().size());
        assertEquals(0, gameTest.getNumberOfPlayers());
        assertFalse(gameTest.areGodsChosen());
    }

    @Test
    public void checkIfWorkerIsStuck() {
        //Aggiungo un nuovo giocatore
        Player player1 = gameTest.addPlayer("Simone");
        //posiziono il primo dei suoi worker
        gameTest.placeWorker(player1, player1.getPlayerWorkers().get(0), pos00);

        //Blocking the worker
        gameTest.getGameMap().setSquareHeight(pos01, 3);
        gameTest.getGameMap().setSquareHeight(pos11, 3);
        gameTest.getGameMap().setSquareHeight(pos10, 3);

        //Check if it's stuck
        assertTrue(gameTest.isWorkerStuck(player1.getPlayerWorkers().get(0)));


    }

    @Test
    public void startGame() throws PlayerNotFoundException {
        Player simone = gameTest.addPlayer("Simone");
        Player max  = gameTest.addPlayer("Max");
        Player magdy = gameTest.addPlayer("Magdy");

        assertEquals(3, gameTest.getNumberOfPlayers());

        //Il giocatore più GODLIKE prende le carte dal mazzo
        for (int i = 1; i < gameTest.getNumberOfPlayers() + 1; i++) {
            gameTest.pickGodFromDeck(i);
        }

        assertEquals(3, gameTest.getChosenGodsFromDeck().size());

        //Assegno i god scelti ai player
        gameTest.assignGodToPlayer(simone, gameTest.getChosenGodsFromDeck().get(2));
        gameTest.assignGodToPlayer(max, gameTest.getChosenGodsFromDeck().get(2));
        gameTest.assignGodToPlayer(magdy, gameTest.getChosenGodsFromDeck().get(2));

        gameTest.setGodsChosen(true);

        //Piazzo i worker
        gameTest.placeWorker(simone, simone.getPlayerWorkers().get(0), pos00);
        gameTest.placeWorker(simone, simone.getPlayerWorkers().get(1), pos01);
        gameTest.placeWorker(max, max.getPlayerWorkers().get(0), pos11);
        gameTest.placeWorker(max, max.getPlayerWorkers().get(1), pos10);
        gameTest.placeWorker(magdy, magdy.getPlayerWorkers().get(0), pos43);
        gameTest.placeWorker(magdy, magdy.getPlayerWorkers().get(1), pos44);

        //Controllo che effettivamente il worker in pos1 che leggo sulla mappa è il worker #1 di simone
        assertSame(simone.getPlayerWorkers().get(0), gameTest.getGameMap().getWorkerOnSquare(pos00));
        assertSame(simone.getPlayerWorkers().get(1), gameTest.getGameMap().getWorkerOnSquare(pos01));
        assertSame(max.getPlayerWorkers().get(0), gameTest.getGameMap().getWorkerOnSquare(pos11));
        assertSame(max.getPlayerWorkers().get(1), gameTest.getGameMap().getWorkerOnSquare(pos10));
        assertSame(magdy.getPlayerWorkers().get(0), gameTest.getGameMap().getWorkerOnSquare(pos43));
        assertSame(magdy.getPlayerWorkers().get(1), gameTest.getGameMap().getWorkerOnSquare(pos44));


        gameTest.getGameMap().printBoard();

        //Tocca al giocatore 1

        System.out.println("Inizio partita\nTurno di Simone");
        gameTest.setPlayerActive(simone);
        gameTest.initPlayerState(simone);

        //mi aspetto che il worker #1 sia bloccato
        assertTrue(gameTest.isWorkerStuck(simone.getPlayerWorkers().get(0)));
        //ma non il #2
        assertFalse(gameTest.isWorkerStuck(simone.getPlayerWorkers().get(1)));

        gameTest.moveWorker(simone, simone.getPlayerWorkers().get(1), pos02);
        gameTest.buildBlock(simone, simone.getPlayerWorkers().get(0), pos12);

        gameTest.getGameMap().printBoard();
        gameTest.setLastPlayerActive(gameTest.getPlayerActive());


        //Turno giocatore 2

        System.out.println("Turno di Max");
        gameTest.setPlayerActive(max);
        gameTest.initPlayerState(max);

        gameTest.moveWorker(max, max.getPlayerWorkers().get(0), pos12);
        gameTest.buildBlock(max, max.getPlayerWorkers().get(0), pos23);
        gameTest.setLastPlayerActive(gameTest.getPlayerActive());

        gameTest.getGameMap().printBoard();





        //gameTest.moveWorker(magdy, magdy.getPlayerWorkers().get(0), pos12);
        //gameTest.buildBlock(magdy, magdy.getPlayerWorkers().get(0), pos23);





    }

}