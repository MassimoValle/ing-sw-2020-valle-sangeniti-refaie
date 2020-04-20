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
    private int numberOfPlayers;
    private GameMap gameMap;

    public Game() {
        this.players = new ArrayList<>();
        this.playerActive = null;
        this.deck = assignNewDeck();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.numberOfPlayers = 0;
        this.gameMap = assignNewMap();
    }

    public Game getGame() {
        return this.clone();
    }

    public GameMap getGameMap() {
        return this.gameMap;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayerActive() { return playerActive; }

    public int getNumberOfPlayers() { return numberOfPlayers; }



    private GameMap assignNewMap() {
        return new GameMap();
    }

    private Deck assignNewDeck() {
        return new Deck();
    }

    public Player addPlayer(String name) {
        Player newPlayer = new Player(name);
        players.add(newPlayer);
        numberOfPlayersPlusOne();
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

    private void numberOfPlayersPlusOne() {
        this.numberOfPlayers = this.getNumberOfPlayers() + 1;
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
        game1.playerActive = getPlayerActive();
        game1.deck = getDeck();
        game1.numberOfPlayers = getNumberOfPlayers();
        return game1;
    }
}


