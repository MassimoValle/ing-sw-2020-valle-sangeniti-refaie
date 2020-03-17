package it.polimi.ingsw.Model;

import java.util.List;

public class Player {

    private String playerName;
    private God playerGod;
    private List<Worker> playerWorkers;

    private boolean moved;
    private boolean built;

    public Player(String playerName, God playerGod) {
        this.playerName = playerName;
        this.playerGod = playerGod;
        this.moved = false;
        this.built = false;
    }


    public boolean hasMoved() { return true; }
    public boolean hasBuilt() { return true; }

    public void choseGod() { };

    public void placeWorker() { };

    public void choseWorkerToUse() { };

    public void assignGod() { };


    @Override
    public String toString() {
        return "Nome Giocatore :" + this.playerName +
                "\nDivinita :" + this.playerGod;
    }
}

