package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
import it.polimi.ingsw.Model.Map.GameMap;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.View.Observable;

import java.util.ArrayList;
import java.util.List;

public class Game extends Observable<Game> implements Cloneable{

    private List<Player> players;
    private Player playerActive;
    private Deck deck;
    private Player lastPlayerActive;

    private Worker workerActive;

    //Chosen Gods from the FIRST_PLAYER (GODLIKE PLAYER)
    private List<God> chosenGodsFromDeck;
    private boolean godsChosen;
    private int numberOfPlayers;
    private GameMap gameMap;




    public List<God> getChosenGodsFromDeck() {
        return chosenGodsFromDeck;
    }

    public boolean areGodsChosen() {
        return godsChosen;
    }



    public Game() {
        this.players = new ArrayList<>();
        this.playerActive = null;
        this.deck = assignNewDeck();
        this.chosenGodsFromDeck = new ArrayList<>();
        this.numberOfPlayers = 0;
        this.gameMap = assignNewMap();
        this.godsChosen = false;
        this.workerActive = null;
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
    
    public void setGodsChosen(boolean godsChosen) {
        this.godsChosen = godsChosen;
    }

    public void setPlayerActive(Player playerActive) {
        this.playerActive = playerActive;
    }

    public void setLastPlayerActive(Player lastPlayerActive) {
        this.lastPlayerActive = lastPlayerActive;
    }

    /**
     * Pick god from {@link Deck#getInstance()} and set the selected {@link God#takenFromDeck} true;
     *
     * @param i it references to the index that is shown to the player referred to the god
     */
    public void pickGodFromDeck(int i) {
        getDeck().getGod(i).setTakenFromDeck(true);
        chosenGodsFromDeck.add(Deck.getInstance().getGod(i));
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
        for (Player playerInGame : getPlayers()) {
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

    private void numberOfPlayersPlusOne() {
        this.numberOfPlayers = this.getNumberOfPlayers() + 1;
    }



    /**
     * Start round.
     * Set to false {@link Player#moved } and {@link Player#built}
     *
     * @param player the player
     */
    public void initPlayerState(Player player) {
        player.startRound();
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

    /**
     * Build any kind of block (lvl1, lvl2, lv3 or a dome) with the selected {@link Worker worker}
     * by {@link Player player} after he has moved in {@link Position position} chose by the player
     *
     *
     * @param player   the worker's owner
     * @param worker   worker selected by the player
     * @param position position where we want to build
     */
    public void buildBlock(Player player, Worker worker, Position position) {
        //ALWAYS CHECK THE PLAYER AND THE WORKER'S PROPERTY

        //Controllo che il worker con il quale si vuole costruire sia lo stesso che si è mosso precedentemente
        if (this.workerActive != worker) {
            System.out.println("Devi costruire con lo stesso worker con il quale hai mosso!");
            return;
        }
        getGameMap().addBlock(position);

        //Worker has built so update its state
        player.setBuilt(true);
    }

    /**
     * Move the selected {@link Worker worker}  by {@link Player player} into the {@link Position position} chose by the player;
     * Also set the worker passed as parameter to {@link Game#workerActive}
     *
     * @param player   the worker's owner
     * @param worker   worker selected by the player
     * @param position position where we want to move
     */
    public void moveWorker(Player player, Worker worker, Position position) {
        getGameMap().setWorkerPosition(worker, position);
        this.workerActive = worker;
        player.setMoved(true);
    }



    /**
     * Place the {@link Worker workerSelected} in the {@link Position position}
     * IT IS CALLED ONLY IN THE SETUP PHASE OF THE GAME
     *
     * @param player         the player that owns the worker
     * @param workerSelected the worker selected by the player
     * @param position       the position chosen by the placer where to place the Worker
     */
    public void placeWorker(Player player, Worker workerSelected, Position position) {
        //TODO: verificare se il worker appartiene a quel giocatore
        getGameMap().setWorkerPosition(workerSelected, position);
        workerSelected.setPlaced(true);
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


