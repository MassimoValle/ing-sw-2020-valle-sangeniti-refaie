package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.TurnManager;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Worker;

/**
 * La classe outcome dovrà avere al suo interno tutte le informazioni necessarie per capire se il player ha vinto o no
 */
public class Outcome {

    Player player;

    GameMap gameMap;


    public Outcome(Player player) {
        this.player = player;
        getInfo(); //Inizializzo l'oggetto con le informazioni necessarie per capire se ha vinto o no
    }

    private void getInfo() {
        this.gameMap = Game.getInstance().getGameMap();
    }


    public boolean playerHasWon(Worker activeWorker) {

        if(!activeWorker.getColor().equals(player.getColor())) return false;

        int height = gameMap.getSquareHeight(activeWorker.getWorkerPosition());

        return height == 3;

    }
}
