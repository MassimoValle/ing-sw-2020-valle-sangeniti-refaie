package it.polimi.ingsw.Controller;

public enum PossibleGameState {
    IN_LOBBY ("Game waiting for players to connect"),
    READY_TO_PLAY ("Game is ready to start!"),
    GODLIKE_PLAYER_MOMENT ("One player have to pick #playerInGame gods"),
    CHOOSING_GOD ("Every player has to chose 1 god from the ones selected by the Godlike player"),
    PLACING_WORKERS ("Each player has to place his workers on the board"),
    START_GAME ("The first player start playing"),
    ;


    private String description;

    PossibleGameState(String description) {
        this.description = description;
    }
}
