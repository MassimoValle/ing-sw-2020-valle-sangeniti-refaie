package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;

public class Game extends Observable<Game> implements Cloneable{

    private List<Player> players;
    private Player playerActive;
    private Deck deck;

    //Chosen Gods from the FIRST_PLAYER (GODLIKE PLAYER)
    private List<God> chosenGodsFromDeck;
    private boolean godsChosen;
    private int numberOfPlayers;
    private GameMap gameMap;

    private static Game instance=null;
    //private List<God> gods;


    public static Game getInstance() {
        if(instance==null)
            instance = new Game();
        return instance;
    }

    public Game() {
        this.players = new ArrayList<>();
        this.playerActive = null;
        this.deck = assignNewDeck();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.numberOfPlayers = 0;
        this.gameMap = assignNewMap();
        this.godsChosen = false;
    }

    public Game getGame() {
        return this.clone();
    }

    private GameMap assignNewMap() {
        return new GameMap();
    }

    private Deck assignNewDeck() {
        return new Deck();
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public Deck getDeck() {
        return this.deck;
    }


    public Player addPlayer(String name) {
        Player newPlayer = new Player(name);
        players.add(newPlayer);
        numberOfPlayers++;
        return newPlayer;
    }

    public Player searchPlayerByName(String player) throws PlayerNotFoundException {
        Player result = null;
        for (Player playerInGame : getPlayers()) {
            if (playerInGame.getPlayerName().equals(player)) {
                result = playerInGame;
                break;
            }
        }
        if (result == null) {
            throw new PlayerNotFoundException("Giocatore non trovato");
        }
        return result;
    }


    public List<Player> getPlayers() {
        return this.players;
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
        game1.players = players;
        game1.gameMap = gameMap;
        game1.playerActive = playerActive;
        game1.deck = deck;
        game1.numberOfPlayers = numberOfPlayers;
        return game1;
    }


}


