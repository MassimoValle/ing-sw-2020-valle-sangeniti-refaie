package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.UserPlayerState;
import it.polimi.ingsw.Model.God.Power;

import java.util.List;


public class TurnManager {

    private Player lastPlayerActive;
    private Player playerActive;
    private List<Player> playersInGame;
    private List<God> godInGame;
    private List<Power> powerActive;
    private UserPlayerState userPlayerState;



    public TurnManager(Player player) {
        initTurnManager();
    }

    private void initTurnManager() {
        //TODO
    }

    public Player getPlayerActive() {
        return playerActive;
    }

    public void choseWorker() {
        //TODO
    }

}