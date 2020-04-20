package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Map.GameMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private String playerName;
    private God playerGod;
    private List<Worker> playerWorkers;
    private int numWorker = 2;

    private boolean moved;
    private boolean built;


    public Player(String playerName) {
        this.playerName = playerName;
        this.playerWorkers = new ArrayList<>();
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker(i + 1)); }   // 2 workers
    }

    public God getPlayerGod() {
        return this.playerGod;
    }

    public void setPlayerGod(God selectedGod) {
        this.playerGod = selectedGod;
    }

    public String getPlayerName() {return playerName; }

    public boolean hasMoved() {
        return moved;
    }

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
     *
     * @return the player workers
     */
    public List<Worker> getPlayerWorkers() { return playerWorkers; }

    /**
     * Add new {@link Worker} to {@link Player this player}
     *
     * @return the worker
     */
    public Worker addNewWorker() {
        Worker newWorker = new Worker(getPlayerWorkers().size() + 1);
        getPlayerWorkers().add(newWorker);
        return newWorker;
    }


    public void assignGodToPlayer(God selectedGod) {
        this.playerGod = selectedGod;
    }







    public void startRound() {
        this.built = false;
        this.moved = false;
    }

    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase();
    }

}

