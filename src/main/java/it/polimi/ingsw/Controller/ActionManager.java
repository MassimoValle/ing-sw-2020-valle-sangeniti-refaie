package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.*;

public class ActionManager {

    private Game gameInstance;
    private GameManager gameManager;
    private TurnManager turnManager;


    public ActionManager(GameManager gameManager) {
        this.gameInstance = gameManager.getGameInstance();
        this.gameManager = gameManager;
        this.turnManager = gameManager.getTurnManager();

        gameManager.setGameState(PossibleGameState.GODLIKE_PLAYER_MOMENT);
    }

    public Response handleRequest(Message message) {

        PossibleGameState gameState = gameManager.getGameState();

        switch (message.getMessageContent()) {
            case PICK_GOD:
                if (gameState == PossibleGameState.ASSIGNING_GOD) {
                    return handlePickGodRequest((PickGodRequest) message);
                }

            case PLACE_WORKER:
                if ( (gameState == PossibleGameState.WORKER_SELECTED) && !gameInstance.workersPlacedByEveryone()) {
                    return handlePlaceWorkerAction((PlaceWorkerRequest) message);
                }

            case SELECT_WORKER:
                if (gameState == PossibleGameState.FILLING_BOARD || gameState == PossibleGameState.START_GAME ||
                        gameState == PossibleGameState.START_ROUND) {
                    return handleSelectWorkerAction((SelectWorkerRequest) message);
                }


            case MOVE:
                if (gameState == PossibleGameState.WORKER_SELECTED) {
                    return handleMoveAction((MoveRequest) message);
                }

            case BUILD:
                if (gameState == PossibleGameState.WORKER_MOVED) {
                    return handleBuildAction((BuildRequest) message);
                }

            default: return buildNegativeResponse("Must never be reached!");
        }
    }

    Response handleGodsChosen(ChoseGodsRequest request) {
        gameInstance.setChosenGodsFromDeck(request.getChosenGod());
        turnManager.setGodsInGame(request.getChosenGod());

        //gameManager.setGameState(PossibleGameState.ACTION_DONE);

        gameManager.setGameState(PossibleGameState.ASSIGNING_GOD);

        turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);

        return buildPositiveResponse("Gods selected!");
    }

    Response handlePickGodRequest(PickGodRequest request) {
        String requestSender = request.getMessageSender();
        Player activePlayer = turnManager.getActivePlayer();
        God pickedGod = request.getPickedGod();

        if(!isYourTurn(requestSender)) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        gameInstance.setGodToPlayer(activePlayer, pickedGod);

        //gameManager.setGameState(PossibleGameState.ACTION_DONE);


        if (gameInstance.godsPickedByEveryone()) {
            gameManager.setGameState(PossibleGameState.FILLING_BOARD);
            turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);
            return buildPositiveResponse("Gods picked up, now it's time to fill the board!");
        }

        gameManager.setGameState(PossibleGameState.ASSIGNING_GOD);
        turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);
        return buildPositiveResponse("God picked up!");
    }

    Response handleSelectWorkerAction(SelectWorkerRequest request) {

        String requestSender = request.getMessageSender();
        Player activePlayer = turnManager.getActivePlayer();
        Worker workerFromRequest = request.getWorkerToSelect();

        //Controllo che il player sia nel suo turno
        if(!isYourTurn(requestSender)) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        //When a handleSelectWorkerRequest occurs the activeWorker in the turn must be set tu null
        //or has to be the other player's worker
        Worker activeWorker = turnManager.getActiveWorker();

        //You get into this statement only if the activeWorker is own by someone else.
        if (activeWorker != null) {
            if (!(activeWorker == activePlayer.getPlayerWorkers().get(0) || activeWorker == activePlayer.getPlayerWorkers().get(1))) {
                return buildNegativeResponse("There's something wrong with the worker selection");
            }
        }



        Action selectWorkerAction = new SelectWorkerAction(activePlayer, workerFromRequest);

        //
        if (!workerFromRequest.isPlaced()) {
            selectWorkerAction.doAction();
        } else if ( workerFromRequest.isPlaced() ) {

            if ( ! gameInstance.getGameMap().isWorkerStuck(workerFromRequest) ) {
                selectWorkerAction.doAction();
            }
            else {
                return buildNegativeResponse("Worker Stuck!");
            }

        } else {
            return buildNegativeResponse("You cannot select this worker");
        }

        turnManager.setActiveWorker(workerFromRequest);

        //gameManager.setGameState(PossibleGameState.ACTION_DONE);
        gameManager.setGameState(PossibleGameState.WORKER_SELECTED);


        return buildPositiveResponse("Worker Selected");

    }

    Response handlePlaceWorkerAction(PlaceWorkerRequest request) {
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        //Controllo che il worker che il player vuole piazzare sia lo stesso che ha selezionato inizialmente
        if (request.getWorkerToPlace() != activeWorker) {
            return buildNegativeResponse("The worker you want to place isn't the worker selected!");
        }

        //Passo direttamente lo square su cui piazzare il worker dal controller, per ovviare alle getInstance in classi che non c'èentrano nulla
        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(activePlayer, activeWorker, positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {
            placeWorkerAction.doAction();
        } else {
            return buildNegativeResponse("You cannot place the worker here");
        }

        if (activePlayer.areWorkerPlaced()) {
            if ( gameInstance.workersPlacedByEveryone()) {
                gameManager.setGameState(PossibleGameState.START_GAME);
                turnManager.updateTurnState(PossibleGameState.START_GAME);
                return buildPositiveResponse("Everybody have plaed their workers, we are ready to Start!");
            }

            gameManager.setGameState(PossibleGameState.FILLING_BOARD);
            turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);
            return buildPositiveResponse("Worker Placed");
        }

            gameManager.setGameState(PossibleGameState.FILLING_BOARD);
        return buildPositiveResponse("Worker Placed! Let's place the other one!");

    }

    Response handleMoveAction(MoveRequest request) {

        String requestSender = request.getMessageSender();


        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();

        //Controllo che il player sia nel suo turno
        if(!isYourTurn(requestSender)) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);

        Action moveAction = new MoveAction(activePlayer, activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        if (moveAction.isValid()) {
            moveAction.doAction();
        } else {
            return buildNegativeResponse("You cannot move here!");
        }


        gameManager.setGameState(PossibleGameState.WORKER_MOVED);
        turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);

        return buildPositiveResponse("Worker Moved!");
    }

    Response handleBuildAction(BuildRequest request) {

        String requestSender = request.getMessageSender();

        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToBuild = request.getPositionWhereToBuild();

        //Controllo che il player sia nel suo turno
        if(!isYourTurn(requestSender)) { //The player who sent the request isn't the playerActive
            return buildNegativeResponse("It's not your turn!");
        }

        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);

        Action buildAction = new BuildAction(squareWhereToBuild);


        if (buildAction.isValid()) {
            buildAction.doAction();
        } else {
            return buildNegativeResponse("You cannot move here!");
        }

        gameManager.setGameState(PossibleGameState.BUILT);
        turnManager.updateTurnState(PossibleGameState.BUILT);
        gameManager.setGameState(PossibleGameState.START_ROUND);

        return buildPositiveResponse("Worker Moved!");
    }

    Response buildNegativeResponse(String gameManagerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagerSays, MessageStatus.ERROR);

    }

    Response buildPositiveResponse(String gameManagaerSays) {

        String activePlayerUsername = turnManager.getActivePlayer().getPlayerName();
        return new Response(activePlayerUsername, gameManagaerSays , MessageStatus.OK);
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
