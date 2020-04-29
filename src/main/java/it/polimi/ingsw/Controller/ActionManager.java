package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Requests.*;

/**
 * The ActionManager handles every request sent by a client to perform an action
 */
public class ActionManager {

    private final Game gameInstance;
    private final TurnManager turnManager;

    private PossibleGameState gameState;


    public ActionManager(Game game, TurnManager turnManager) {
        this.gameInstance = game;
        this.turnManager = turnManager;
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
            return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), request.getMessageContent(), "It's not your turn!");
        }

        switch (request.getMessageContent()) {

            case SELECT_WORKER:
                if (gameState == PossibleGameState.FILLING_BOARD || gameState == PossibleGameState.START_GAME ||
                        gameState == PossibleGameState.START_ROUND) {
                    return handleSelectWorkerAction((SelectWorkerRequest) request);
                }


            case MOVE:
                if (gameState == PossibleGameState.WORKER_SELECTED) {
                    return handleMoveAction((MoveRequest) request);
                }

            case BUILD:
                if (gameState == PossibleGameState.WORKER_MOVED) {
                    return handleBuildAction((BuildRequest) request);
                }

            default: return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), request.getMessageContent(),"Must never be reached!");
        }
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

        String requestSender = request.getMessageSender();
        Player activePlayer = turnManager.getActivePlayer();
        Worker workerFromRequest = request.getWorkerToSelect();

        //When a handleSelectWorkerRequest occurs the activeWorker in the turn must be set tu null
        //or has to be the other player's worker
        Worker activeWorker = turnManager.getActiveWorker();

        //You get into this statement only if the activeWorker is own by someone else.
        if (activeWorker != null) {
            if (!(activeWorker == activePlayer.getPlayerWorkers().get(0) || activeWorker == activePlayer.getPlayerWorkers().get(1))) {
                return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "There's something wrong with the worker selection");
            }
        }



        Action selectWorkerAction = new SelectWorkerAction(workerFromRequest);

        //
        if (!workerFromRequest.isPlaced()) {
            selectWorkerAction.doAction();
        } else if ( workerFromRequest.isPlaced() ) {

            if ( ! gameInstance.getGameMap().isWorkerStuck(workerFromRequest) ) {
                selectWorkerAction.doAction();
            }
            else {
                return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "Worker stuck!");

            }

        } else {
            return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "You cannot select this worker!");

        }

        turnManager.setActiveWorker(workerFromRequest);

        //gameManager.setGameState(PossibleGameState.ACTION_DONE);
        gameState = PossibleGameState.WORKER_SELECTED;


        return SuperMegaController.buildPositiveResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "Worker Selected!");


    }

    /**
     * It handle the {@link MoveRequest} by a player that can be sent to Server after the {@link SelectWorkerRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} whe the player have to move the previously selected worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    Response handleMoveAction(MoveRequest request) {

        String requestSender = request.getMessageSender();

        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();

        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);

        Action moveAction = new MoveAction(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        if (moveAction.isValid()) {
            moveAction.doAction();
        } else {
            return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "You cannot move here!");
        }


        gameState = PossibleGameState.WORKER_MOVED;
        turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);

        return SuperMegaController.buildPositiveResponse(turnManager.getActivePlayer(), request.getMessageContent(), "Worker Moved!");
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

        String requestSender = request.getMessageSender();

        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToBuild = request.getPositionWhereToBuild();

        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);

        Action buildAction = new BuildAction(squareWhereToBuild);


        if (buildAction.isValid()) {
            buildAction.doAction();
        } else {
            return SuperMegaController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getClientManagerSays()), request.getMessageContent(), "You cannot build here!");

        }

        gameState = PossibleGameState.BUILT;
        turnManager.updateTurnState(PossibleGameState.BUILT);
        gameState = PossibleGameState.START_ROUND;

        return SuperMegaController.buildPositiveResponse(turnManager.getActivePlayer(), request.getMessageContent(), "Built!");

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


}
