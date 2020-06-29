package it.polimi.ingsw.exceptions;

public class DomePresentException extends SantoriniException {

    public DomePresentException() {
        super("There's already a dome!");
    }

}
