package it.polimi.ingsw.Model.Player;

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
        assertEquals(2, player1.getPlayerWorkers().size());
    }

    @Test
    public void workersNumberCheckAfterPlayerAdd() {
        assertEquals(1, player1.getPlayerWorkers().get(0).getWorkersNumber());
        assertEquals(2, player1.getPlayerWorkers().get(1).getWorkersNumber());
        Player player2 = new Player("player2");
        assertEquals(1, player2.getPlayerWorkers().get(0).getWorkersNumber());
        assertEquals(2, player2.getPlayerWorkers().get(1).getWorkersNumber());
    }

    @Test
    public void newWorkerAddedAFTERplayerAdded() {
        player1.addNewWorker();
        assertEquals(3, player1.getPlayerWorkers().get(2).getWorkersNumber());
        assertEquals(3, player1.getPlayerWorkers().size());
    }


    @After
    public void tearDown() throws Exception {
    }
}