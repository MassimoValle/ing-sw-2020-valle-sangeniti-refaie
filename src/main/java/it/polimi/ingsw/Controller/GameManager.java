package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Server;

import java.util.List;
import java.util.Random;

public class GameManager {

    final int MIN_CONNECTION_IN = 2;
    final int MAX_CONNECTION_IN = 3;

    private static transient Server server;
    private final Game gameInstance;
    private transient TurnManager turnManager;
    private transient ActionManager actionManager;

    Player godLikePlayer;
    private PossibleGameState gameState;

    public GameManager(Server server){
        this.server = server;
        this.gameInstance = Game.getInstance();
        this.turnManager = null;
        this.gameState = PossibleGameState.GAME_INIT;
    }

    public Game getGameInstance() {
        return this.gameInstance;
    }

    public PossibleGameState getGameState() {
        return gameState;
    }

    public TurnManager getTurnManager() {
        return this.turnManager;
    }



    public Response startGame() {

        choseGodLikePlayer();

        this.turnManager = new TurnManager(gameInstance, godLikePlayer);
        this.actionManager = new ActionManager(this);

        //Notifico al player in questione che deve scegliere i god
        return actionManager.buildPositiveResponse("Let's chose which gods you want to be part of the game!");
    }



    //public only for test purposes
    //QUESTO METODO DOVREBBE ESSERE INVOCATO UNA VOLTA SCADUTO IL COUNTDOWN DI INIZIO MATCH;
    //MI METTO IN ATTESA CHE IL GODLIKE PLAYER PRESCELTO MI INVII I GOD DA UTILIZZARE NELLA PARTITA
    public void choseGodLikePlayer() {

        //scegli un player a caso
        Random rand = new Random();
        int n = rand.nextInt(gameInstance.getNumberOfPlayers());
        godLikePlayer = gameInstance.getPlayers().get(n);

    }

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

    //public only for test purposes
    public void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    //Methods below only used for test Purpose
    public void _addPlayerToCurrentGame(String username) {
        gameInstance.addPlayer(username);
    }

    public void _setNewActivePlayer(Player player) {
        turnManager.setActivePlayer(player);
    }

}