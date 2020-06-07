package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Client.Model.Map.CLIclientMap;
import it.polimi.ingsw.Client.Model.Map.GUImap;
import it.polimi.ingsw.Client.View.Gui.GUI;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.Server.Model.Player.Player;

import java.util.HashSet;
import java.util.Set;

public class BabyGame {

    private CLIclientMap clientMap;
    public Set<Player> players = new HashSet<>();

    private static BabyGame instance = null;

    public BabyGame(){
        if(ClientManager.clientView instanceof GUI) {
            clientMap = new GUImap();
        }
        else clientMap = new CLIclientMap();
    }

    public static BabyGame getInstance() {
        if(instance==null)
            instance = new BabyGame();
        return instance;
    }

    public void addPlayer(UpdatePlayersMessage.ClientPlayer clientPlayer){

        Player player = new Player(clientPlayer.getName());
        player.setPlayerGod(clientPlayer.getGod());
        player.setColor(clientPlayer.getColor());

        players.add(player);
    }

    public Player getPlayerByName(String name){

        for (Player player : players)
            if(player.getPlayerName().equals(name))
                return player;

        return null;
    }

    public CLIclientMap getClientMap() {
        return clientMap;
    }

    public Set<Player> getPlayers() {
        return players;
    }

}