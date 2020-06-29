package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.model.Gods.PumpedDeck;
import it.polimi.ingsw.client.model.map.CLIclientMap;
import it.polimi.ingsw.client.model.map.GUImap;
import it.polimi.ingsw.client.view.gui.GuiController;
import it.polimi.ingsw.network.message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.server.model.God.Deck;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.Worker;

import java.util.HashSet;
import java.util.Set;

public class BabyGame {

    private final CLIclientMap clientMap;
    private final Set<Player> players = new HashSet<>();
    private final Deck deck;

    private static BabyGame instance = null;

    public BabyGame(){
        if(ClientManager.clientView instanceof GuiController) {
            clientMap = new GUImap();
            deck = new PumpedDeck(clientMap);
        }
        else{
            clientMap = new CLIclientMap();
            deck = new Deck(clientMap);
        }
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

        if(ClientManager.clientView instanceof GuiController) {
            for (Worker worker : player.getPlayerWorkers()) {
                worker.initGUIObj();
            }
        }

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

    public Deck getDeck() {
        return deck;
    }
}
