package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    @Before
    public void initClass() {
        game = new Game();
    }

    @Test
    public void gameNotNull(){
        assertNotNull(game);
    }

    @Test
    public void playersListIsEmpty() {
        assertEquals(game.getPlayers().size(), 0);
    }

    @Test
    public void addPlayer() throws PlayerNotFoundException {
        String name = "player";
        game.addPlayer(name);

        assertNotNull(game.searchPlayerByName(name));

    }

    @Test
    public void checkClone(){
        assertEquals(game.getPlayers(), game.clone().getPlayers());
        assertEquals(game.getGameMap(), game.clone().getGameMap());
        assertEquals(game.getPlayerActive(), game.clone().getPlayerActive());
        assertEquals(game.getDeck(), game.clone().getDeck());
        assertEquals(game.getNumberOfPlayers(), game.clone().getNumberOfPlayers());
    }

}