package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.message.server.ServerMessage;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.ColorEnum;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Worker;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.server.view.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Game extends Observable<Message> {


    private final List<Player> players;
    private final Deck deck;

    private final List<God> chosenGodsFromDeck;
    private final GameMap gameMap;

    private final List<ColorEnum> colorAvailable = new ArrayList<>();
    private final HashMap<Player, ColorEnum> playerColor = new HashMap<>();


    public Game() {
        this.players = new ArrayList<>();
        this.gameMap = new GameMap();
        this.deck = new Deck();

        this.chosenGodsFromDeck = new ArrayList<>();

        initColorAvailable();

    }

    // getter
    public List<Player> getPlayers() {
        return this.players;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public List<God> getChosenGodsFromDeck() {
        return chosenGodsFromDeck;
    }

    /**
     * Gets the Gods' powers from the game.
     *
     * @return the Gods' powers
     */
    public List<Power> getPowersInGame() {
        List<Power> powers = new ArrayList<>();
        for (God god: chosenGodsFromDeck) {
            powers.add(god.getGodPower());
        }
        return powers;
    }

    public int getNumberOfPlayers() { return players.size(); }

    public GameMap getGameMap() {
        return this.gameMap;
    }


    private void initColorAvailable() {
        colorAvailable.add(ColorEnum.RED);
        colorAvailable.add(ColorEnum.BLUE);
        colorAvailable.add(ColorEnum.GREEN);
    }

    /**
     * Add {@link Player} to the {@link Game}
     *
     * @param player the player we want to add
     */
    public void addPlayer(Player player) {
        //Aggiungi il player alla partita
        players.add(player);

        //Aggiorni l'hasmap
        playerColor.put(player, colorAvailable.get(0));
        colorAvailable.remove(0);

        player.setColor(playerColor.get(player));

    }

    /**
     * Search {@link Player player} by name player.
     *
     * @param name is the name of player to search
     * @return the player with that username
     */
    public Player searchPlayerByName(String name) {
        Player result = null;
        for (Player playerInGame : players) {
            if (playerInGame.getPlayerName().equals(name)) {
                result = playerInGame;
                break;
            }
        }
        return result;
    }

    /**
     * It fills the {@link Game#chosenGodsFromDeck} with the {@link ArrayList<God>} passed as parameter
     *
     * @param godsChosen the gods chosen to be part of this game
     */
    public void setChosenGodsFromDeck(ArrayList<God> godsChosen) {
        chosenGodsFromDeck.addAll(godsChosen);
    }


    /**
     * It checks if every {@link Player} has a God
     *
     * @return true if every player has a God
     */
    public boolean godsPickedByEveryone() {
        boolean a = true;
        for (Player player : players) {
            if (!player.godAssigned()) {
                a = false;
            }
        }
        return a;
    }

    public List<God> getUnassignedGods() {
        List<God> unassignedGod = new ArrayList<>();

        for(God god: chosenGodsFromDeck)
            if (!god.isAssigned()) {
                unassignedGod.add(god);
            }

        return unassignedGod;
    }

    public void setGodAssigned(God godAssigned) {
        for(God god: chosenGodsFromDeck) {
            if (god.equals(godAssigned)) {
                god.setAssigned(true);
            }
        }
    }


    /**
     * It checks if every {@link Player} has placed its workers
     *
     * @return the boolean
     */
    public boolean workersPlacedByEveryone() {
        for (Player player : players) {
            for (Worker worker : player.getPlayerWorkers()) {
                if ( !worker.isPlaced() )
                    return false;
            }
        }
        return true;
    }


    /**
     * It makes the message available to all the {@link it.polimi.ingsw.server.view.RemoteView}
     *
     * @param player who the message is intended for
     * @param serverMessage the ServerMessage in output
     */
    public void putInChanges(Player player, ServerMessage serverMessage) {
        serverMessage.setMessageRecipient(player.getPlayerName());
        notify(serverMessage);
    }





    public void printGameInfo() {
        if (getPlayers().size() == 0) {
            System.out.println("The game hasn't started yet");
            return;
        }
        System.out.println("[GAME INFO]\n" +
                "This is a " + getPlayers().size() + " player match.\n" +
                "PLAYERS:\n" + players.toString());
        gameMap.printBoard();
    }














    //  ####    TESTING-ONLY    ####
    /*public static void resetInstance() {
        gameInstance = null;
    }

    public boolean checkGodPresence(String godName) {

        for (God god : getChosenGodsFromDeck())
            if (godName.equals(god.getGodName()))
                return true;

        return false;
    }*/
}


