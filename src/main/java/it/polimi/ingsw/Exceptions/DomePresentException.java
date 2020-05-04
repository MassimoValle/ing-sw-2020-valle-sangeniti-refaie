package it.polimi.ingsw.Exceptions;

public class DomePresentException extends SantoriniException {

    public DomePresentException() {
        super("There's already a dome!");
    }

}
