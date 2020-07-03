package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.message.Enum.RequestContent;
import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.network.message.Enum.UpdateType;
import it.polimi.ingsw.network.message.server.serverrequests.StartTurnServerRequest;
import it.polimi.ingsw.network.message.server.serverresponse.LostServerResponse;
import it.polimi.ingsw.network.message.server.serverresponse.WonServerResponse;
import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.god.godspower.Power;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.Outcome;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.clientrequests.*;

/**
 * The ActionManager handles every request sent by a client to perform an action, and it's basically the main controller during the game.
 * It is used also to keep track of round evolution;
 */
public class ActionManager {

    private final Game gameInstance;
    private final TurnManager turnManager;
    private final OutgoingMessageManager outgoingMessageManager;
    private PossibleGameState gameState;
    private ActionOutcome actionOutcome;                            //contains the last action outcome performed by the client
    private Player activePlayer;                                    //turnOwner
    private Request currentRequest;                                 //currentRequest by the turnOwner
    private Player winner;                                          //this is set when during someone else's turn one player wins

    public ActionManager(Game game, TurnManager turnManager, OutgoingMessageManager messageManager) {
        this.gameInstance = game;
        this.turnManager = turnManager;
        this.outgoingMessageManager = messageManager;
        this.gameState = PossibleGameState.START_ROUND;
    }



    /**
     * It handles the the request .
     *
     * @param request the request sent by the client
     */
    public void handleRequest(Request request){

        Player requestSender = gameInstance.searchPlayerByName(request.getMessageSender());

        if(!isYourTurn(request.getMessageSender())) {
            outgoingMessageManager.buildNegativeResponse(requestSender, ResponseContent.NOT_YOUR_TURN, "It's not your turn!");
            return;
        }

        currentRequest = request;

        switch (request.getRequestContent()) {
            case SELECT_WORKER -> handleSelectWorkerAction((SelectWorkerRequest) request);

            case POWER_BUTTON -> handlePowerButton();

            case MOVE -> handleMoveAction((MoveRequest) request);
            case END_MOVE -> handleEndMoveAction();

            case BUILD -> handleBuildAction((BuildRequest) request);
            case END_BUILD -> handleEndBuildAction();

            case END_TURN -> handleEndTurnAction();
            default -> outgoingMessageManager.buildNegativeResponse(gameInstance.searchPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "Must never be reached!");
        }
    }


    /**
     * If one {@link Player} has a {@link God} that let him use some 'special' {@link Power}
     * than this method handles the {@link PowerButtonRequest}
     *
     */
     private void handlePowerButton() {
        Worker activeWorker = turnManager.getActiveWorker();

        //controllo che il giocatore abbia un worker attivo
        if (turnManager.getActiveWorker() == null) {
           outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.POWER_BUTTON, "Please, select a worker first");
            return;
        }

        Power godPower = activePlayer.getPlayerGod().getGodPower();


        if (godPower.canBuildBeforeMoving(activeWorker)) {
            godPower.setBuildBefore();
            gameState = PossibleGameState.BUILD_BEFORE;
            turnManager.updateTurnState(PossibleGameState.BUILD_BEFORE);

           outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.POWER_BUTTON, "You can build first!");
           outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.BUILD,activeWorker);
            return;
        }

        if(godPower.canBuildDomeAtAnyLevel()) {

            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
           outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.POWER_BUTTON, "You can build a dome (even onto a non-level-3 block!)");
           outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.BUILD, activeWorker);
            return;
        }


       outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.POWER_BUTTON, "You are not allowed!");
    }

    /**
     * It handle the {@link SelectWorkerRequest} by a player
     * or in {@link PossibleGameState#FILLING_BOARD} when the players have to place their workers
     * or in {@link PossibleGameState#START_ROUND} when the player has to select one Worker
     *
     * @param request the request sent by the client
     */
    private void handleSelectWorkerAction(SelectWorkerRequest request) {

        RequestContent requestContent = RequestContent.SELECT_WORKER;

        if (wrongRequest(requestContent)) {
           outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.SELECT_WORKER, "You cannot select a worker!");
            return;
        }

        Integer workerIndex = request.getWorkerToSelect();
        Worker workerFromRequest = activePlayer.getPlayerWorkers().get(workerIndex);
        Power power = activePlayer.getPlayerGod().getGodPower();

        actionOutcome = power.selectWorker(workerFromRequest, activePlayer);

        if (actionOutcome == ActionOutcome.NOT_DONE) {
            outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.SELECT_WORKER, "You cannot select this worker!");
            return;
        }

        turnManager.setActiveWorker(workerFromRequest);

        gameState = PossibleGameState.WORKER_SELECTED;
        nextPhase();
    }



    /**
     * It handle the {@link MoveRequest} by a player that can be sent to Server after the {@link SelectWorkerRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} whe the player have to move the previously selected worker
     *
     * @param request the request sent by the client
     */
    private void handleMoveAction(MoveRequest request) {
        ResponseContent responseContent = ResponseContent.MOVE_WORKER;

        if (wrongRequest(RequestContent.MOVE)) {
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You cannot move!");
            return;
        }

        Worker activeWorker = turnManager.getActiveWorker();
        Position positionWhereToMove = request.getSenderMovePosition();
        God playerGod = activePlayer.getPlayerGod();
        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getPosition());
        Square squareWhereToMove = gameInstance.getGameMap().getSquare(positionWhereToMove);

        actionOutcome = playerGod.getGodPower().move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

        //action hasn't been done
        if (actionOutcome == ActionOutcome.NOT_DONE) {
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You cannot move here!");
            return;
        }

        //L'azione è stata eseguita quindi l'aggiungo alla lista nel turn manager e informo i client
        turnManager.addActionPerformed(new MoveAction(playerGod.getGodPower(), activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove));
        outgoingMessageManager.updateClients(activePlayer.getPlayerName(), UpdateType.MOVE, positionWhereToMove, activeWorker.getNumber(), false);

        if (winningMove()) {
            gameEndingPhase();
            return;
        }

        gameState = PossibleGameState.WORKER_MOVED;
        nextPhase();
    }



    private void handleEndMoveAction() {

        if (wrongRequest(RequestContent.END_MOVE)) {
            outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.END_MOVE, "You must move at least once!");
            return;
        }

        gameState = PossibleGameState.WORKER_MOVED;
        nextPhase();
    }

    /**
     * It handle the {@link BuildRequest} by a player that can be sent to Server after the {@link MoveRequest}
     * It's called only in {@link PossibleGameState#WORKER_SELECTED} wheN the player has to move the previously selected worker
     *
     * @param request the request sent by the client
     *
     *  in case of SOME GOD'S POWERS it can be called also when the {@link PossibleGameState#WORKER_SELECTED}
     */
    private void handleBuildAction(BuildRequest request) {
        ResponseContent responseContent = ResponseContent.BUILD;
        Worker activeWorker = turnManager.getActiveWorker();

        if (wrongRequest(RequestContent.BUILD)){
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You cannot build!");
            return;
        }

        God playerGod = activePlayer.getPlayerGod();
        Position positionWhereToBuild = request.getPositionWhereToBuild();
        Square squareWhereToBuild = gameInstance.getGameMap().getSquare(positionWhereToBuild);
        Square squareWhereTheWorkerIs = gameInstance.getGameMap().getSquare(activeWorker.getPosition());

        if (request instanceof BuildDomeRequest && activePlayer.getPlayerGod().is("Atlas"))
            actionOutcome = playerGod.getGodPower().buildDome(squareWhereTheWorkerIs, squareWhereToBuild);
        else
            actionOutcome = playerGod.getGodPower().build(squareWhereTheWorkerIs, squareWhereToBuild);

        if (actionOutcome == ActionOutcome.NOT_DONE) {
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You cannot build here!");
            return;
        }

        //L'azione è stata eseguita
        if(request instanceof BuildDomeRequest)
            turnManager.addActionPerformed(new BuildDomeAction(squareWhereTheWorkerIs, squareWhereToBuild));
        else
            turnManager.addActionPerformed(new BuildAction(squareWhereTheWorkerIs, squareWhereToBuild));

       outgoingMessageManager.updateClients(activePlayer.getPlayerName(), UpdateType.BUILD, positionWhereToBuild, activeWorker.getNumber(), request instanceof BuildDomeRequest);

        //vado a contrllare se con questa mossa un qualsiasi giocatore con qualche potere particolare ha vinto
        if (someoneHasWonAfterBuilding(activePlayer)) {
           outgoingMessageManager.buildWonResponse(winner,"YOU WON!");
            return;
        }

        if (gameState != PossibleGameState.BUILD_BEFORE)
            gameState = PossibleGameState.BUILT;

        nextPhase();
    }

    /**
     * It handle the {@link EndBuildRequest} by a player
     * It's called only in {@link PossibleGameState#BUILT} when the player may build again or go to the next phase
     *
     */
    private void handleEndBuildAction() {

        if (wrongRequest(RequestContent.END_BUILD)) {
           outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.END_BUILD, "You must build at least once!");
            return;
        }

        gameState = PossibleGameState.BUILT;
        nextPhase();
    }


    /**
     * It handle the {@link EndTurnRequest} by a player
     * It's called only in {@link PossibleGameState#PLAYER_TURN_ENDING} when the player has no other option than passing the turn
     *
     */
    private void handleEndTurnAction() {

        ResponseContent responseContent = ResponseContent.END_TURN;

        if (wrongRequest(RequestContent.END_TURN)) {
           outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You cannot end your turn!");
            return;
        }

        Power power = activePlayer.getPlayerGod().getGodPower();

        if (power.powerMustBeReset())
            power.resetPower();

        gameState = PossibleGameState.PLAYER_TURN_ENDING;
       outgoingMessageManager.buildPositiveResponse(activePlayer, responseContent, "Turn ending");

        nextPhase();
    }


    /**
     * It handle the case whether a player lose during the game
     *
     * @param nextPlayer the player who is unable to complete a full round, so gets eliminated
     */
    private void playerHasLost(Player nextPlayer) {

        gameInstance.getGameMap().removePlayerWorkers(nextPlayer);
        nextPlayer.removeWorkers();

        //devo rimuovere il giocatore dal turn manager
        turnManager.updateTurnState(gameState);
        turnManager.getInGamePlayers().remove(nextPlayer);
        nextPlayer.setEliminated(true);

    }

    /**
     * It checks if the {@link Player#getPlayerName()} is the turn owner
     *
     * @param username the username of the player we want to know if is the turn owner
     *
     * @return true if he is the turn owner, false otherwise
     */
    private boolean isYourTurn(String username) {
        return activePlayer.getPlayerName().equals(username);
    }


    /**
     * It checks if the {@link Player#getPlayerName()} has won after performing a {@link MoveAction}
     *
     * @param player the player to check if he won
     *
     * @return true if he has won, false otherwise
     */
    private boolean playerHasWonAfterMoving(Player player) {
        Outcome outcome = new Outcome(player, gameInstance.getPowersInGame(), gameInstance.getGameMap(), gameInstance.getPlayers(), turnManager.getLastActionPerformed());
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
        Outcome outcome = new Outcome(player, gameInstance.getPowersInGame(), gameInstance.getGameMap(), gameInstance.getPlayers(), turnManager.getLastActionPerformed());
        if (outcome.playerHasWonAfterBuilding(gameInstance.getGameMap())) {
            winner = outcome.getWinner();
            return true;
        } else
            return false;
    }


    /**
     * It checks if the request is sent at the right moment
     *
     * @param content the request content sent by the client
     * @return true if the request cannot be accepted, false otherwise
     */
    private boolean wrongRequest(RequestContent content) {

        switch (content) {

            case SELECT_WORKER -> {
                return gameState != PossibleGameState.START_ROUND && gameState != PossibleGameState.WORKER_SELECTED;
            }

            case MOVE -> {
                return gameState != PossibleGameState.WORKER_SELECTED ;
            }

            case END_MOVE -> {
                return gameState != PossibleGameState.WORKER_MOVED && (gameState != PossibleGameState.WORKER_SELECTED || !turnManager.activePlayerHasMoved());
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

    /**
     * It updates the server
     */
    private void gameEndingPhase() {
        gameState = PossibleGameState.GAME_OVER;

    }

    /**
     * it checks if the last action performed by any player is a match-winner
     *
     * @return true if match-winner, false otherwise
     */
    private boolean winningMove() {

        if (actionOutcome == ActionOutcome.WINNING_MOVE || playerHasWonAfterMoving(activePlayer)) {
           outgoingMessageManager.buildWonResponse(activePlayer, "YOU WON!");
            return true;
        }

        return false;
    }

    /**
     * It makes the server to evolve based on the current gameState
     */
    public void nextPhase() {

        switch (gameState) {
            case WORKER_SELECTED -> workerSelected();

            case WORKER_MOVED -> workerMoved();

            case BUILT, BUILD_BEFORE -> built();

            case PLAYER_TURN_ENDING -> startNextRound(false);

            default -> {
                // will never be executed
            }
        }
    }

    /**
     * It updates the server
     */
    private void workerSelected() {
        turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
        outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.SELECT_WORKER, "Worker Selected!");
        outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, turnManager.getActiveWorker());
    }

    /**
     * It updates the server
     */
    private void workerMoved() {

        if (actionOutcome == ActionOutcome.DONE || currentRequest instanceof EndMoveRequest) {
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);

            if (currentRequest instanceof EndMoveRequest)
                outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.END_MOVE, "Now you can build");
            else
                outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.MOVE_WORKER, "Worker Moved!");

            outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.BUILD, turnManager.getActiveWorker());

        } else {
            gameState = PossibleGameState.WORKER_SELECTED;
            turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
            outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.MOVE_WORKER_AGAIN, "Worker Moved! Worker has an extra move! You can choose to move again or to build!");
            outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, turnManager.getActiveWorker());
        }
    }

    /**
     * It updates the server
     */
    private void built() {

        if (actionOutcome == ActionOutcome.DONE && gameState == PossibleGameState.BUILD_BEFORE ) {
            gameState = PossibleGameState.WORKER_SELECTED;
            turnManager.updateTurnState(PossibleGameState.WORKER_SELECTED);
            outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "Now you can move!");
            outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.MOVE_WORKER, turnManager.getActiveWorker());

        } else if (actionOutcome == ActionOutcome.DONE || currentRequest instanceof EndBuildRequest) {

            gameState = PossibleGameState.PLAYER_TURN_ENDING;
            turnManager.updateTurnState(PossibleGameState.BUILT);

            if (currentRequest instanceof EndBuildRequest)
                outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.END_BUILD, "You've got to end your turn");
            else
                outgoingMessageManager.buildPositiveResponse(activePlayer, ResponseContent.BUILD, "Built!");

            outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.END_TURN, null);

        } else {
            gameState = PossibleGameState.WORKER_MOVED;
            turnManager.updateTurnState(PossibleGameState.WORKER_MOVED);
            outgoingMessageManager.buildPositiveResponse(turnManager.getActivePlayer(), ResponseContent.BUILD_AGAIN, "Built! Worker has an extra built!");
            outgoingMessageManager.buildServerRequest(activePlayer, ServerRequestContent.BUILD, turnManager.getActiveWorker());
        }

    }

    /**
     * Start next round.
     *
     * @param firstRound first round
     */
    protected void startNextRound(boolean firstRound) {

        if (firstRound) { //scelta implementativa, il giocatore ad iniziare sarà sempre il successivo al godlikePlayer (cioè il player #0)
            activePlayer = gameInstance.getPlayers().get(1);
        } else if (gameState == PossibleGameState.PLAYER_HAS_LOST) {
            activePlayer = turnManager.getActivePlayer();
        } else {
            turnManager.updateTurnState(gameState);
            activePlayer = turnManager.getActivePlayer();
        }


        Player nextPlayer = activePlayer;


        gameState = PossibleGameState.START_ROUND;
        turnManager.updateTurnState(gameState);
        StartTurnServerRequest startTurnServerRequest = new StartTurnServerRequest();
        gameInstance.putInChanges(nextPlayer, startTurnServerRequest);

        //Se era una partita a due giocatori e uno si ritrova con i worker bloccati
        if (turnManager.getInGamePlayers().size() == 1) {
            WonServerResponse wonServerResponse = new WonServerResponse("You won!");
            gameInstance.putInChanges(nextPlayer, wonServerResponse);
            gameEndingPhase();
            return;
        }

        if (nextPlayer.allWorkersStuck()) {
            gameState = PossibleGameState.PLAYER_HAS_LOST;
            LostServerResponse lostServerResponse = new LostServerResponse("You lost!");
            gameInstance.putInChanges(nextPlayer, lostServerResponse);
            playerHasLost(nextPlayer);
            startNextRound(false);
            return;
        }

        outgoingMessageManager.buildServerRequest(nextPlayer, ServerRequestContent.SELECT_WORKER, null);
    }


    public TurnManager getTurnManager() {
        return this.turnManager;
    }
    //  ####    TESTING-ONLY    ####

    public void _setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    public void _setActivePlayer(Player player) {
        this.activePlayer = player;
    }

    public ActionOutcome _getActionOutcome() {
        return actionOutcome;
    }

    public PossibleGameState _getGameState() {
        return gameState;
    }

    public Player getWinner(){ return this.winner;}
}
