package it.polimi.ingsw.model;

import it.polimi.ingsw.server.model.God.Deck;
import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.server.model.Map.GameMap;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

    static Deck deck;

    @BeforeClass
    public static void initClass() {
        deck = new Deck(new GameMap());
    }


    @Test
    public void deckIsNotNull() {
        assertNotNull(deck);
    }

    @Test
    public void deckHave14Gods() {
        assertEquals(deck.size(), 14);
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
        //deck.getGod(i) DOESN'T REMOVE A CARD FROM THE DECK
        assertTrue(deck.contains(deck.getGod(1)));
    }

    @Test
    public void checkGod() {
        God apollo = deck.getGod(0);
        God chronus = deck.getGod(9);

        assertTrue(apollo.is("Apollo"));
        assertFalse(apollo.is("Artemis"));

        assertTrue(chronus.is("Chronus"));
        assertFalse(chronus.is("Chronu"));

    }
}
