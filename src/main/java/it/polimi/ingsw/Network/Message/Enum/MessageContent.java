package it.polimi.ingsw.Network.Message.Enum;

//Enumerations that indicates in what state of the connection the message is sent
public enum MessageContent {
    FIRST_CONNECTION,
    CONNECTION_RESPONSE,
    LOGIN,
    NUM_PLAYER, // per la lobbysize

    GODS_CHOSE, // godLikePlayer
    PICK_GOD,   // ogni giocatore si piglia un god

    PLACE_WORKER,
    SELECT_WORKER,
    MOVE,
    BUILD,

    ASYNC,
    YOUR_TURN,
    WORKER_CHOSEN,
    WORKER_MOVED,
    PLAYERS_HAS_BUILT,
    END_OF_TURN,
    CHECK,
    ;
}
