package it.polimi.ingsw.Server.Controller.Enum;

public enum PossibleGameState {

    GAME_INIT ("Game manager has been just created"),
    GODLIKE_PLAYER_MOMENT ("One player have to chose which gods are going to take part of the game"),
    ASSIGNING_GOD ("Every player has to chose 1 god from the ones selected by the Godlike player"),
    FILLING_BOARD ("Every player has to select & place its workers on the board"),
    START_ROUND("The turn has just started"),
    WORKER_SELECTED ("The player has selected the worker to place/move"),
    BUILD_BEFORE ("The player has the ability to build before moving"),
    WORKER_MOVED ("The player has moved, if he hasn't any extra abilities, now must build"),
    BUILT ("The player has built"),
    PLAYER_TURN_ENDING ("End of player turn")
    ;


    private String description;

    PossibleGameState(String description) {
        this.description = description;
    }
}
