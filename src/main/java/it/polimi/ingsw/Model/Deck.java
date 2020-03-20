package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private static Deck istance=null;
    private List<God> gods;
    private final int numGods = 5;

    private Deck() {
        gods = new ArrayList<>();
        for (int i = 0; i < numGods; i++) { gods.add(new God()); }
    }

    public static Deck getIstance() {
        if(istance==null)
            istance = new Deck();
        return istance;
    }


    public void removeGod(God selectedGod) {
        gods.remove(selectedGod);
    }

    // public void initDeck() { };  // -> default constructor is the new initDeck()


}
