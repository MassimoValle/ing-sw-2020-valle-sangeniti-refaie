package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.Deck;
import it.polimi.ingsw.Model.God;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {

    private String playerName;
    private God playerGod;
    private List<Worker> playerWorkers;
    private int numWorker = 2;

    public Player(String playerName) {
        this.playerName = playerName;

        this.playerWorkers = new ArrayList<>();
        for (int i = 0; i < numWorker; i++) { this.playerWorkers.add(new Worker()); }   // 2 workers
    }

    public God getPlayerGod() {
        return this.playerGod;
    }


    public String getPlayerName() {return playerName; }

    public List<Worker> getPlayerWorkers() { return playerWorkers; }


    public void choseGodsFromDeck() {

        //Mostro a video gli dei disponibili per la scelta
        System.out.println(Deck.getInstance().toString());

        //Prendo da tastiera la scelta del Dio
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        //Setto il playerGod preso dalla posizione 'choice' nel Deck
        playerGod = Deck.getInstance().getGod(choice);

    }


    public void assignGodToPlayer(God selectedGod) {
        this.playerGod = selectedGod;
    }


    public void placeWorker(Position position) {

    }


    public void choseWorkerToUse(Worker selectedWorker) {
        Worker currentWorker = this.playerWorkers.get(this.playerWorkers.indexOf(selectedWorker));
        // do something
    }





    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase() +
                "\nGod: " + this.playerGod.getGodName().toUpperCase();
    }

}

