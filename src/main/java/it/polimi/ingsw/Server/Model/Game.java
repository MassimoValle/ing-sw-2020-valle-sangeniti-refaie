package it.polimi.ingsw.Server.Model;

import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.ColorEnum;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.Server.View.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Game extends Observable<Game> {


    private List<Player> players;
    private Deck deck;

    private List<God> chosenGodsFromDeck;
    private GameMap gameMap;

    private List<ColorEnum> colorAvailable = new ArrayList<>();
    private final HashMap<Player, Message> changes = new HashMap<>();
    private HashMap<Player, ColorEnum> playerColor = new HashMap<>();

    private static Game gameInstance;


    public static Game getInstance() {
        if(gameInstance == null)
            gameInstance = new Game();
        return gameInstance;
    }

    public Game() {
        this.players = new ArrayList<>();
        this.deck = Deck.getInstance();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.gameMap = new GameMap();

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
     * @param name
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
     * It put the {@link ServerResponse} to the relative {@link Player} in the {@link Game#changes} HashMap
     *  @param player the player to whom the answer is intended
     * @param serverMessage the response that must be sent to the player
     */
    public void putInChanges(Player player, Message serverMessage) {
        changes.put(player, serverMessage);

        notify(this);
        changes.remove(player, serverMessage);
    }


    /**
     * It notify the {@link Player} to whom the {@link Message} is intended
     *
     * @param player the player to whom the answer is intended
     * @return the message that must be sent to the player
     */
    public Message notifyPlayer(Player player) {
        Message x = changes.get(player);
        System.out.println("Sent message to: " + player.getPlayerName());

        return x;
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
    public static void resetInstance() {
        gameInstance = null;
    }

}


