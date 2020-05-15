package it.polimi.ingsw.Network.Message.Enum;

public enum ResponseContent {

    LOGIN ("It follows the --->>>", RequestContent.LOGIN),
    NUM_PLAYER ("The server asking to the first player who made a login request how many players"), // per la lobbysize

    NOT_YOUR_TURN ("If you perform an action during your turn, it follows any kind of request"),

    SHOW_DECK ("The godlike player is notified to chose # gods"),
    CHOOSE_GODS ("The server check if the gods chosen by the godlike player are ok"),


    PICK_GOD ("The player has to chose a god and get the response"),
    PLACE_WORKER ("The player has to place his worker and get a response"),

    START_TURN ("The player is notified that his turn is starting"),
    SELECT_WORKER ("The player has to selesct a worker and get a response"),

    POWER_BUTTON ("The player clicked the power button"),

    MOVE_WORKER ("The player has to move and get a response"),
    BUILD ("The player has to built and get a response"),

    END_TURN ("The player has finished his turn"),
    PLAYER_WON ("The player has won"),

    CHECK ("Not sure where this should be really used");

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
