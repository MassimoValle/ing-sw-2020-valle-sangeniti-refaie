package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.controller.Enum.PossibleGameState;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.BuildAction;
import it.polimi.ingsw.server.model.action.MoveAction;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.Player.PossiblePlayerState;
import it.polimi.ingsw.server.model.Player.Worker;

import java.util.ArrayList;
import java.util.List;


/**
 * The Turn manager is used to managing the turn dynamics
 */
public class TurnManager {


    private Player lastActivePlayer;
    private Worker lastActiveWorker;
    private Player activePlayer;
    private Worker activeWorker;
    private final List<Player> inGamePlayers;

    private List<Action> actionPerformedByActivePlayer;


    private PossiblePlayerState playerState;



    public TurnManager(List<Player> players) {

        this.lastActivePlayer = null;
        this.lastActiveWorker = null;
        //this.activePlayer = null;
        this.activePlayer = players.get(1);
        this.activeWorker = null;
        this.inGamePlayers = new ArrayList<>(players);
        this.actionPerformedByActivePlayer = new ArrayList<>();

        updatePlayerState(PossiblePlayerState.CHOSE_GODS);
    }



    // getter


    public List<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    /**
     * Gets active player.
     *
     * @return the active player
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Gets active worker.
     *
     * @return the active worker
     */
    public Worker getActiveWorker() {
        return activeWorker;
    }




    // setter

    /**
     * Sets active player.
     *
     * @param player the player
     */
    public void setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    /**
     * Sets active worker.
     *
     * @param Worker the worker
     */
    public void setActiveWorker(Worker Worker) {
        this.activeWorker = Worker;
    }




    // functions

    /**
     * It updates the current {@link #playerState}
     *
     * @param playerState the playerState
     */
    private void updatePlayerState(PossiblePlayerState playerState) {
        this.playerState = playerState;
    }

    /**
     * Based on what the gameState, then the {@link #playerState} is updated
     * and the turnOwnership given to the next {@link Player}
     *
     * @param gameState the actual game state
     */
    public void updateTurnState(PossibleGameState gameState) {

        switch (gameState) {

            case ASSIGNING_GOD -> {
                updatePlayerState(PossiblePlayerState.PICK_GOD);
                giveTurnOwnership();
            }

            case FILLING_BOARD -> {
                updatePlayerState(PossiblePlayerState.PLACING_WORKERS);
                giveTurnOwnership();
            }

            case START_ROUND -> updatePlayerState(PossiblePlayerState.STARTING_TURN);

            case WORKER_SELECTED -> updatePlayerState(PossiblePlayerState.WORKER_SELECTED);

            case WORKER_MOVED -> updatePlayerState(PossiblePlayerState.WORKER_MOVED);

            case BUILD_BEFORE -> updatePlayerState(PossiblePlayerState.POWER_BUTTON);

            case BUILT -> updatePlayerState(PossiblePlayerState.BUILT);

            case PLAYER_TURN_ENDING -> {
                updatePlayerState(PossiblePlayerState.ENDING_TURN);
                giveTurnOwnership();
            }

            case PLAYER_HAS_LOST -> {
                updatePlayerState(PossiblePlayerState.GAME_OVER);
                giveTurnOwnership();
            }
        }

    }


    /**
     * Simply give the Turn Ownership to the next player
     */
    private void giveTurnOwnership() {

        int playerIndex = inGamePlayers.indexOf( activePlayer);
        Player nextPlayer;

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

    /**
     * Update the turn info
     *
     * @param player the player who is set to became the next turn ownership
     */
    public void nextTurn(Player player) {
        this.lastActivePlayer = activePlayer;
        this.lastActiveWorker = activeWorker;
        this.activePlayer = player;
        this.activeWorker = null;
        this.actionPerformedByActivePlayer = new ArrayList<>();

        //Quando inizia il turno di un nuovo giocatore dovrebbe essere invocato il metodo updateTurnState(START_ROUND)
        //cosicchè lo stato del player venga settato su STARTING_TURN
        this.playerState = PossiblePlayerState.STARTING_TURN;
    }

    /**
     * Check if the player during his turn has moved at least once
     *
     * @return the boolean
     */
    public boolean activePlayerHasMoved() {
        boolean ret = false;
        for (Action a: actionPerformedByActivePlayer) {
            if (a instanceof MoveAction) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    /**
     * Check if the player during his turn has build at least once
     *
     * @return the boolean
     */
    public boolean activePlayerHasBuilt() {
        boolean ret = false;
        for (Action a: actionPerformedByActivePlayer) {
            if (a instanceof BuildAction) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public Action getLastActionPerformed() {
        return actionPerformedByActivePlayer.get(actionPerformedByActivePlayer.size() - 1);
    }

    public void addActionPerformed(Action action) {
        actionPerformedByActivePlayer.add(action);
    }

}