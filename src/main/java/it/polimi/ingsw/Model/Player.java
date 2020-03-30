package it.polimi.ingsw.Model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerName;
    private God playerGod;
    private List<Worker> playerWorkers;
    private final int numWorker = 2;

    public Player(String playerName) {
        this.playerName = playerName;

        this.playerWorkers = new ArrayList<>();
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker()); }   // 2 workers
    }


    public String getPlayerName() {return playerName; }

    public List<Worker> getPlayerWorkers() { return playerWorkers; }


    public void choseGodsFromDeck() { };


    public void assignGodToPlayer(God selectedGod) { this.playerGod = selectedGod; }


    public void placeWorker() { };


    public void choseWorkerToUse(Worker selectedWorker) {
        Worker currentWorker = this.playerWorkers.get(this.playerWorkers.indexOf(selectedWorker));

        // do something
    };





    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase() +
                "\nGod: " + this.playerGod.getGodName().toUpperCase();
    }
}

