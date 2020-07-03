package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.MoveAction;
import it.polimi.ingsw.server.model.god.godspower.*;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.*;

import java.util.List;

/**
 * This class is used to check if any player has won the game
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

        if (!((MoveAction) lastAction).winningMove())
            return false;

        winner = player;

        return true;

    }




    public boolean playerHasWonAfterBuilding(GameMap gameMap) {

        if( chronusIsPresent() && gameMap.hasAtLeastFiveFullTower() ) {

            for (Player pl: playersInGame) {
                if (pl.getPlayerGod().is("Chronus") && !pl.isEliminated())
                    winner = pl;
            }

            return true;
        } else {
            return false;
        }

    }

    private boolean heraIsPresent() {

        Player HeraPlayer = null;

        for(Power power : powersInGame ) {

            if (power instanceof HeraPower) {

                for (Player player : playersInGame) {

                    if (player.getPlayerGod().is("Hera") && !player.isEliminated())
                        return true;
                }
            }

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
