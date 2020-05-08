package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    @Before
    public void initClass() {
        Game.resetInstance();
        game = Game.getInstance();
    }

    @Test
    public void gameNotNull(){
        assertNotNull(game);
    }

    @Test
    public void ensureEverythingIsInitialized() {
        assertNotNull("Game deck", game.getDeck());
        assertNotNull("Game Map", game.getGameMap());
        assertEquals(0, game.getPlayers().size());
        assertEquals(0, game.getChosenGodsFromDeck().size());
        assertEquals(0, game.getNumberOfPlayers());
    }

    @Test
    public void playersListIsEmpty() {
        assertEquals(0, game.getPlayers().size());
    }

    @Test
    public void addPlayerTest() {
        //Adding 1 player
        Player player1 = new Player("player1");
        game.addPlayer(player1);

        assertNotNull(game.searchPlayerByName("player1"));

        //adding 2 player
        Player player2 = new Player("player2");
        game.addPlayer(player2);

        assertNotNull(game.searchPlayerByName("player2"));

        //adding 3 player
        Player player3 = new Player("player3");
        game.addPlayer(player3);

        assertNotNull(game.searchPlayerByName("player3"));
    }

    @Test
    public void godsInGameTest() {
        ArrayList<God> godsChosen = new ArrayList<>();
        godsChosen.add(game.getDeck().getGod(0));
        godsChosen.add(game.getDeck().getGod(1));
        godsChosen.add(game.getDeck().getGod(2));

        //chosenGodsFromDeck is empty
        assertEquals(0, game.getChosenGodsFromDeck().size());

        game.setChosenGodsFromDeck(godsChosen);

        assertNotEquals(0, game.getChosenGodsFromDeck().size());

        //The gods in game are the same chosen from the deck
        assertEquals(godsChosen.get(0), game.getChosenGodsFromDeck().get(0));
        assertEquals(godsChosen.get(1), game.getChosenGodsFromDeck().get(1));
        assertEquals(godsChosen.get(2), game.getChosenGodsFromDeck().get(2));

    }

    @Test
    public void everyoneHasPickedAGod() {
        Player player1 = new Player("player1");
        game.addPlayer(player1);
        //adding 2 player
        Player player2 = new Player("player2");
        game.addPlayer(player2);
        //adding 3 player
        Player player3 = new Player("player3");
        game.addPlayer(player3);

        assertFalse(game.godsPickedByEveryone());

        player1.setPlayerGod(game.getDeck().get(0));
        player2.setPlayerGod(game.getDeck().get(1));
        player3.setPlayerGod(game.getDeck().get(2));

        assertTrue(game.godsPickedByEveryone());

    }

    @Test
    public void everyoneHasPlacedItsWorkers() {
        //New player added to the game
        Player player1 = new Player("player1");
        game.addPlayer(player1);

        //Workers still not placed
        assertFalse(game.workersPlacedByEveryone());

        player1.getPlayerWorkers().get(0).setPlaced(true);
        assertFalse(game.workersPlacedByEveryone());

        player1.getPlayerWorkers().get(1).setPlaced(true);
        //Now the workers are placed
        assertTrue(game.workersPlacedByEveryone());

        Player player2 = new Player("player2");
        game.addPlayer(player2);

        //Now the second player's workers are not placed so...
        assertFalse(game.workersPlacedByEveryone());

        player2.getPlayerWorkers().get(0).setPlaced(true);
        assertFalse(game.workersPlacedByEveryone());

        player2.getPlayerWorkers().get(1).setPlaced(true);
        assertTrue(game.workersPlacedByEveryone());

        Player player3 = new Player("player3");
        game.addPlayer(player3);

        assertFalse(game.workersPlacedByEveryone());

        player3.getPlayerWorkers().get(0).setPlaced(true);
        assertFalse(game.workersPlacedByEveryone());

        player3.getPlayerWorkers().get(1).setPlaced(true);
        assertTrue(game.workersPlacedByEveryone());

    }

}