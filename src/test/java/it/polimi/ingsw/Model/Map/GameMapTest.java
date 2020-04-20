package it.polimi.ingsw.Model.Map;

import it.polimi.ingsw.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameMapTest {

    GameMap gameMap;
    Position pos1 = new Position(0,0);
    Position pos2 = new Position(0,1);

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

    @Test
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

    }

}