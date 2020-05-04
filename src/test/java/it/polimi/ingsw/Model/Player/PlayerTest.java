package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player1;

    @Before
    public void setUp() throws Exception {
        player1 = new Player("player1");
    }

    @Test
    public void playerHasTwoWorkers() {
        assertEquals("player1", player1.getPlayerName());
        assertEquals(2, player1.getPlayerWorkers().size());
    }

    @Test
    public void workersNumberCheckAfterPlayerAdd() {
        assertEquals(0, player1.getPlayerWorkers().get(0).getWorkersNumber());
        assertEquals(1, player1.getPlayerWorkers().get(1).getWorkersNumber());
        Player player2 = new Player("player2");
        assertEquals(0, player2.getPlayerWorkers().get(0).getWorkersNumber());
        assertEquals(1, player2.getPlayerWorkers().get(1).getWorkersNumber());
    }

    @Test
    public void newWorkerAddedAFTERplayerAdded() {
        player1.addNewWorker();
        assertEquals(3, player1.getPlayerWorkers().get(2).getWorkersNumber());
        assertEquals(3, player1.getPlayerWorkers().size());
    }

    @Test
    public void checkPlayerGod() {
        assertFalse(player1.godAssigned());
        player1.setPlayerGod(new Game().getDeck().get(0));
        assertEquals(new Game().getDeck().get(0),player1.getPlayerGod());
        assertTrue(player1.godAssigned());
    }

    @Test
    public void checkWorkersPlaced() {
        assertFalse(player1.areWorkersPlaced());

        player1.getPlayerWorkers().get(0).setPlaced(true);
        player1.getPlayerWorkers().get(1).setPlaced(true);
        assertTrue(player1.areWorkersPlaced());
    }



}