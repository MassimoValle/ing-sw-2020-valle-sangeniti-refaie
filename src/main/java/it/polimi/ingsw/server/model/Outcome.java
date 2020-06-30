package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.MoveAction;
import it.polimi.ingsw.server.model.god.godspower.*;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.*;

import java.util.List;

/**
 * La classe outcome dovrà avere al suo interno tutte le informazioni necessarie per capire se il player ha vinto o no
 */
public class Outcome {

    private final Player player;
    private final GameMap gameMap;
    private final List<Power> powersInGame;
    private final List<Player> playersInGame;

    private final Action lastAction;

    private Player winner = null;


    public Outcome(Player player, List<Power> powersInGame, GameMap gameMap, List<Player> playersInGame, Action lastAction) {
        this.player = player;
        this.powersInGame = powersInGame;
        this.gameMap = gameMap;
        this.playersInGame = playersInGame;
        this.lastAction = lastAction;
    }



    public boolean playerHasWonAfterMoving(Worker activeWorker) {

        int height = gameMap.getSquareHeight(activeWorker.getPosition());
        Position position = activeWorker.getPosition();


        if(!activeWorker.getColor().equals(player.getColor()))
            return false;

        if (height != 3)
            return false;

        if(heraIsPresent() && position.isPerimetral()){
            return false;
        }

        //TODO da gestire il caso in cui io mi muovo da un livello 3 a un livello 3 e casi particolare (zeus)
        if (!((MoveAction) lastAction).winningMove())
            return false;

        winner = player;

        return true;

    }




    public boolean playerHasWonAfterBuilding(GameMap gameMap) {

        if( chronusIsPresent() && gameMap.hasAtLeastFiveFullTower() ) {

            for (Player pl: playersInGame) {
                if (pl.getPlayerGod().is("Chronus"))
                    winner = pl;
            }

            return true;
        } else {
            return false;
        }

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

    public Player getWinner() {
        return winner;
    }
}
