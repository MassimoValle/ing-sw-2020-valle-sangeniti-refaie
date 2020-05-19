package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Exceptions.DomePresentException;
import it.polimi.ingsw.Server.Model.Building.Dome;
import it.polimi.ingsw.Server.Model.Building.LevelOneBlock;
import it.polimi.ingsw.Server.Model.Building.LevelThreeBlock;
import it.polimi.ingsw.Server.Model.Building.LevelTwoBlock;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Worker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    Square square;

    @Before
    public void initClass() {
        square = new Square(0,0);
    }


    @Test
    public void getWorkerOnSquare(){
        assertNull(square.getWorkerOnSquare());

        Worker worker1 = new Worker(0);
        square.setWorkerOn (worker1);
        assertEquals(worker1, square.getWorkerOnSquare());

        square.freeSquare();
        assertFalse(square.hasWorkerOn());
    }

    @Test
    public void hasWorkerOnSquare(){
        assertFalse(square.hasWorkerOn());
        Worker worker1 = new Worker(0);
        square.setWorkerOn (worker1);
        assertTrue(square.hasWorkerOn());
    }

    @Test
    public void checkIfBuilt() throws DomePresentException {
        Worker worker1 = new Worker(0);

        assertEquals(0, square.getHeight());
        assertFalse(square.hasBeenBuiltOver());

        square.addBlock(false);
        assertEquals(1, square.getHeight());
        assertTrue(square.hasBeenBuiltOver());
        assertTrue(square._getTower().get(0) instanceof LevelOneBlock);
        assertFalse(square.hasDome());
        square.addBlock(false);
        assertTrue(square._getTower().get(1) instanceof LevelTwoBlock);

        square.addBlock(false);
        assertTrue(square._getTower().get(2) instanceof LevelThreeBlock);

        square.addBlock(true);
        assertEquals(4, square.getHeight());
        assertTrue(square.hasDome());
        assertTrue(square._getTower().get(3) instanceof Dome);

        //adding a new block when the tower.size() is >= 4 throws an exception
        System.out.println(square.toString());

    }



}
