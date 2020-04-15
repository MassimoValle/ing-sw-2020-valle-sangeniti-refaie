package it.polimi.ingsw.Model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

public class GameTest {

    Game gameTest;

    @Before
    public void setUp() throws Exception {
        gameTest = new Game();
    }

    @Test
    public void gameNotNull(){
        assertNotNull(gameTest);
    }

    @Test
    public void playersListIsEmpty() {
        assertEquals(gameTest.getPlayers().size(), 0);
    }

}