package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Map.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private String playerName;                  /** Name chosen by the player */
    private God playerGod;                      /** God card chosen by the player */
    private List<Worker> playerWorkers;         /** List of workers assigned to the player */
    private int numWorker = 2;                  /** Number of workers per player */

    private boolean moved;
    private boolean built;


    /**
     * Create a player with the name specified by input user
     * <p></p>
     * The for loop is used to assign a distinctive number to workers
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.playerWorkers = new ArrayList<>();
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker(i + 1)); }   // 2 workers
    }

    /**
     * Gets player name.
     * @return the name chosen by the player at game start
     */
    public String getPlayerName() {return playerName; }
    /**
     * Gets player god.
     * @return the god card chosen by the player among those available
     */
    public God getPlayerGod() {
        return this.playerGod;
    }
    public void setPlayerGod(God selectedGod) {
        this.playerGod = selectedGod;
    }

    /**
     * If the player perform a move on his turn the method will return true
     */
    public boolean hasMoved() {
        return moved;
    }

    /**
     * If the player has built on his turn the method will return true
     */
    public boolean hasBuilt() {
        return built;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
    public void setBuilt(boolean built) {
        this.built = built;
    }

    /**
     * Gets player workers.
     * @return list of workers assigned to the player.
     * this list contains 2 elements as 2 is the number of workers per player.
     */
    public List<Worker> getPlayerWorkers() { return playerWorkers; }

    /**
     * Add new {@link Worker} to {@link Player this player}
     *
     * @return the worker
     */
    public Worker addNewWorker() {      //TODO a che serve? il gioco senza espansioni non aggiunge worker
        Worker newWorker = new Worker(getPlayerWorkers().size() + 1);
        getPlayerWorkers().add(newWorker);
        return newWorker;
    }






    /**
     *  At the beginning of each turn this method must be called to reset the moved and built values.
     */
    public void startRound() {
        this.moved = false;
        this.built = false;
    }

    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase();
    }

}

