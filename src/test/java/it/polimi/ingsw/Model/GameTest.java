package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game gameTest;
    Position pos1 = new Position(0,0);
    Position pos2 = new Position(0,1);
    Position pos3 = new Position(1,1);
    Position pos4 = new Position(1,0);

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
        gameTest.placeWorker(player1, player1.getPlayerWorkers().get(0), pos1);

        //Blocking the worker
        gameTest.getGameMap().setSquareHeight(pos2, 3);
        gameTest.getGameMap().setSquareHeight(pos3, 3);
        gameTest.getGameMap().setSquareHeight(pos4, 3);

        //Check if it's stuck
        assertTrue(gameTest.isWorkerStuck(player1.getPlayerWorkers().get(0)));


    }

}