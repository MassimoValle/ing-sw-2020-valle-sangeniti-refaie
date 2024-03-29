package it.polimi.ingsw.network.message.Enum;

public enum ResponseContent {

    LOGIN ("It follows the --->>>", RequestContent.LOGIN),
    NUM_PLAYER ("The server asking to the first player who made a login request how many players"), // per la lobbysize

    NOT_YOUR_TURN ("If you perform an action during your turn, it follows any kind of request"),

    SHOW_DECK ("The godlike player is notified to chose # gods"),
    CHOOSE_GODS ("The server check if the gods chosen by the godlike player are ok"),


    PICK_GOD ("The player has to chose a god and get the response"),
    PLACE_WORKER ("The player has to place his worker and get a response"),

    START_TURN ("The player is notified that his turn is starting"),
    SELECT_WORKER ("The player has to select a worker and get a response"),

    POWER_BUTTON ("The player clicked the power button"),

    MOVE_WORKER ("The player has to move and get a response"),
    MOVE_WORKER_AGAIN ("The player can decide either to move or to stop moving and start building"),
    END_MOVE ("The player decides to stop moving and start building"),

    BUILD ("The player has to built and get a response"),
    BUILD_AGAIN ("The player can decide either to build or to end his turn"),
    END_BUILD ("The player decides to stop from building"),

    END_TURN ("The player has finished his turn"),
    PLAYER_HAS_WON("The player has won"),
    PLAYER_HAS_LOST ("The player has lost"),

    CHECK ("Not sure where this should be really used"),

    DISCONNECT ("Whether a client gets disconnected");

    private final String description;
    private final RequestContent follows;

    ResponseContent(String description, RequestContent request) {
        this.description = description;
        this.follows = request;
    }

    ResponseContent(String description) {
        this(description, RequestContent.CHECK);
    }
}
