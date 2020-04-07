package it.polimi.ingsw.Model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("Snake");
    }

    @Test
    public void playerHasTwoWorkers() {
        assertEquals(2, player.getPlayerWorkers().size());
    }

    @Test
    public void workersNumberCheckAfterPlayerAdd() {
        assertEquals(1, player.getPlayerWorkers().get(0).getWorkersNumber());
        assertEquals(2, player.getPlayerWorkers().get(1).getWorkersNumber());
    }


    @After
    public void tearDown() throws Exception {
    }
}