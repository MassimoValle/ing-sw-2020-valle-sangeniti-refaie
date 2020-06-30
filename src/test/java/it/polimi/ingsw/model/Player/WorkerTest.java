package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    GameMap gameMap;


    @Before
    public void setUp() throws Exception {
        GameMap gameMap = new GameMap();
    }

    @Test
    public void checkWorkersPositionAndNumber() {

        Worker worker1 = new Worker(new Player("test"), 0);
        assertEquals(0, worker1.getNumber());

        worker1.setPosition(new Position(0,0));
        assertEquals(new Position(0,0), worker1.getPosition());

        Worker worker2 = new Worker(new Player("test"),1);
        assertEquals(1, worker2.getNumber());

        worker2.setPosition(new Position(0,1));
        assertEquals(new Position(0,1), worker2.getPosition());
    }

    @Test
    public void checkIfSelectedAndPlaced() {
        Worker worker1 = new Worker(new Player("test"),0);
        Worker worker2 = new Worker(new Player("test"),0);

        assertFalse(worker1.isPlaced());
        assertFalse(worker1.isSelected());

        assertFalse(worker2.isPlaced());
        assertFalse(worker2.isSelected());

        worker1.setSelected(true);
        worker1.setPlaced(true);
        assertTrue(worker1.isPlaced());
        assertTrue(worker1.isSelected());

        assertFalse(worker2.isPlaced());
        assertFalse(worker2.isSelected());
    }


}