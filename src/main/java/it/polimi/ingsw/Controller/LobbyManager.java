package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Connection;
import it.polimi.ingsw.View.RemoteView;

import java.util.ArrayList;
import java.util.List;

//CONTROLLER PER GESTIRE LA LOBBY
public class LobbyManager {

    private ArrayList<Player> playersInLobby;

    boolean isLobbyFull;


    public LobbyManager() {
        this.playersInLobby = new ArrayList<>();
    }

    void addPlayer(Player player) {
        if (isLobbyFull) {
            //DO nothing
        }
        //TODO
    }

    void startGame() {
        //TODO start new game
    }

    public ArrayList<Player> getPlayersInLobby() {
        return (ArrayList<Player>) playersInLobby.clone();
    }
}
