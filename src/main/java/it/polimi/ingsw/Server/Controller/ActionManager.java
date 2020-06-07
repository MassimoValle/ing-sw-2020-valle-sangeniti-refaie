package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.StartTurnServerRequest;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Model.Action.*;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.God.GodsPower.AtlasPower;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Outcome;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import org.jetbrains.annotations.NotNull;

/**
 * The ActionManager handles every request sent by a client to perform an action
 */
public class ActionManager {

    private final Game gameInstance;
    private final TurnManager turnManager;

    private PossibleGameState gameState;

    private ActionOutcome actionOutcome;

    private Player requestSender;
    //this is setted when during someone else's turn one player wins
    private Player winner;

    public ActionManager(Game game, TurnManager turnManager) {
        this.gameInstance = game;
        this.turnManager = turnManager;

        this.gameState = PossibleGameState.START_ROUND;
    }



    /**
     * It handles the the request .
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    public void handleRequest(Request request){

        requestSender = gameInstance.searchPlayerByName(request.getMessageSender());

        if(!isYourTurn(request.getMessageSender())) {
            MasterController.buildNegativeResponse(requestSender, ResponseContent.NOT_YOUR_TURN, "It's not your turn!");
            return;
        }

        switch (request.getRequestContent()) {
            case SELECT_WORKER -> handleSelectWorkerAction((SelectWorkerRequest) request);

            case POWER_BUTTON -> handlePowerButton((PowerButtonRequest) request);

            case MOVE -> handleMoveAction((MoveRequest) request);
            case END_MOVE -> handleEndMoveAction((EndMoveRequest) request);

            case BUILD -> handleBuildAction((BuildRequest) request);
            case END_BUILD -> handleEndBuildAction((EndBuildRequest) request);

            case END_TURN -> handleEndTurnAction((EndTurnRequest) request);
            default -> MasterController.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "Must never be reached!");
        };
    }


    /**
     * If one {@link Player} has a {@link God} that let him use some 'special' {@link Power}
     * than this method handles the {@link PowerButtonRequest}
     *
     * @param request - the request sent by the client
     */
     private void handlePowerButton(PowerButtonRequest request) {
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();

        //controllo che il giocatore abbia un worker attivo
        if (turnManager.getActiveWorker() == null) {
            MasterController.buildNegativeResponse(activePlayer, ResponseContent.POWER_BUTTON, "Please, select a worker first");
            return;
        }

        Power godPower = activePlayer.getPlayerGod().getGodPower();


        if (godPower.canBuildBeforeMoving(activeWorker)) {
            godPower.setBuildBefore();
            gameState = PossibleGameState.BUILD_BEFORE;
            turnManager.updateTurnState(PossibleGameState.BUILD_BEFORE);

            MasterController.buildPositiveResponse(activePlayer, ResponseContent.POWER_BUTTON, "You can build first!");
            MasterController.buildServerRequest(activePlayer, ServerRequestContent.BUILD,activeWorker);
            return;
        }

        if(godPower.canBuildDomeAtAnyLevel()) {

            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            MasterController.buildPositiveResponse(activePlayer, ResponseContent.POWER_BUTTON, "You can build a dome (even onto a non-level-3 block!)");
            MasterController.buildServerRequest(activePlayer, ServerRequestContent.BUILD, activeWorker);
            return;
        }


        MasterController.buildNegativeResponse(activePlayer, ResponseContent.POWER_BUTTON, "You are not allowed!");
    }

    /**
     * It handle the {@link SelectWorkerRequest} by a player
     * or in {@link PossibleGameState#FILLING_BOARD} when the players have to place their workers
     * or in {@link PossibleGameState#START_ROUND} when the player has to select one Worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    void handleSelectWorkerAction(SelectWorkerRequest request) {

        RequestContent requestContent = RequestContent.SELECT_WORKER;
        Player activePlayer = turnManager.getActivePlayer();

        if (wrongRequest(requestContent)) {
            MasterController.buildNegativeResponse(activePlayer, ResponseContent.SELECT_WORKER, "You cannot select a worker!");
            return;
        }


        Integer workerIndex = request.getWorkerToSelect();
        Worker workerFromRequest = activePlayer.getPlayerWorkers().get(workerIndex);
        Power power = activePlayer.getPlayerGod().getGodPower();

        actionOutcome = power.selectWorker(workerFromRequest, requestSender);

        if (actionOutcome == ActionOutcome.NOT_DONE) {
            MasterController.buildNegativeResponse(requestSender, ResponseContent.SELECT_WORKER, "You cannot select this worker!");
            return;
        }




        turnManager.setActiveWorker(workerFromRequest);
        gameState = PossibleGameState.WORKER_SELECTED;
        turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
        MasterController.buildPositiveResponse(activePlayer, ResponseContent.SELECT_WORKER, "Worker Selected!");
        MasterController.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, turnManager.getActiveWorker());
    }


    /**
     * It handle the {@link MoveRequest} by a player that can be sent to Server after the {@link SelectWorkerRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} whe the player have to move the previously selected worker
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    void handleMoveAction(MoveRequest request) {

        ResponseContent responseContent = ResponseContent.MOVE_WORKER;
        Player activePlayer = turnManager.getActivePlayer();

        if (wrongRequest(RequestContent.MOVE)) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You cannot move!");
            return;
        }

        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();
        God playerGod = activePlayer.getPlayerGod();
        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);

        actionOutcome = playerGod.getGodPower().move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);


        //action hasn't been done
        if (actionOutcome == ActionOutcome.NOT_DONE) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You cannot move here!");
            return;
        }


        //L'azione è stata eseguita quindi l'aggiungo alla lista nel turn manager
        turnManager.addActionPerformed(new MoveAction(playerGod.getGodPower(), activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove));

        MasterController.updateClients(activePlayer.getPlayerName(), UpdateType.MOVE, positionWhereToMove, activeWorker.getWorkersNumber(), false);

        //controllo che il giocatore con la mossa appena eseguita abbia vinto
        if (actionOutcome == ActionOutcome.WINNING_MOVE) {
            MasterController.buildWonResponse(activePlayer, "YOU WON!");
            //bisogna comunicare a tutti gli altri giocatori che l'activePlayer ha vinto
            return;
        }

        //vado a contrllare se con questa mossa l'activePlayer ha vinto (sono salito da un livello 2 a un livello 3)
        if (playerHasWonAfterMoving(activePlayer)) {
            MasterController.buildWonResponse(activePlayer,"YOU WON!");
            return;
        }

        //action can not be performed again
        if (actionOutcome == ActionOutcome.DONE) {
            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            MasterController.buildPositiveResponse(activePlayer, responseContent, "Worker Moved!");
            MasterController.buildServerRequest(activePlayer, ServerRequestContent.BUILD, activeWorker);
        } else {    //action  can be performed again
            gameState = PossibleGameState.WORKER_SELECTED;
            turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);

            MasterController.buildPositiveResponse(activePlayer, ResponseContent.MOVE_WORKER_AGAIN, "Worker Moved! Worker has an extra move! You can choose to move again or to build!");
            MasterController.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, activeWorker);
        }

    }


    private void handleEndMoveAction(EndMoveRequest request) {
        ResponseContent responseContent = ResponseContent.END_MOVE;
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();

        if (wrongRequest(RequestContent.END_MOVE)) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You must move at least once!");
            return;
        }

        //Sei sei arrivato qua vuol dire che hai sei in WORKER_MOVED, adesso controllo che hai costruito almeno una volta
        if ( !turnManager.activePlayerHasMoved() ) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You must move at least once!");
            return;
        }


        gameState = PossibleGameState.WORKER_MOVED;
        turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
        MasterController.buildPositiveResponse(activePlayer, responseContent, "Now you can build");
        MasterController.buildServerRequest(activePlayer, ServerRequestContent.BUILD, activeWorker);
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
    void handleBuildAction(BuildRequest request) {
        ResponseContent responseContent = ResponseContent.BUILD;
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();

        if (wrongRequest(RequestContent.BUILD)){
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You cannot build!");
            return;
        }

        God playerGod = activePlayer.getPlayerGod();
        Position positionWhereToBuild = request.getPositionWhereToBuild();
        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);
        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getWorkerPosition());

        if (request instanceof BuildDomeRequest && requestSender.getPlayerGod().is("Atlas"))
            actionOutcome = playerGod.getGodPower().buildDome(squareWhereTheWorkerIs, squareWhereToBuild);
        else
            actionOutcome = playerGod.getGodPower().build(squareWhereTheWorkerIs, squareWhereToBuild);


        if (actionOutcome == ActionOutcome.NOT_DONE) {
            //action hasn't been done
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You cannot build here!");
            return;
        }

        //L'azione è stata eseguita
        if(request instanceof BuildDomeRequest)
            turnManager.addActionPerformed(new BuildDomeAction(squareWhereTheWorkerIs, squareWhereToBuild));
        else
            turnManager.addActionPerformed(new BuildAction(squareWhereTheWorkerIs, squareWhereToBuild));

        MasterController.updateClients(activePlayer.getPlayerName(), UpdateType.BUILD, positionWhereToBuild, activeWorker.getWorkersNumber(), request instanceof BuildDomeRequest);

        //vado a contrllare se con questa mossa un qualsiasi giocatore con qualche potere particolare ha vinto
        if (someoneHasWonAfterBuilding(activePlayer)) {
            MasterController.buildWonResponse(winner,"YOU WON!");
            //Da inviare anche a tutti gli altri giocatori che il winner ha vinto
            return;
        }

        if (actionOutcome == ActionOutcome.DONE) {

            switch (gameState) {

                case BUILD_BEFORE -> {
                    gameState = PossibleGameState.WORKER_SELECTED;
                    turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
                    MasterController.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "Now you can move!");
                    MasterController.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, activeWorker);
                }

                default -> {
                    //action can not be performed again
                    gameState = PossibleGameState.PLAYER_TURN_ENDING;
                    turnManager.updateTurnState(PossibleGameState.BUILT);
                    MasterController.buildPositiveResponse(activePlayer, responseContent, "Built!");
                    MasterController.buildServerRequest(activePlayer, ServerRequestContent.END_TURN, null);}
            }

        } else {
            //action  can be performed again
            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            MasterController.buildPositiveResponse(turnManager.getActivePlayer(), ResponseContent.BUILD_AGAIN, "Built! Worker has an extra built!");
            MasterController.buildServerRequest(activePlayer, ServerRequestContent.BUILD, activeWorker);
        }
    }



    private void handleEndBuildAction(EndBuildRequest request) {
        ResponseContent responseContent = ResponseContent.END_BUILD;
        Player activePlayer = turnManager.getActivePlayer();

        if (wrongRequest(RequestContent.END_BUILD)) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You must build at least once!");
            return;
        }


        gameState = PossibleGameState.BUILT;
        turnManager.updateTurnState(PossibleGameState.BUILT);
        gameState = PossibleGameState.PLAYER_TURN_ENDING;
        MasterController.buildPositiveResponse(activePlayer, responseContent, "Devi passare il turno!");
        MasterController.buildServerRequest(activePlayer, ServerRequestContent.END_TURN, null);
    }

    private void handleEndTurnAction(EndTurnRequest request) {

        ResponseContent responseContent = ResponseContent.END_TURN;
        Player activePlayer = turnManager.getActivePlayer();

        if (wrongRequest(RequestContent.END_TURN)) {
            MasterController.buildNegativeResponse(activePlayer, responseContent, "You cannot end your turn!");
            return;
        }

        Power power = activePlayer.getPlayerGod().getGodPower();

        if (power.powerMustBeReset())
            power.resetPower();


        //aggiorno lo stato del controller e notifico al player che
        gameState = PossibleGameState.PLAYER_TURN_ENDING;
        MasterController.buildPositiveResponse(activePlayer, responseContent, "Turn ending");

        turnManager.updateTurnState(gameState);
        activePlayer = turnManager.getActivePlayer();

        gameState = PossibleGameState.START_ROUND;

        StartTurnServerRequest startTurnServerRequest = new StartTurnServerRequest();
        gameInstance.putInChanges(activePlayer, startTurnServerRequest);
        MasterController.buildServerRequest(activePlayer, ServerRequestContent.SELECT_WORKER, null);

    }

    /**
     * It checks if the {@link Player#getPlayerName()} is the turn owner
     *
     * @param username the username of the player we want to know if is the turn owner
     *
     * @return true if he is the turn owner, false otherwise
     */
    private boolean isYourTurn(String username) {
        return turnManager.getActivePlayer().getPlayerName().equals(username);
    }


    /**
     * It checks if the {@link Player#getPlayerName()} has won after performing a {@link MoveAction}
     *
     * @param player the player to check if he won
     *
     * @return true if he has won, false otherwise
     */
    private boolean playerHasWonAfterMoving(Player player) {
        Outcome outcome = new Outcome(player, gameInstance.getPowersInGame(), gameInstance.getGameMap(), gameInstance.getPlayers());
        if( outcome.playerHasWonAfterMoving(turnManager.getActiveWorker()) ) {
            winner = outcome.getWinner();
            return true;
        } else
            return false;
    }

    /**
     * It checks if some {@link Player} has won after performing a {@link MoveAction}
     *
     * @param player the player to check if he won
     *
     * @return true if he has won, false otherwise
     */
    private boolean someoneHasWonAfterBuilding(Player player) {
        Outcome outcome = new Outcome(player, gameInstance.getPowersInGame(), gameInstance.getGameMap(), gameInstance.getPlayers());
        if (outcome.playerHasWonAfterBuilding(gameInstance.getGameMap())) {
            winner = outcome.getWinner();
            return true;
        } else
            return false;
    }


    /**
     * It checks if the request is sent at the right moment
     *
     * @param content
     * @return
     */
    private boolean wrongRequest(RequestContent content) {

        switch (content) {

            case SELECT_WORKER -> {
                return gameState != PossibleGameState.START_ROUND && gameState != PossibleGameState.WORKER_SELECTED;
            }

            case MOVE -> {
                return gameState != PossibleGameState.WORKER_SELECTED;
            }

            case END_MOVE -> {
                return gameState != PossibleGameState.WORKER_SELECTED && gameState != PossibleGameState.WORKER_MOVED;
            }

            case BUILD -> {
                return gameState != PossibleGameState.WORKER_MOVED && gameState != PossibleGameState.BUILD_BEFORE;
            }

            case END_BUILD -> {
                return gameState != PossibleGameState.WORKER_MOVED || !turnManager.activePlayerHasBuilt();
            }

            case END_TURN -> {
                return !(turnManager.activePlayerHasMoved() && turnManager.activePlayerHasBuilt());
            }

            default -> {
                return false;
            }

        }
    }


    public TurnManager getTurnManager() {
        return this.turnManager;
    }
    //  ####    TESTING-ONLY    ####

    public void _setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    public ActionOutcome _getActionOutcome() {
        return actionOutcome;
    }

    public Player getWinner(){ return this.winner;}
}
