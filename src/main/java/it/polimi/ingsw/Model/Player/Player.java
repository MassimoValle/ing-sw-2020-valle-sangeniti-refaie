package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.God.God;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerName;                  /** Name chosen by the player */
    private God playerGod;                      /** God card chosen by the player */
    private List<Worker> playerWorkers;         /** List of workers assigned to the player */
    private int numWorker = 2;                  /** Number of workers per player */


    /**
     *
     *      Create a player with the name specified by input user
     *      <p></p>
     *      The for loop is used to assign a distinctive number to workers
     *
     * @param playerName the player name
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.playerWorkers = new ArrayList<>();
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker(i + 1)); }   // 2 workers
    }

    /**
     * Gets player god.
     * <p></p>
     * @return the god card chosen by the player among those available
     */
    public God getPlayerGod() {
        return this.playerGod;
    }

    public void setPlayerGod(God selectedGod) {
        this.playerGod = selectedGod;
    }

    /**
     * Gets player name.
     * <p></p>
     * @return the name chosen by the player at game start
     */
    public String getPlayerName() {return playerName; }


    /**
     * Gets player workers.
     * <p></p>
     * This list contains 2 elements as 2 is the number of workers per player.
     * <p></p>
     * @return the list of workers assigned to the player.
     */
    public List<Worker> getPlayerWorkers() { return playerWorkers; }

    /**
     * Add new {@link Worker} to {@link Player this player}
     * <p></p>
     * Probably this method will never be used but just in case just leave it here
     * <p></p>
     * @return the worker
     */
    public Worker addNewWorker() {
        Worker newWorker = new Worker(getPlayerWorkers().size() + 1);
        getPlayerWorkers().add(newWorker);
        return newWorker;
    }

    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase();
    }

    public boolean godAssigned() {
        if (playerGod != null )
            return true;
        else return false;
    }

    public boolean areWorkerPlaced() {
        for (Worker worker : playerWorkers) {
            if (!worker.isPlaced())
                return false;
        }
        return true;
    }
}

