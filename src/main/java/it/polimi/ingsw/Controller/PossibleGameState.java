package it.polimi.ingsw.Controller;

public enum PossibleGameState {
    GAME_INIT ("Game manager has been just created"),
    IN_LOBBY ("Game waiting for players to connect"),
    READY_TO_PLAY ("Game is ready to start!"),
    FIRST_MOVE ("The turn has just started"),
    GODLIKE_PLAYER_MOMENT ("One player have to chose which gods are going to take part of the game"),
    CHOOSING_GOD ("Every player has to chose 1 god from the ones selected by the Godlike player"),

    SELECTING_WORKER ("The player select the worker to place/move"),
    WORKER_SELECTED ("The player has selected the worker to place/move"),
    PLACING_WORKERS ("Each player has to place his workers on the board"),
    WORKER_MOVED ("The player has moved, if he hasn't any extra abilities, now must build"),

    START_GAME("The first player start playing"),
    ACTION_DONE ("The player has done his action");
    ;


    private String description;

    PossibleGameState(String description) {
        this.description = description;
    }
}
