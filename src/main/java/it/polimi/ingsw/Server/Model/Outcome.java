package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.God.GodsPower.*;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.*;

import java.util.List;

/**
 * La classe outcome dovrà avere al suo interno tutte le informazioni necessarie per capire se il player ha vinto o no
 */
public class Outcome {

    private final Player player;
    private final GameMap gameMap;
    private final List<Power> powersInGame;


    public Outcome(Player player, List<Power> powersInGame, GameMap gameMap) {
        this.player = player;
        this.powersInGame = powersInGame;
        this.gameMap = gameMap;

        //getInfo(); //Inizializzo l'oggetto con le informazioni necessarie per capire se ha vinto o no
    }



    public boolean playerHasWonAfterMoving(Worker activeWorker) {

        int height = gameMap.getSquareHeight(activeWorker.getWorkerPosition());
        Position position = activeWorker.getWorkerPosition();


        if(!activeWorker.getColor().equals(player.getColor()))
            return false;

        if (height != 3)
            return false;

        if(heraIsPresent() && position.isPerimetral()){
            return false;
        }

        return true;

    }




    public boolean playerHasWonAfterBuilding(GameMap gameMap) {
        return chronusIsPresent() && gameMap.hasAtLeastFiveFullTower();
    }

    private boolean heraIsPresent() {
        for(Power power : powersInGame ) {
            if (power instanceof HeraPower)
                return true;
        }
        return false;
    }

    private boolean chronusIsPresent() {
        for(Power power : powersInGame ) {
            if (power instanceof ChronusPower)
                return true;
        }
        return false;
    }

    //ritorna il giocatore che ha chrono
    public Player takeWinner(List<Player> players) {
        for (Player pl: players) {
            if (pl.getPlayerGod().is("Chronus"))
                return pl;
        }
        return null;
    }
}
