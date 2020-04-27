package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.God.Deck;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Response;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//GAME CLASS DOESN'T NEED TO IMPLEMENTS CLONABLE

public class Game extends Observable<Game> implements Cloneable{


    private List<Player> players;
    private Deck deck;


    //Chosen Gods from the FIRST_PLAYER (GODLIKE PLAYER)
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

    public void setChosenGodsFromDeck(Player activePlayer, ArrayList<God> godsChosen) {
        chosenGodsFromDeck.addAll(godsChosen);

        changes.put(activePlayer, buildPositiveResponse(activePlayer, MessageContent.GODS_CHOSE, "Gods selezionati"));
        notify(this);
    }




    // add Message in changes
    public Response buildNegativeResponse(Player player, MessageContent messageContent, String gameManagerSays) {

        String activePlayerUsername = player.getPlayerName();
        return new Response(activePlayerUsername, messageContent, MessageStatus.ERROR, gameManagerSays);

    }

    public Response buildPositiveResponse(Player player, MessageContent messageContent, String gameManagerSays) {

        String activePlayerUsername = player.getPlayerName();
        Response x = new Response(activePlayerUsername, messageContent, MessageStatus.OK, gameManagerSays);

        changes.put(player, x);

        notify(this);

        return x;
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


