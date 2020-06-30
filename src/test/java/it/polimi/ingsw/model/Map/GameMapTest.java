package it.polimi.ingsw.model.Map;

import it.polimi.ingsw.exceptions.DomePresentException;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameMapTest {

    GameMap gameMap;

    @Before
    public void setUp() throws Exception {
        gameMap = new GameMap();

    }



    @Test
    public void everySquareToLevelZero() {
        assertNotNull(gameMap);
        for (int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, gameMap.getBoard()[i][j].getHeight());
            }
        }
    }

    @Test
    public void checkDifference() throws DomePresentException {
        gameMap.getBoard()[0][0].addBlock(true);


        assertEquals(1, gameMap.getBoard()[0][0].getHeight());

        System.out.println("Let's ceck the difference between position 0,0 and 0,1");
        assertEquals(1, gameMap.getDifferenceInAltitude(new Position(0,0), new Position(0,1)));

        //Pos (0,0) is adjacent...
        System.out.println(new Position(0,1).getAdjacentPlaces());

        //...but not reachable
        System.out.println(gameMap.getReachableAdjacentPlaces(new Position(0,1)).toString());

        System.out.println(gameMap.getPlacesWhereYouCanBuildOn(new Position(0,1)));
    }




    @Test
    public void placesWhereYouCanBuildOn() throws Exception {
        Position pos1 = new Position(1,1);
        assertNotNull(gameMap.getPlacesWhereYouCanBuildOn(pos1));

        List<Position> positions = gameMap.getPlacesWhereYouCanBuildOn(pos1);

        //Assuming we are in pos(1,1) we can build in all the surrounding squares
        assertEquals(8, positions.size());
        assertEquals(new Position(0, 1), positions.get(0));
        assertEquals(new Position(0, 2), positions.get(1));
        assertEquals(new Position(1, 2), positions.get(2));
        assertEquals(new Position(2, 2), positions.get(3));
        assertEquals(new Position(2, 1), positions.get(4));
        assertEquals(new Position(2, 0), positions.get(5));
        assertEquals(new Position(1, 0), positions.get(6));
        assertEquals(new Position(0, 0), positions.get(7));

        //Let's put a dome on the square in pos(0,0)
        gameMap.getSquare(new Position(0,0)).addBlock(true);

        positions = gameMap.getPlacesWhereYouCanBuildOn(pos1);
        assertEquals(7, positions.size());

        gameMap.printBoard();

    }


    @Test
    public void workerOnSquareTest() {
        Position pos1 = new Position(0,0);
        Worker worker1 = new Worker(new Player("test"),0);

        assertNull(gameMap.getWorkerOnSquare(pos1));

        gameMap.getSquare(pos1).setWorkerOn(worker1);
        assertEquals(worker1, gameMap.getWorkerOnSquare(pos1));
    }


    @Test
    public void checkLegalPosition() {
        Position pos1 = new Position(5,5);
        assertFalse("Due to the board is 5x5 (from 0,0 to 4,4), pos1 must be NOT REAL", gameMap.isPositionOnMapReal(pos1));

        Position pos2 = new Position(0,0);
        assertTrue(gameMap.isPositionOnMapReal(pos2));


        //The pos2 (0,0) has to be free
        assertTrue(gameMap.isPositionFree(pos2));

        Worker worker1 = new Worker(new Player("test"),0);
        worker1.setPosition(pos2);
        worker1.setPlaced(true);
        gameMap.getSquare(pos2).setWorkerOn(worker1);

        assertFalse(gameMap.isPositionFree(pos2));

        //isPositionValid return false if 1) pos is not valid or 2) pos is not real
        Position pos3 = new Position(3,3);
        assertTrue(gameMap.isPositionValid(pos3));

        assertFalse("Pos1 (5,5) is out of bound",gameMap.isPositionValid(pos1));
        assertFalse("Pos2 (0,0) is inside the board, but has a worker on", gameMap.isPositionValid(pos2));


    }

}