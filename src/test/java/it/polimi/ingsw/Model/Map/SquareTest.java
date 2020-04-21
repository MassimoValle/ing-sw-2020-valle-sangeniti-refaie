package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    Square square;

    @Before
    public void initClass() {
        square = new Square(0, 0);
    }


    @Test
    public void getWorkerOnSquare(){
        assertNull(square.getWorkerOnSquare());

        Worker worker1 = new Worker(0);
        square.setWorkerOn (worker1);
        assertEquals(worker1, square.getWorkerOnSquare());
    }

    @Test
    public void hasWorkerOnSquare(){
        Worker worker1 = new Worker(0);
        square.setWorkerOn (worker1);
        assertTrue(square.hasWorkerOn());
    }
}
