package it.polimi.ingsw;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testPlayer {

    Player player;
    String name = "player1";

    @Test
    public void checkName() {
        player = new Player(name);
        assertEquals(player.getPlayerName(), name);
    }

    @Test
    public void has2Worker() {
        player = new Player(name);
        assertEquals(player.getPlayerWorkers().size(), 2);
    }
}
