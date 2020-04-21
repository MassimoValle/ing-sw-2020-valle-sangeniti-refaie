package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Deck;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    static Deck deck;

    @BeforeClass
    public static void initClass() {
        deck = Deck.getInstance();
    }


    @Test
    public void deckIsNotNull() {
        assertNotNull(deck);
    }

    @Test
    public void deckHave9Gods() {
        assertEquals(deck.size(), 9);
    }

    @Test
    public void eachGodIsUnique() {

        for(int i=0; i < deck.size(); i++){
            for (int j = i+1; j < deck.size(); j++) {
                assertNotEquals(deck.get(i), deck.get(j));
            }
        }

    }

    @Test
    public void getGod() {
        assertTrue(deck.contains(deck.getGod(1)));

    }
}
