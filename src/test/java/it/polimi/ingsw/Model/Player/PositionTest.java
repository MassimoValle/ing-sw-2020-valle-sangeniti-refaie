package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Map.GameMap;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PositionTest {

    GameMap gameMap = new GameMap();


    @Test
    public void checkList() {
        Position position = new Position(1,1);
        ArrayList<Position> list = new ArrayList<>();
        list = position.getAdjacentPlaces();
        System.out.println(list.toString());
        System.out.println(list.size());
    }


}