package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;

public class Game extends Observable<Game> implements Cloneable{

    private List<Player> players;
    private Player playerActive;
    private Deck deck;
    private int numberOfPlayers;
    private GameMap gameMap;

    public Game() {
        this.players = new ArrayList<>();
        this.playerActive = null;
        this.deck = assignNewDeck();
        this.numberOfPlayers = 0;
        this.gameMap = assignNewMap();
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
        numberOfPlayersPlusOne();
        return newPlayer;
    }

    private void numberOfPlayersPlusOne() {
        this.numberOfPlayers++;
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


