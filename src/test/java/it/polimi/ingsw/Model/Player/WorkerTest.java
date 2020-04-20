package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorkerTest {

    @Before
    public void setUp() throws Exception {
        Game.getInstance().addPlayer("simone");

    }

    @Test
    public void playerHasTwoWorkers() throws PlayerNotFoundException {
        assertEquals(2, Game.getInstance().searchPlayerByName("simone").getPlayerWorkers().size());
    }

    @Test
    public void placeWorkerInPosition() throws PlayerNotFoundException {
        Position pos1 = new Position(0,0);
        //Game.getInstance().searchPlayerByName("simone").placeWorker(Game.getInstance().searchPlayerByName("simone").getPlayerWorkers().get(0), pos1);

        assertTrue(Game.getInstance().getGameMap().getSquare(pos1).hasWorkerOn());


        assertEquals(pos1, Game.getInstance().getPlayers().get(0).getPlayerWorkers().get(0).getWorkerPosition());

    }

    @Test
    public void checkIfWorkerStuck() throws PlayerNotFoundException {
        Position pos1 = new Position(0,0);
        Position pos2 = new Position(0,1);
        Position pos3 = new Position(1,1);
        Position pos4 = new Position(1,0);
        //Game.getInstance().searchPlayerByName("simone").placeWorker(Game.getInstance().searchPlayerByName("simone").getPlayerWorkers().get(0), pos1);
        Game.getInstance().getGameMap().getSquare(pos2).heightPlusOne();
        Game.getInstance().getGameMap().getSquare(pos2).heightPlusOne();
        Game.getInstance().getGameMap().getSquare(pos3).heightPlusOne();
        Game.getInstance().getGameMap().getSquare(pos3).heightPlusOne();
        Game.getInstance().getGameMap().getSquare(pos4).heightPlusOne();

        assertFalse(Game.getInstance().getPlayers().get(0).getPlayerWorkers().get(0).isStuck());

        Game.getInstance().getGameMap().getSquare(pos4).heightPlusOne();
        assertTrue(Game.getInstance().getPlayers().get(0).getPlayerWorkers().get(0).isStuck());

        System.out.println(Game.getInstance().getGameMap().printBoard());

        //Game.getInstance().searchPlayerByName("simone").placeWorker(Game.getInstance().searchPlayerByName("simone").getPlayerWorkers().get(1), pos2);
        System.out.println(Game.getInstance().getGameMap().printBoard());

    }


}