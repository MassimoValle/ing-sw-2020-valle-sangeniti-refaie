package it.polimi.ingsw.Network.Message;

//Enumerations that indicates in what state of the connection the message is sent
public enum MessageContent {
    FIRST_CONNECTION,
    CONNECTION_RESPONSE,
    LOGIN,

    PLACE_WORKER,
    MOVE,
    BUILD,

    GOD_SELECTION,
    ASYNC,
    YOUR_TURN,
    WORKER_CHOSEN,
    WORKER_MOVED,
    PLAYERS_HAS_BUILT,
    END_OF_TURN,
    CHECK,
    ;
}
