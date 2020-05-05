package it.polimi.ingsw.Network.Message.Enum;

//Enumerations that indicates in what state of the connection the message is sent
public enum MessageContent {
    //FIRST_CONNECTION,
    //CONNECTION_RESPONSE,
    LOGIN,
    NUM_PLAYER, // per la lobbysize

    GODS_CHOSE, // godLikePlayer
    PICK_GOD,   // ogni giocatore si piglia un god
    PLACE_WORKER,

    //inizio turno
    STARTING_TURN,
    SELECT_WORKER,
    //movimento
    MOVE,
    END_MOVE,
    //costruzione
    BUILD,
    END_BUILD,
    //fine turno
    END_TURN,



    YOUR_TURN,
    WORKER_CHOSEN,
    WORKER_MOVED,
    PLAYERS_HAS_BUILT,
    CHECK,
    PLAYER_WON,
    ;
}
