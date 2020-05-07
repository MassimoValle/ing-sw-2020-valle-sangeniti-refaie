package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Outcome;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Requests.*;
import it.polimi.ingsw.Network.Message.Responses.Response;

/**
 * The ActionManager handles every request sent by a client to perform an action
 */
public class ActionManager {

    private final Game gameInstance;
    private final TurnManager turnManager;

    private PossibleGameState gameState;

    private ActionOutcome actionOutcome;


    public ActionManager(Game game, TurnManager turnManager) {
        this.gameInstance = game;
        this.turnManager = turnManager;
        this.gameState = MasterController.gameState;
    }



    /**
     * It handles the the request .
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    public Response handleRequest(Request request){

        //Controllo che il player sia nel suo turno
        if(!isYourTurn(request.getMessageSender())) { //The player who sent the request isn't the playerActive
            return MasterController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "It's not your turn!");
        }

        return switch (request.getRequestContent()) {
            case SELECT_WORKER -> handleSelectWorkerAction((SelectWorkerRequest) request);

            case MOVE -> handleMoveAction((MoveRequest) request);
            case END_MOVE -> handleEndMoveAction((EndMoveRequest) request);

            case BUILD -> handleBuildAction((BuildRequest) request);
            case END_BUILD -> handleEndBuildAction((EndBuildRequest) request);

            case END_TURN -> handleEndTurnAction((EndTurnRequest) request);
            default -> MasterController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "Must never be reached!");
        };
    }

    /**
     * It handle the {@link SelectWorkerRequest} by a player
     * or in {@link PossibleGameState#FILLING_BOARD} when the players have to place their workers
     * or in {@link PossibleGameState#START_ROUND} when the player has to select one Worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    Response handleSelectWorkerAction(SelectWorkerRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        if ( gameState != PossibleGameState.START_ROUND && gameState != PossibleGameState.WORKER_SELECTED) {
                return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot select a worker!");
            }


        Worker workerFromRequest = request.getWorkerToSelect();

        //When a handleSelectWorkerRequest occurs the activeWorker in the turn must be set tu null
        //or has to be the other player's worker
        Worker activeWorker = turnManager.getActiveWorker();

        //You get into this statement only if the activeWorker is own by someone else.
        if (activeWorker != null && !(activeWorker == activePlayer.getPlayerWorkers().get(0) || activeWorker == activePlayer.getPlayerWorkers().get(1))) {
                return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "There's something wrong with the worker selection");
            }

        Action selectWorkerAction = new SelectWorkerAction(workerFromRequest);

        if (!workerFromRequest.isPlaced()) {
            //worker is not placed yet
            selectWorkerAction.doAction();
        } else if ( workerFromRequest.isPlaced() ) {
            if ( ! gameInstance.getGameMap().isWorkerStuck(workerFromRequest) ) {
                //worker isn't stuck
                selectWorkerAction.doAction();
            }
            else {
                //worker is stuck
                return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Worker stuck!");
            }
        } else {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot select this worker!");
        }

        turnManager.setActiveWorker(workerFromRequest);
        gameState = PossibleGameState.WORKER_SELECTED;
        turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
        return MasterController.buildPositiveResponse(activePlayer, ResponseContent.MOVE_WORKER, "Worker Selected!");
    }

    /**
     * It handle the {@link MoveRequest} by a player that can be sent to Server after the {@link SelectWorkerRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} whe the player have to move the previously selected worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    Response handleMoveAction(MoveRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        if (gameState != PossibleGameState.WORKER_SELECTED) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot move!");
        }

        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();
        God playerGod = activePlayer.getPlayerGod();

        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);


        //ESEGUO LA MOSSA E METTO L'ESITO IN moveOutcome
        actionOutcome = playerGod.getGodPower().move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        //action hasn't been done
        if (actionOutcome == ActionOutcome.NOT_DONE) {
            return MasterController.buildNegativeResponse(activePlayer, request.getMessageContent(), "You cannot move here!");
        }


        //L'azione è stata eseguita quindi l'aggiungo alla lista nel turn manager
        turnManager.addActionPerformed(new MoveAction(playerGod.getGodPower(), activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove));

        //vado a contrllare se con questa mossa l'activePlayer ha vinto (sono salito da un livello 2 a un livello 3)
        if (playerHasWon(activePlayer)) {
            return MasterController.buildWonResponse(activePlayer,"Hai vinto!");
        }

        //action can not be performed again
        if (actionOutcome == ActionOutcome.DONE) {
            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            return MasterController.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "Worker Moved!");
        } else {    //action  can be performed again
            //add the action done to the List inside the turn manager
            gameState = PossibleGameState.WORKER_SELECTED;
            turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
            return MasterController.buildPositiveResponse(activePlayer, ResponseContent.MOVE_WORKER, "Worker Moved! Worker has an extra move! You can choose to move again or to build!");
        }

    }


    private Response handleEndMoveAction(EndMoveRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        if (gameState != PossibleGameState.WORKER_SELECTED && gameState != PossibleGameState.WORKER_MOVED) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You must move at least once!");
        }

        //Sei sei arrivato qua vuol dire che hai sei in WORKER_MOVED, adesso controllo che hai costruito almeno una volta
        if ( !turnManager.activePlayerHasMoved() ) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You have to move at least once");
        }


        gameState = PossibleGameState.WORKER_MOVED;
        turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
        return MasterController.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "Ora puoi costruire");
    }

    /**
     * It handle the {@link BuildRequest} by a player that can be sent to Server after the {@link MoveRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} whe the player have to move the previously selected worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested,
     *
     *  in case of SOME GOD'S POWERS it can be called also when the {@link PossibleGameState#WORKER_SELECTED}
     */
    Response handleBuildAction(BuildRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        if (gameState != PossibleGameState.WORKER_MOVED) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot build!");
        }

        God playerGod = activePlayer.getPlayerGod();
        Position positionWhereToBuild = request.getPositionWhereToBuild();
        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);

        //actionInfo[0] value used to see if the action requested has been done
        //actionInfo[1] value used to see if the action can be performed again
        boolean[] actionInfo;

        actionOutcome = playerGod.getGodPower().build(squareWhereToBuild);


        if (actionOutcome == ActionOutcome.NOT_DONE) {
            //action hasn't been done
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot build here!");
        }

        //L'azione è stata eseguita
        turnManager.addActionPerformed(new BuildAction(squareWhereToBuild));

        if (actionOutcome == ActionOutcome.DONE) {
            //action can not be performed again
            gameState = PossibleGameState.BUILT;
            turnManager.updateTurnState(PossibleGameState.BUILT);
            return MasterController.buildPositiveResponse(turnManager.getActivePlayer(), ResponseContent.END_TURN, "Built!");
        } else {
            //action  can be performed again
            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            return MasterController.buildPositiveResponse(turnManager.getActivePlayer(), ResponseContent.BUILD, "Built! Worker has an extra built!");
        }
    }



    private Response handleEndBuildAction(EndBuildRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        if (gameState != PossibleGameState.WORKER_MOVED) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You must build at least once!");
        }

        //Sei sei arrivato qua vuol dire che hai sei in WORKER_MOVED, adesso controllo che hai costruito almeno una volta
        if ( !turnManager.activePlayerHasBuilt() ) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You have to build at least once");
        }

        gameState = PossibleGameState.BUILT;
        turnManager.updateTurnState(PossibleGameState.BUILT);
        gameState = PossibleGameState.PLAYER_TURN_ENDING;
        return MasterController.buildPositiveResponse(turnManager.getActivePlayer(), ResponseContent.END_TURN, "Devi passare il turno!");
    }

    private Response handleEndTurnAction(EndTurnRequest request) {
        Player activePlayer = turnManager.getActivePlayer();

        //TODO this code must be revisited
        if (gameState == PossibleGameState.WORKER_MOVED && !turnManager.activePlayerHasBuilt()) {
            return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot end your turn, you must build first");
        }

        if (gameState != PossibleGameState.BUILT) {
            if (gameState != PossibleGameState.PLAYER_TURN_ENDING) {
                if (gameState != PossibleGameState.WORKER_MOVED) {
                    return MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "You cannot end your turn");
                }
            }
        }

        if (!turnManager.activePlayerHasBuilt()) {
            //QUESTO BLOCCO NON DOVREBBE MAI ESSERE RAGGIUNGIBILE PERCHÈ SE IL GAME STATE È BUILD ALLORAVUOL DIRE CHE HO COSTRUITO
            return MasterController.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "You must build at least once");
        }

        //aggiorno lo stato del controller e notifico al player che
        gameState = PossibleGameState.PLAYER_TURN_ENDING;
        MasterController.buildPositiveResponse(activePlayer, ResponseContent.END_TURN, "Turn ending");
        turnManager.updateTurnState(gameState);
        activePlayer = turnManager.getActivePlayer();
        gameState = PossibleGameState.START_ROUND;
        return MasterController.buildPositiveResponse(activePlayer, ResponseContent.START_TURN, "Turn ending");

    }

    /**
     * It checks if the {@link Player#getPlayerName()} is the turn owner
     *
     * @param username the username of the player we want to know if is the turn owner
     *
     * @return true if he is the turn owner, false otherwise
     */
    private boolean isYourTurn(String username) {
        if ( !turnManager.getActivePlayer().getPlayerName().equals(username) )
            return false;
        else
            return true;
    }


    /**
     * It checks if the {@link Player#getPlayerName()} has won
     *
     * @param player the player to check if he won
     *
     * @return true if he has won, false otherwise
     */
    private boolean playerHasWon(Player player) {
        Outcome outcome = new Outcome(player);
        return outcome.playerHasWon(turnManager.getActiveWorker());
    }

    //  ####    TESTING-ONLY    ####

    public void setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }
}
