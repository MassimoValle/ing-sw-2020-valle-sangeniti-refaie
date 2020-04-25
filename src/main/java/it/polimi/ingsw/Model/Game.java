package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;


//GAME CLASS DOESN'T NEED TO IMPLEMENTS CLONABLE

public class Game extends Observable<Game> implements Cloneable{

    //GAME MUST BE SINGLETON
    private static Game instance;

    private List<Player> players;
    private Deck deck;


    //Chosen Gods from the FIRST_PLAYER (GODLIKE PLAYER)
    private List<God> chosenGodsFromDeck;
    private int numberOfPlayers;
    private GameMap gameMap;


    private Game() {
        this.players = new ArrayList<>();
        this.deck = Deck.getInstance();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.numberOfPlayers = 0;
        this.gameMap = new GameMap();
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public List<God> getChosenGodsFromDeck() {
        return chosenGodsFromDeck;
    }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public GameMap getGameMap() {
        return this.gameMap;
    }



    /**
     * Add a new {@link Player player} to the current {@link Game game}.
     *
     * @param name the name given to the new Player
     * @return the newly created player
     */
    public Player addPlayer(String name) {
        Player newPlayer = new Player(name);
        players.add(newPlayer);
        numberOfPlayers++;
        return newPlayer;
    }

    /**
     * Search {@link Player player} by name player.
     *
     * @param name
     * @return the player with that username
     * @throws PlayerNotFoundException There's no player with that username
     */
    public Player searchPlayerByName(String name) throws PlayerNotFoundException {
        Player result = null;
        for (Player playerInGame : players) {
            if (playerInGame.getPlayerName().equals(name)) {
                result = playerInGame;
                break;
            }
        }
        if (result == null) {
            throw new PlayerNotFoundException("Giocatore non trovato");
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


    public boolean godsPickedByEveryone() {
        boolean a = true;
        for (Player player : players) {
            if (!player.godAssigned()) {
                a = false;
            }
        }
        return a;
    }


    public boolean workersPlacedByEveryone() {
        for (Player player : players) {
            for (Worker worker : player.getPlayerWorkers()) {
                if ( !worker.isPlaced() )
                    return false;
            }
        }
        return true;
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
        game1.numberOfPlayers = getNumberOfPlayers();
        return game1;
    }

}


