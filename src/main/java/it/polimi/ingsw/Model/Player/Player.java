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


    //ATTRIBUTI PROPRI DEL WORKER (PUR VOLENDO ANCHE DEL PLAYER CHE VENGONO CONTROLLATI NEL CONTROLLER
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


    public String getPlayerName() {return playerName; }

    public List<Worker> getPlayerWorkers() { return playerWorkers; }

    public Worker addNewWorker() {
        Worker newWorker = new Worker(getPlayerWorkers().size() + 1);
        getPlayerWorkers().add(newWorker);
        return newWorker;
    }


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

    //TODO metodo che permette al player di decidere quale worker selezionare
    // (con controllo interno nel caso in cui 1 dei worker risulti bloccato)
    public Worker choseWorkerToUse(Worker selectedWorker) {



        return null;
        //Worker currentWorker = this.playerWorkers.get(this.playerWorkers.indexOf(selectedWorker));
    }


    public boolean hasMoved() { return moved; }
    public boolean hasBuilt() { return built; }

    public void moveWorker(){
        // do something
        moved = true;
    }
    public void buildWithWorker(){
        // do something
        built = true;
    }






    @Override
    public String toString() {
        return "Player Name: " + this.playerName.toUpperCase() +
                "\nGod: " + this.playerGod.getGodName().toUpperCase();
    }

}
