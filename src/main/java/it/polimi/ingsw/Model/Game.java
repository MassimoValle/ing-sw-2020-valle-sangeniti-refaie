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



    // ### DA SPOSTARE ###

    public void chooseGodFromDeck(int i) {
        getDeck().getGod(i).setTakenFromDeck(true);
        chosenGodsFromDeck.add(Deck.getInstance().getGod(i));
    }

    /**
     * Assign the {@link God god} chosen by the {@link Player player}
     *
     * @param player player
     * @param god    god
     */
    public void assignGodToPlayer(Player player, God god) {
        god.setAssigned(true);
        player.setPlayerGod(god);
    }

    /**
     * It checks if the {@link Worker worker} has no reachable places or in case he has than if he has adjacent places to build on
     *
     *
     * @return boolean
     */
    public boolean isWorkerStuck(Worker worker) {
        ArrayList<Position> placesWhereToMove;
        placesWhereToMove = getGameMap().getReachableAdjacentPlaces(worker.getWorkerPosition());

        if (placesWhereToMove.size() == 0) return true;


        for (Position position: placesWhereToMove ) {
            ArrayList<Position> placesWhereYouCanBuildOn = getGameMap().getPlacesWhereYouCanBuildOn(position);
            if (placesWhereYouCanBuildOn.size() != 0) return false;
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


