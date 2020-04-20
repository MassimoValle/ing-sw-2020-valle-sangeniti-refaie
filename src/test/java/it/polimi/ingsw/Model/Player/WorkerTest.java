package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    GameMap gameMap;
    Worker worker1;
    Position pos1;

    @Before
    public void setUp() throws Exception {
        GameMap gameMap = new GameMap();
        worker1 = new Worker(0);
        pos1 = new Position(0,0);
    }

    @Test
    public void setPosition() {
        worker1.setPosition(pos1);
        assertEquals(pos1, worker1.getWorkerPosition());

    }



}