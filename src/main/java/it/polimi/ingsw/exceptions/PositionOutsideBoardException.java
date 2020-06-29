package it.polimi.ingsw.exceptions;

public class PositionOutsideBoardException extends SantoriniException {

    public PositionOutsideBoardException() {
        super("The position is outside the board!");
    }
}
