package it.polimi.ingsw.client.view.cli;

import java.io.PrintStream;

public class SantoriniStream extends PrintStream {

    public static final String GAME_MANAGER_SAYS = "\nGame manager says";
    public static final String INSERT_A_NUMBER = "Please insert a number!";

    SantoriniStream() {
        super(System.out, true);
    }

}
