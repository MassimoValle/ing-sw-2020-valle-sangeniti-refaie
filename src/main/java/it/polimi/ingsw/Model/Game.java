package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {

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

    public void checkWin() { };






    @Override
    public String toString() {
        return players.toString() +
                gameMap.toString() +
                deck.toString();
    }
}
