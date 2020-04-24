package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameMapTest {

    GameMap gameMap;
    Position pos1 = new Position(0,0);
    Position pos2 = new Position(0,1);
    Position pos3 = new Position(1,0);

    @Before
    public void setUp() throws Exception {
        gameMap = new GameMap();
    }

    @Test
    public void everySquareToLevelZero() {
        for (int i=0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, gameMap.getBoard()[i][j].getHeight());
            }
        }
    }

    /*@Test
    public void increaseHeight() {
        gameMap.getBoard()[0][0].heightPlusOne();
        gameMap.getBoard()[0][0].heightPlusOne();

        assertEquals(2, gameMap.getBoard()[0][0].getHeight());

        System.out.println("Let's ceck the difference between position 0,0 and 0,1");
        assertEquals(-2, gameMap.getDifferenceInAltitude(pos2, pos1));

        //Mi aspetto che la pos 0,0 sia adiacente
        System.out.println(pos2.getAdjacentPlaces());

        //Ma che non sia raggiungibile
        System.out.println(gameMap.getReachableAdjacentPlaces(pos2).toString());

    }*/



    /*

    @Test
    public void setWorkerPosition(){
        Worker worker1 = new Worker(0);
        Worker worker2 = new Worker(1);
        gameMap.setWorkerPosition (worker1, pos1);
        gameMap.setWorkerPosition(worker2, pos2);

        assertTrue(gameMap.getSquare(pos1).hasWorkerOn());
        assertTrue(gameMap.getSquare(pos2).hasWorkerOn());


        gameMap.printBoard();

    }

     */

    /*
    @Test
    public void workerOnSquare(){
        Worker worker1 = new Worker(0);
        gameMap.setWorkerPosition (worker1, pos1);

        assertEquals(worker1, gameMap.getWorkerOnSquare(pos1));
    }

     */

    /*
    @Test
    public void squareHeight(){
        gameMap.getBoard()[0][0].heightPlusOne();
        gameMap.getBoard()[0][0].heightPlusOne();
        gameMap.getBoard()[0][0].heightPlusOne();
        gameMap.getBoard()[0][0].heightPlusOne();

        gameMap.setSquareHeight(pos1, 4);


        assertEquals(4, gameMap.getSquareHeight(pos1));

    }
    */


    @Test
    public void placesWhereYouCanBuildOn(){
        assertNotNull(gameMap.getPlacesWhereYouCanBuildOn(pos1));

        List<Position> positions = gameMap.getPlacesWhereYouCanBuildOn(pos1);

        assertEquals(new Position(0, 1), positions.get(0));
        assertEquals(new Position(1, 0), positions.get(2));
        assertEquals(new Position(1, 1), positions.get(1));

    }


    /*
    @Test
    public void addBlock(){
        Position pos = new Position(0, 0);
        gameMap.addBlock(pos);
        assertEquals(1, gameMap.getSquareHeight(pos));
    }


     */




}