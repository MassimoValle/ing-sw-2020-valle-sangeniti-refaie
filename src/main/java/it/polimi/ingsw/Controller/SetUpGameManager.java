package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.PlaceWorkerAction;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Requests.*;
import it.polimi.ingsw.Network.Message.Responses.DeckResponse;
import it.polimi.ingsw.Network.Message.Responses.PickGodResponse;
import it.polimi.ingsw.Network.Message.Responses.PlaceWorkerResponse;

import java.util.ArrayList;


/**
 *
 * This controller manages the set-up phases of the game, before the actual game starts.
 *
 */
public class SetUpGameManager {

    private final Game gameInstance;

    public Player activePlayer;
    private int currentPlayer;

    private int playerLoop = 0; // temporary var loop for player iteration





    public SetUpGameManager(Game game, Player activePlayer){

        MasterController.gameState = PossibleGameState.GAME_INIT;

        this.gameInstance = game;
        this.activePlayer = activePlayer;
        currentPlayer = gameInstance.getPlayers().indexOf(activePlayer);

        notifyTheGodLikePlayer(activePlayer);

    }


    /**
     * Notify the godlike {@link Player} that he has to chose the {@link java.util.ArrayList<God> } he wants to be part of the game
     *
     * @param godLikePlayer the god like {@link Player}
     */
    public void notifyTheGodLikePlayer(Player godLikePlayer) {

        MasterController.gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        //Notifico al player in questione che deve scegliere i god
        DeckResponse deckResponse = new DeckResponse(godLikePlayer.getPlayerName(), MessageStatus.OK, ""+gameInstance.getPlayers().size(), gameInstance.getDeck());
        gameInstance.putInChanges(godLikePlayer, deckResponse);

    }


    /**
     * Assign {@link God} to {@link Player}
     *
     * @param player the player
     * @param god    the god
     */
    public void assignGodToPlayer(Player player, God god) {
        player.setPlayerGod(god);
        god.setAssigned(true);
        MasterController.gameState = PossibleGameState.ASSIGNING_GOD;
    }


    /**
     * Check if the player is the turn owner
     *
     * @param username the player username
     * @return true if the player is the turn owner, false otherwise
     */
    private boolean isYourTurn(String username) {
        return username.equals(activePlayer.getPlayerName());
    }

    /**
     * It updates the player you expect an interaction from
     *
     * @return the {@link Player} who owns the turn
     */
    public Player nextPlayer() {
        currentPlayer++;
        Player next = gameInstance.getPlayers().get((currentPlayer % gameInstance.getPlayers().size()));

        return next;
    }





    // helper
    private Player getPlayerByName(String messageSender) {
            return gameInstance.searchPlayerByName(messageSender);
    }


    /**
     * Handles the {@link Request} sent by the client
     *
     * @param request the request
     */
    public void handleMessage(Request request) {

        if(!isYourTurn(request.getMessageSender())) {
            MasterController.buildNegativeResponse(getPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "It's not your turn!");
            return;
        }

        switch (request.getRequestContent()) {
            case CHOSEN_GODS -> handleGodsChosen((ChoseGodsRequest) request);
            case PICKED_GOD -> handleGodAssignment((AssignGodRequest) request);
            case PLACED_WORKER -> handlePlaceWorkerAction((PlaceWorkerRequest) request);
            default -> MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Something went wrong!");
        }
    }


    /**
     * It handles the {@link ChoseGodsRequest}
     *
     * @param request the request sent by the client
     */
    private void handleGodsChosen(ChoseGodsRequest request) {

        if(MasterController.gameState != PossibleGameState.GODLIKE_PLAYER_MOMENT){
            return;
        }

        if(request.getChosenGods().size() != gameInstance.getPlayers().size()){
            MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Troppi pochi gods");
            return;
        }

        gameInstance.setChosenGodsFromDeck(request.getChosenGods());

        //Turno del giocatore successivo
        activePlayer = nextPlayer();
        MasterController.gameState = PossibleGameState.ASSIGNING_GOD;

        // response: scegli un god
        PickGodResponse pickGodResponse = new PickGodResponse(activePlayer.getPlayerName(), MessageStatus.OK, "Pick a god", (ArrayList<God>) gameInstance.getChosenGodsFromDeck());
        gameInstance.putInChanges(activePlayer, pickGodResponse);

    }

    /**
     * It handles the {@link PickGodRequest}
     *
     * @param request the request sent by the client who own
     */
    private void handleGodAssignment(AssignGodRequest request) {

        if(MasterController.gameState != PossibleGameState.ASSIGNING_GOD){
            return;
        }

        //turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);

        assignGodToPlayer(activePlayer, request.getGod());

        activePlayer = nextPlayer();
        playerLoop++;

        if(playerLoop < gameInstance.getPlayers().size()) {
            PickGodResponse pickGodResponse = new PickGodResponse(activePlayer.getPlayerName(), MessageStatus.OK, "Pick a god", (ArrayList<God>) gameInstance.getChosenGodsFromDeck());
            gameInstance.putInChanges(activePlayer, pickGodResponse);
        }
        else {

            PlaceWorkerResponse placeWorkerResponse = new PlaceWorkerResponse(activePlayer.getPlayerName(), MessageStatus.OK, "place yout worker!", activePlayer.getPlayerWorkers().get(0));
            gameInstance.putInChanges(activePlayer, placeWorkerResponse);

            playerLoop = 0;
            MasterController.gameState = PossibleGameState.FILLING_BOARD;

        }
    }

    /**
     * It handles the {@link PlaceWorkerRequest}
     *
     * @param request the request sent by the client
     */
    private void handlePlaceWorkerAction(PlaceWorkerRequest request) {

        if(MasterController.gameState != PossibleGameState.FILLING_BOARD){
            return;
        }

        //turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);

        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(request.getWorkerToPlace(), positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {

            placeWorkerAction.doAction();

            if(activePlayer.areWorkersPlaced()) {   // se activePlayer ha già posizionato 2 worker

                activePlayer = nextPlayer();
                playerLoop++;

                if(playerLoop >= gameInstance.getPlayers().size()){
                    MasterController.buildPositiveResponse(activePlayer, ResponseContent.CHOOSE_WORKER, "Select a worker!");
                    MasterController.gameState = PossibleGameState.START_ROUND;
                    playerLoop = 0;
                    return;
                }

            }

            PlaceWorkerResponse placeWorkerResponse = new PlaceWorkerResponse(activePlayer.getPlayerName(), MessageStatus.OK, "place yout worker!", activePlayer.getPlayerWorkers().get(1));
            gameInstance.putInChanges(activePlayer, placeWorkerResponse);

        }
        else
            MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Errore nel posizionamento del worker");

    }






    // handler con check e javadoc

    /**
     * It handles the {@link ChoseGodsRequest} when the {@link PossibleGameState#GODLIKE_PLAYER_MOMENT}
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    /*
    Response handleGodsChosen(ChoseGodsRequest request) {
        gameInstance.setChosenGodsFromDeck(request.getChosenGods());
        turnManager.setGodsInGame(request.getChosenGods());


        gameManager.updateGameState(PossibleGameState.ASSIGNING_GOD);
        turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);

        return buildPositiveResponse("Gods selected!");
    }*/

    /**
     * It handles the {@link PickGodRequest} when the {@link PossibleGameState#ASSIGNING_GOD}
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    /*Response handlePickGodRequest(PickGodRequest request) {
        String requestSender = request.getMessageSender();
        Player activePlayer = turnManager.getActivePlayer();
        God pickedGod = request.getPickedGod();

        gameInstance.setGodToPlayer(activePlayer, pickedGod);

        if (gameInstance.godsPickedByEveryone()) {
            gameManager.updateGameState(PossibleGameState.FILLING_BOARD);
            turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);
            return buildPositiveResponse("Gods picked up, now it's time to fill the board!");
        }

        gameManager.updateGameState(PossibleGameState.ASSIGNING_GOD);
        turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);
        return buildPositiveResponse("God picked up!");
    }*/

    /**
     * It handle the {@link PlaceWorkerRequest} by a player that can be sent to Server after the {@link SelectWorkerRequest}
     * It's called only in {@link PossibleGameState#FILLING_BOARD} whe the players have to place their workers yet
     *
     * @param request the request sent by the client
     * @return positive/negative response if the client can perform action requested
     */
    /*Response handlePlaceWorkerAction(PlaceWorkerRequest request) {
        Player activePlayer = turnManager.getActivePlayer();
        Worker activeWorker = turnManager.getActiveWorker();
        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        //Controllo che il worker che il player vuole piazzare sia lo stesso che ha selezionato inizialmente
        if (request.getWorkerToPlace() != activeWorker) {
            return buildNegativeResponse("The worker you want to place isn't the worker selected!");
        }

        //Passo direttamente lo square su cui piazzare il worker dal controller, per ovviare alle getInstance in classi che non c'èentrano nulla
        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(activeWorker, positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {
            placeWorkerAction.doAction();
        } else {
            return buildNegativeResponse("You cannot place the worker here");
        }

        if (activePlayer.areWorkerPlaced()) {
            if ( gameInstance.workersPlacedByEveryone()) {
                gameManager.updateGameState(PossibleGameState.START_GAME);
                turnManager.updateTurnState(PossibleGameState.START_GAME);
                return buildPositiveResponse("Everybody have plaed their workers, we are ready to Start!");
            }

            gameManager.updateGameState(PossibleGameState.FILLING_BOARD);
            turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);
            return buildPositiveResponse("Worker Placed");
        }

        gameManager.updateGameState(PossibleGameState.FILLING_BOARD);
        return buildPositiveResponse("Worker Placed! Let's place the other one!");

    }*/





    // test

    /*
    public void _addPlayerToCurrentGame(String username) {
        gameInstance.addPlayer(username);
    }

    public void _setGameState(PossibleGameState gameState) {
        this.gameState = gameState;
    }

    public void _setNewActivePlayer(Player player) {
        turnManager.setActivePlayer(player);
    }

    public TurnManager _getTurnManager() {
        return this.turnManager;
    }

     */

}