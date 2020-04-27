package it.polimi.ingsw.Model.Player;

//Enum per gestire le varie fasi del turno, ovvero gli stati possibili in cui un giocatore può trovarsi

public enum UserPlayerState {
    STARTING_TURN, WORKER_SELECTED, WORKER_MOVED, WORKER_BUILT, ENDING_TURN, NOT_YOUR_TURN
}
