package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.UserPlayerState;
import it.polimi.ingsw.Model.God.Power;
import it.polimi.ingsw.Model.Player.Worker;

import java.util.ArrayList;
import java.util.List;


public class TurnManager {

    private Player lastActivePlayer;
    private Worker lastActiveWorker;
    private Player activePlayer;
    private Worker activeWorker;

    private List<Player> inGamePlayers;
    private List<God> inGameGods;
    private List<Power> inGamePowers;

    private UserPlayerState userPlayerState;





    public TurnManager(Game game, Player godlikePlayer) {
        this.lastActivePlayer = null;
        this.lastActiveWorker = null;
        this.activePlayer = godlikePlayer;
        this.activeWorker = null;
        this.inGamePlayers = game.getPlayers();


        //When the TurnManager is instantiated this info is not available yet.
        //this.godInGame = game.getChosenGodsFromDeck();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Worker getActiveWorker() {
        return activeWorker;
    }

    public void setGodsInGame(ArrayList<God> chosenGod) {
        this.inGameGods = chosenGod;
    }

    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public void setActiveWorker(Worker Worker) {
        this.activeWorker = Worker;
    }


    //Passo al prossimo turno
    public void nextTurn(Player player) {
        this.lastActivePlayer = activePlayer;
        this.lastActiveWorker = activeWorker;

        this.activePlayer = player;
        this.activeWorker = null;
    }

}