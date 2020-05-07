package it.polimi.ingsw.Network.Message.Enum;

//Enumerations that indicates in what state of the connection the message is sent
public enum RequestContent {

    LOGIN,
    NUM_PLAYER, // per la lobbysize

    CHOSEN_GODS, // godLikePlayer
    PICKED_GOD,   // ogni giocatore si piglia un god
    PLACED_WORKER,

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

    CHECK;
}
