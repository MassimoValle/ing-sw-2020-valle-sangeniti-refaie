package it.polimi.ingsw.Network.Message.Enum;

public enum ResponseContent {

    LOGIN,
    NUM_PLAYER, // per la lobbysize

    CHOOSE_GODS,
    PICK_GOD,
    PLACE_WORKER,

    START_TURN,
    CHOOSE_WORKER,
    MOVE_WORKER,
    BUILD,
    CHECK,
    END_TURN,

    PLAYER_WON;
}
