package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Server;

import java.util.List;
import java.util.Random;

/**
 * THE MAIN CONTROLLER FOR THE GAME
 * EVERYTHING THE CLIENTS WANT TO DO MUST PASS THROUGH THIS CLASS
 */
public class GameManager {


    private static transient Server server;
    private final Game gameInstance;
    private transient TurnManager turnManager;
    private transient ActionManager actionManager;


    Player godLikePlayer;                               //Is the player randomly chosen by the Game Manager to select he gods
    private PossibleGameState gameState;                //Fundamental to track the game state

    /**
     * Instantiates a new Game manager.
     *
     * @param server the server
     */
    public GameManager(Server server){
        this.server = server;
        this.gameInstance = Game.getInstance();
        this.turnManager = null;
        this.gameState = PossibleGameState.GAME_INIT;
    }

    /**
     * Gets game instance.
     *
     * @return the game instance
     */
    public Game getGameInstance() {
        return this.gameInstance;
    }

    /**
     * Gets game state.
     *
     * @return the game state
     */
    public PossibleGameState getGameState() {
        return gameState;
    }

    /**
     * Gets turn manager.
     *
     * @return the turn manager
     */
    public TurnManager getTurnManager() {
        return this.turnManager;
    }


    /**
     * When the everything has already been set up, the game starts!
     *
     * @return notify that the game is started
     */
    public Response startGame() {

        choseGodLikePlayer();

        this.turnManager = new TurnManager(gameInstance, godLikePlayer);
        this.actionManager = new ActionManager(this);

        //Notifico al player in questione che deve scegliere i god
        return actionManager.buildPositiveResponse("Let's chose which gods you want to be part of the game!");
    }


    /**
     * It chose a random  {@link Player } from the players in game
     */
    //public only for test purposes
    public void choseGodLikePlayer() {

        //scegli un player a caso
        Random rand = new Random();
        int n = rand.nextInt(gameInstance.getNumberOfPlayers());
        godLikePlayer = gameInstance.getPlayers().get(n);

    }

    /**
     * It handle the {@link Request} sent by the Client whenever it wants to perform an action and call the {@link ActionManager}
     *
     * @param message the request sent by the client
     * @return positive/negative response if it can/cannot perform the {@link it.polimi.ingsw.Model.Action.Action} requested
     */
    public Response handleMessage(Message message) {

        //SOLO LA PRIMA VOLTA
        if (gameState == PossibleGameState.GODLIKE_PLAYER_MOMENT) {
            return actionManager.handleGodsChosen((ChoseGodsRequest) message);
        }

        if (message instanceof Request) {
            return actionManager.handleRequest(message);
        }

        return actionManager.buildNegativeResponse("Non si dovrebbe mai arrivare qui");
    }

    /**
     * It updates the {@link #gameState}
     *
     * @param gameState the game state
     */
    //public only for test purposes
    public void updateGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }



    //Methods below only used for test Purpose

    /**
     * Add player to current game.
     *
     * @param username the username
     */
    public void _addPlayerToCurrentGame(String username) {
        gameInstance.addPlayer(username);
    }

    /**
     * Set new active player.
     *
     * @param player the player
     */
    public void _setNewActivePlayer(Player player) {
        turnManager.setActivePlayer(player);
    }

}