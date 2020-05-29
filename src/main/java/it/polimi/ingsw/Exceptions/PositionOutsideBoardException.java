package it.polimi.ingsw.Exceptions;

public class PositionOutsideBoardException extends SantoriniException {

    public PositionOutsideBoardException() {
        super("The position is outside the board!");
    }
}
