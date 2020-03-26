package it.polimi.ingsw;

import it.polimi.ingsw.Model.Deck;
import org.junit.Test;

import static org.junit.Assert.*;

public class testDeck {

    Deck deck;


    @Test
    public void deckIsNotNull() {
        deck = Deck.getIstance();
        assertNotNull(deck);
    }

    @Test
    public void deckHave9Gods() {
        deck = Deck.getIstance();
        assertEquals(deck.size(), 9);
    }

    @Test
    public void eachGodIsUnique() {
        deck = Deck.getIstance();

        for(int i=0; i < deck.size(); i++){
            for (int j = i+1; j < deck.size(); j++) {
                assertNotEquals(deck.get(i), deck.get(j));
            }
        }

    }
}
