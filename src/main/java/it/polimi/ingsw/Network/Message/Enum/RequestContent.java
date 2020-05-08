package it.polimi.ingsw.Network.Message.Enum;

//Enumerations that indicates in what state of the connection the message is sent
public enum RequestContent {

    LOGIN ("The login request that the client when it starts"),
    NUM_PLAYER ("The number of players we want to create a match for"), // per la lobbysize

    CHOSE_GODS ("The godlike player send the gods chosen to take part pf the game"), // godLikePlayer
    PICK_GOD ("The player picking up a god for thr match"),   // ogni giocatore si piglia un god
    PLACE_WORKER ("The player wants to place his worker"),

    //inizio turno
    SELECT_WORKER ("The player wants to select one of his worker"),

    //movimento
    MOVE ("The player wants to move his worker"),
    END_MOVE ("The player wants to stop moving"),
    //costruzione
    BUILD ("The player wants to build"),
    END_BUILD ("The player wants to stop building"),
    //fine turno
    END_TURN ("The player wants to end his turn"),



    CHECK ("We don't know when to really use this");


    private final String description;

    RequestContent(String string) {
        this.description = string;
    }
}
