package it.polimi.ingsw.Server.Model.Action;

public enum ActionOutcome {
    DONE ("Action has been done!"),
    NOT_DONE ("Action hasn't been done!"),
    DONE_CAN_BE_DONE_AGAIN ("Action has been done, the player can perform the same action again!");



    private final String desc;

    ActionOutcome(String desc) {
        this.desc = desc;
    }
}
