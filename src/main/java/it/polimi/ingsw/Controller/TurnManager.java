package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.PossiblePlayerState;
import it.polimi.ingsw.Model.God.Power;
import it.polimi.ingsw.Model.Player.Worker;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class TurnManager {

    private Player lastActivePlayer;
    private Worker lastActiveWorker;
    private Player activePlayer;
    private Worker activeWorker;

    private List<Player> inGamePlayers;
    private List<God> inGameGods;
    private List<Power> inGamePowers;

    private PossiblePlayerState playerState;


    public TurnManager(Game game, Player godlikePlayer) {
        this.lastActivePlayer = null;
        this.lastActiveWorker = null;
        this.activePlayer = godlikePlayer;
        this.activeWorker = null;
        this.inGamePlayers = game.getPlayers();

        setPlayerState(PossiblePlayerState.CHOSE_GODS);


        //When the TurnManager is instantiated this info is not available yet.
        //this.godInGame = game.getChosenGodsFromDeck();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Worker getActiveWorker() {
        return activeWorker;
    }

    public void setGodsInGame(ArrayList<God> chosenGod) {
        this.inGameGods = chosenGod;
    }

    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public void setActiveWorker(Worker Worker) {
        this.activeWorker = Worker;
    }

    private void setPlayerState(PossiblePlayerState playerState) {
        this.playerState = playerState;
    }


    public void updateTurnState() {

        if (playerState == PossiblePlayerState.CHOSE_GODS) {
            giveTurnOwnership();
        }

    }

    public void updateTurnState(PossibleGameState gameState) {

        switch (gameState) {

            case ASSIGNING_GOD:
                setPlayerState(PossiblePlayerState.PICK_GOD);
                giveTurnOwnership();
                break;

            case FILLING_BOARD:
                setPlayerState(PossiblePlayerState.PLACING_WORKERS);
                giveTurnOwnership();
                break;


            case START_GAME: //TUTTI I WORKER SONO PIAZZATI INIZIA LA PARTITA VERA E PROPRIA
                setPlayerState(PossiblePlayerState.STARTING_TURN);
                giveTurnOwnership();
                break;

            case WORKER_SELECTED:
                setPlayerState(PossiblePlayerState.WORKER_SELECTED);
                break;

            case WORKER_MOVED:
                setPlayerState(PossiblePlayerState.WORKER_MOVED);
                break;

            case BUILT:
                setPlayerState(PossiblePlayerState.BUILT);
                giveTurnOwnership();
        }

    }

    //PASSO IL TURNO AL GIOCATORE SUCCESSIVO
    private void giveTurnOwnership() {

        int playerIndex = inGamePlayers.indexOf((Player) activePlayer);
        Player nextPlayer = null;

        switch (inGamePlayers.size()) {
            case 2:
                if (playerIndex == 0) {
                    nextPlayer = inGamePlayers.get(playerIndex + 1);
                    nextTurn(nextPlayer);
                } else if (playerIndex == 1) {
                    nextPlayer = inGamePlayers.get(0);
                    nextTurn(nextPlayer);
                }
                break;

            case 3:
                if (playerIndex == 0) {
                    nextPlayer = inGamePlayers.get(playerIndex + 1);
                    nextTurn(nextPlayer);
                } else if (playerIndex == 1) {
                    nextPlayer = inGamePlayers.get(playerIndex + 1);
                    nextTurn(nextPlayer);
                } else if (playerIndex == 2) {
                    nextPlayer = inGamePlayers.get(0);
                    nextTurn(nextPlayer);
                }
           }

    }

    //Passo al prossimo turno
    public void nextTurn(Player player) {
        this.lastActivePlayer = activePlayer;
        this.lastActiveWorker = activeWorker;

        this.activePlayer = player;
        this.activeWorker = null;
    }

}