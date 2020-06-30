package it.polimi.ingsw.server.model.player;

//Enum per gestire le varie fasi del turno, ovvero gli stati possibili in cui un giocatore può trovarsi

public enum PossiblePlayerState {
    CHOSE_GODS, 
    PICK_GOD, 
    PLACING_WORKERS, 
    STARTING_TURN, 
    WORKER_SELECTED,
    POWER_BUTTON,
    WORKER_MOVED,
    BUILT, 
    ENDING_TURN,
    GAME_OVER,
}
