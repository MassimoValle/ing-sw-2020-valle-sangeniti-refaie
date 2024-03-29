package it.polimi.ingsw.model.Player;

import it.polimi.ingsw.server.model.player.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PositionTest {

    Position position;

    @Before
    public void initClass() {
        position = new Position(0, 0);
    }

    @Test
    public void checkList() {
        Position position = new Position(1,1);
        ArrayList<Position> list = new ArrayList<>();
        list = position.getAdjacentPlaces();
        assertEquals(8, list.size());
    }

    @Test
    public void adjacentPlaces(){

        //row: 0
        assertNotNull(position.getAdjacentPlaces());
        assertEquals(3, position.getAdjacentPlaces().size());
        assertEquals(5, new Position(0,1).getAdjacentPlaces().size());
        assertEquals(5, new Position(0,2).getAdjacentPlaces().size());
        assertEquals(5, new Position(0,3).getAdjacentPlaces().size());
        assertEquals(3, new Position(0,4).getAdjacentPlaces().size());

        //row: 1
        assertEquals(5, new Position(1,0).getAdjacentPlaces().size());
        assertEquals(8, new Position(1,1).getAdjacentPlaces().size());
        assertEquals(8, new Position(1,2).getAdjacentPlaces().size());
        assertEquals(8, new Position(1,3).getAdjacentPlaces().size());
        assertEquals(5, new Position(1,4).getAdjacentPlaces().size());

        //row: 2
        assertEquals(5, new Position(2,0).getAdjacentPlaces().size());
        assertEquals(8, new Position(2,1).getAdjacentPlaces().size());
        assertEquals(8, new Position(2,2).getAdjacentPlaces().size());
        assertEquals(8, new Position(2,3).getAdjacentPlaces().size());
        assertEquals(5, new Position(2,4).getAdjacentPlaces().size());

        //row: 3
        assertEquals(5, new Position(3,0).getAdjacentPlaces().size());
        assertEquals(8, new Position(3,1).getAdjacentPlaces().size());
        assertEquals(8, new Position(3,2).getAdjacentPlaces().size());
        assertEquals(8, new Position(3,3).getAdjacentPlaces().size());
        assertEquals(5, new Position(3,4).getAdjacentPlaces().size());

        //row: 4
        assertEquals(3, new Position(4,0).getAdjacentPlaces().size());
        assertEquals(5, new Position(4,1).getAdjacentPlaces().size());
        assertEquals(5, new Position(4,2).getAdjacentPlaces().size());
        assertEquals(5, new Position(4,3).getAdjacentPlaces().size());
        assertEquals(3, new Position(4,4).getAdjacentPlaces().size());
    }

    @Test
    public void isPerimetral() {
        Position pos1 = new Position(0,3);
        assertTrue(pos1.isPerimetral());

        Position pos2 = new Position(1,1);
        assertFalse(pos2.isPerimetral());
    }

    @Test
    public void isClose() {

        assertTrue(new Position(1,1).isClose(new Position(2,2)));
        assertTrue(new Position(1,1).isClose(new Position(1,2)));
        assertTrue(new Position(1,1).isClose(new Position(0,2)));
        assertTrue(new Position(1,1).isClose(new Position(0,1)));
        assertTrue(new Position(1,1).isClose(new Position(0,0)));
        assertTrue(new Position(1,1).isClose(new Position(1,0)));
        assertTrue(new Position(1,1).isClose(new Position(2,0)));
        assertTrue(new Position(1,1).isClose(new Position(2,1)));


        assertFalse(new Position(1,1).isClose(new Position(2,3)));
        assertFalse(new Position(1,1).isClose(new Position(1,3)));
        assertFalse(new Position(1,1).isClose(new Position(0,3)));

    }


    @Test
    public void isInCorner() {

        assertTrue(new Position(0,0).isInCorner());
        assertFalse(new Position(0,1).isInCorner());

    }



}