package it.polimi.ingsw.Client.View.Cli;

import it.polimi.ingsw.Utility.Ansi;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.io.PrintStream;

public class SantoriniStream extends PrintStream {

    public SantoriniStream(@NotNull OutputStream out) {
        super(out, true);
    }

    public void clear(SantoriniStream consoleOut) {

        consoleOut.print(Ansi.CLEAR_CONSOLE);
        consoleOut.flush();

    }
}
