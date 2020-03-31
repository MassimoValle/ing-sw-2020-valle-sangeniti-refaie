package it.polimi.ingsw;

import it.polimi.ingsw.Model.Deck;
import org.junit.Test;

import static org.junit.Assert.*;

public class testDeck {

    Deck deck;


    @Test
    public void deckIsNotNull() {
        deck = Deck.getInstance();
        assertNotNull(deck);
    }

    @Test
    public void deckHave9Gods() {
        deck = Deck.getInstance();
        assertEquals(deck.size(), 9);
    }

    @Test
    public void eachGodIsUnique() {
        deck = Deck.getInstance();

        for(int i=0; i < deck.size(); i++){
            for (int j = i+1; j < deck.size(); j++) {
                assertNotEquals(deck.get(i), deck.get(j));
            }
        }

    }
}
