package it.polimi.ingsw.client.model;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.model.gods.PumpedDeck;
import it.polimi.ingsw.client.model.map.CLIclientMap;
import it.polimi.ingsw.client.model.map.GUImap;
import it.polimi.ingsw.client.view.gui.GuiController;
import it.polimi.ingsw.network.message.server.updatemessage.UpdatePlayersMessage;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Baby game.
 */
public class BabyGame {

    private final CLIclientMap clientMap;
    private final Set<Player> players = new HashSet<>();
    private final Deck deck;

    private static BabyGame instance = null;

    /**
     * Instantiates a new Baby game.
     *
     */
    public BabyGame(){
        if(ClientManager.getClientView() instanceof GuiController) {
            clientMap = new GUImap();
            deck = new PumpedDeck();
        }
        else{
            clientMap = new CLIclientMap();
            deck = new Deck();
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BabyGame getInstance() {
        if(instance==null)
            instance = new BabyGame();
        return instance;
    }

    /**
     * Add player.
     * Add the player to players list and
     * if clientview is GUI, initialize graphics resource of workers
     *
     * @param clientPlayer the client player
     */
    public void addPlayer(UpdatePlayersMessage.ClientPlayer clientPlayer){

        Player player = new Player(clientPlayer.getName());
        player.setPlayerGod(clientPlayer.getGod());
        player.setColor(clientPlayer.getColor());

        if(ClientManager.getClientView() instanceof GuiController) {
            for (Worker worker : player.getPlayerWorkers()) {
                worker.initGUIObj();
            }
        }

        players.add(player);
    }

    /**
     * Get player by name player.
     *
     * @param name the name
     * @return the player
     */
    public Player getPlayerByName(String name){

        for (Player player : players)
            if(player.getPlayerName().equals(name))
                return player;

        return null;
    }

    /**
     * Gets client map.
     *
     * @return the client map
     */
    public CLIclientMap getClientMap() {
        return clientMap;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public Set<Player> getPlayers() {
        return players;
    }

    /**
     * Gets deck.
     *
     * @return the deck
     */
    public Deck getDeck() {
        return deck;
    }
}
