package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//GAME CLASS DOESN'T NEED TO IMPLEMENTS CLONABLE

public class Game extends Observable<Game> implements Cloneable{


    private List<Player> players;
    private Deck deck;

    private List<God> chosenGodsFromDeck;
    private GameMap gameMap;

    private final HashMap<Player, Message> changes = new HashMap<>();


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




    // function
    public void addPlayer(Player player) {
        players.add(player);
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

    public void setChosenGodsFromDeck(ArrayList<God> godsChosen) {
        chosenGodsFromDeck.addAll(godsChosen);
    }


    /**
     * Set the {@link God god} picked up by the {@link Player player}
     *
     * @param player player
     * @param pickedGod    god
     */
    public void setGodToPlayer(Player player, God pickedGod) {
        player.setPlayerGod(pickedGod);
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


    public void putInChanges(Player player, Response response) {

        changes.put(player, response);
        notify(this);
        
    }

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


