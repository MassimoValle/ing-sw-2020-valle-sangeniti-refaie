package it.polimi.ingsw.server.model.action;

public enum ActionOutcome {
    DONE ("Action has been done!"),
    NOT_DONE ("Action hasn't been done!"),
    DONE_CAN_BE_DONE_AGAIN ("Action has been done, the player can perform the same action again!"),

    WINNING_MOVE ("The player has won with the action performed"),

    ENDLESS_LOOP ("Situation where you won't be able to complete your turn, so the server brings you back at the start of your turn");



    private final String desc;

    ActionOutcome(String desc) {
        this.desc = desc;
    }
}
