package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.model.Building.Dome;
import it.polimi.ingsw.server.model.Building.LevelOneBlock;
import it.polimi.ingsw.server.model.Building.LevelThreeBlock;
import it.polimi.ingsw.server.model.Building.LevelTwoBlock;
import it.polimi.ingsw.server.model.Map.Square;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Worker;
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

        Worker worker1 = new Worker(new Player("test"),0);
        square.setWorkerOn (worker1);
        assertEquals(worker1, square.getWorkerOnSquare());

        square.freeSquare();
        assertFalse(square.hasWorkerOn());
    }

    @Test
    public void hasWorkerOnSquare(){
        assertFalse(square.hasWorkerOn());
        Worker worker1 = new Worker(new Player("test"),0);
        square.setWorkerOn (worker1);
        assertTrue(square.hasWorkerOn());
    }

    @Test
    public void checkIfBuilt() throws DomePresentException {
        Worker worker1 = new Worker(new Player("test"),0);

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


    @Test
    public void SquareTest() throws DomePresentException {

        Square sq1 = new Square(1,1);
        Square sq2 = new Square(2,2);

        Worker w1 = new Worker(new Player("pl1"), 1);
        sq1.setWorkerOn(w1);


        assertNotEquals(sq1, sq2);

        Square sq1copy = new Square(1,1);

        Worker w2 = new Worker(new Player("pl1"), 1);
        sq1copy.setWorkerOn(w2);

        assertNotEquals(sq1,sq1copy);
        assertNotSame(sq1,sq1copy);

        sq1copy.setWorkerOn(w1);
        assertEquals(sq1,sq1copy);

        sq1.addBlock(false);
        sq1copy.addBlock(false);

        assertEquals(sq1,sq1copy);



    }



}
