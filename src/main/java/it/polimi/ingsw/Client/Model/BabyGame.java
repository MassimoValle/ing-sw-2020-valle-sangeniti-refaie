package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Client.View.Gui.GUI;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.Server.Model.Player.Player;
import javafx.scene.paint.Color;

import java.util.Set;

public class BabyGame {

    public CLIclientMap clientMap;
    public Set<Player> players;

    private static BabyGame instance = null;

    private BabyGame(){
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

    public void addPlayers(UpdatePlayersMessage serverMessage){

        Player player = new Player(serverMessage.getMessageSender());
        player.setPlayerGod(serverMessage.getGod());
        player.setColor(serverMessage.getColor());

        players.add(player);
    }

    public Player getPlayerByName(String name){

        for (Player player : players)
            if(player.getPlayerName().equals(name))
                return player;

        return null;
    }
}
