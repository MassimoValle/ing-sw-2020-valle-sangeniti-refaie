package it.polimi.ingsw.Server.Model.Player;

import it.polimi.ingsw.Server.Model.Action.SelectWorkerAction;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerName;                  /** Name chosen by the player */
    private God playerGod;                      /** God card chosen by the player */
    private List<Worker> playerWorkers;         /** List of workers assigned to the player */
    private int numWorker = 2;                  /** Number of workers per player */
    private ColorEnum color;

    private boolean eliminated = false;

    //private LastTurnAction[] lastTurnActions;


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
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker(this, i)); }   // 2 workers
    }

    /**
     * Gets player god.
     * <p></p>
     * @return the god card chosen by the player among those available
     */
    public God getPlayerGod() {
        return this.playerGod;
    }


    /**
     * Set the {@link God} passed as parameter to {@link Player this}
     *
     * @param selectedGod the selected god
     */
    public void setPlayerGod(God selectedGod) {
        this.playerGod = selectedGod;
    }


    public void setColor(ColorEnum color) {
        this.color = color;
        this.getPlayerWorkers().get(0).setColor(color);
        this.getPlayerWorkers().get(1).setColor(color);
    }

    public ColorEnum getColor() {
        return color;
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
        Worker newWorker = new Worker(this, getPlayerWorkers().size() + 1);
        getPlayerWorkers().add(newWorker);
        return newWorker;
    }


    //public String toString() { return "Player Name: " + this.playerName.toUpperCase(); }

    public boolean godAssigned() {
        return playerGod != null;
    }

    public boolean areWorkersPlaced() {
        for (Worker worker : playerWorkers) {
            if (!worker.isPlaced())
                return false;
        }
        return true;
    }

    /**
     * This method checks if the player cannot select any worker due to them being stuck
     *
     * @return true if all workers are stuck, false otherwise
     */
    public boolean allWorkersStuck() {
        Power power = this.getPlayerGod().getGodPower();
        int workersStuck = 0;

        for (Worker worker : playerWorkers) {
            SelectWorkerAction selectWorker = new SelectWorkerAction(power, worker, this);
            if (!selectWorker.isValid(null))
                workersStuck++;
        }

        return workersStuck == playerWorkers.size();
    }

    @Override
    public String toString() {

        if (playerGod == null) {
            return "\n" + playerName + "\n" +
                    " " +
                    playerWorkers.toString() + "\n";
        }

        return "\n" + playerName + "\n" +
                "God: " + playerGod.getGodName() +
                " " +
                playerWorkers.toString() + "\n";
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Player)) {
            return false;
        }

        Player player = (Player) obj;

        return playerName.equals(player.playerName) &&
                playerGod == player.playerGod &&
                playerWorkers == player.playerWorkers &&
                color == player.color;
    }

    public String printInfoInCLi() {

        return "Username:" + playerName + "\n" +
                "God: " + playerGod.getGodName() + "\n" ;

    }


    public void removeWorkers() {
        for (Worker worker : playerWorkers) {
            worker.remove();
        }
    }

    public boolean isEliminated() {
        return this.eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }
}

