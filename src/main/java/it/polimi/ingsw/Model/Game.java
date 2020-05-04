package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.View.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//GAME CLASS DOESN'T NEED TO IMPLEMENTS CLONABLE

public class Game extends Observable<Game> implements Cloneable{


    private List<Player> players;
    private Deck deck;

    private List<God> chosenGodsFromDeck;
    private GameMap gameMap;

    private List<Color> colorAvailable = new ArrayList<Color>(){{
        add(Color.RED);
        add(Color.BLUE);
        add(Color.GREEN);
    }};

    private final HashMap<Player, Message> changes = new HashMap<>();
    private HashMap<Player, Color> playerColor = new HashMap<>();



    public Game() {
        this.players = new ArrayList<>();
        this.deck = Deck.getInstance();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.gameMap = new GameMap();
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

    public int getNumberOfPlayers() { return players.size(); }

    public GameMap getGameMap() {
        return this.gameMap;
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
     * It put the {@link Response} to the relative {@link Player} in the {@link Game#changes} HashMap
     *
     * @param player the player to whom the answer is intended
     * @param response the response that must be sent to the player
     */
    public void putInChanges(Player player, Response response) {
        changes.put(player, response);
        notify(this);
    }


    /**
     * It notify the {@link Player} to whom the {@link Message} is intended
     *
     * @param player the player to whom the answer is intended
     * @return the message that must be sent to the player
     */
    public Message notifyPlayer(Player player) {
        Message x = changes.get(player);
        System.out.println("message taken by " + player.getPlayerName());
        return x;
    }





    @Override
    public String toString() {
        return players.toString() +
                gameMap.toString() +
                deck.toString();
    }

    @Override
    public Game clone(){
        Game game1 = new Game();
        game1.players = getPlayers();
        game1.gameMap = getGameMap();
        game1.deck = getDeck();
        return game1;
    }

}


