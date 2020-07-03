package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.message.Enum.UpdateType;
import it.polimi.ingsw.network.message.server.serverrequests.ChooseGodsServerRequest;
import it.polimi.ingsw.network.message.server.serverrequests.PickGodServerRequest;
import it.polimi.ingsw.network.message.server.serverrequests.PlaceWorkerServerRequest;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.PlaceWorkerAction;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.god.GodsInGame;
import it.polimi.ingsw.server.model.map.Square;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.clientrequests.*;
import it.polimi.ingsw.server.model.player.Worker;

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
    private Integer workerPlaced = 0;

    private PossibleGameState setupGameState;

    private final OutgoingMessageManager outgoingMessageManager;




    public SetUpGameManager(Game game, Player activePlayer, OutgoingMessageManager messageManager){

        this.setupGameState = PossibleGameState.GAME_INIT;

        this.gameInstance = game;
        this.outgoingMessageManager = messageManager;
        this.activePlayer = activePlayer;
        currentPlayer = gameInstance.getPlayers().indexOf(activePlayer);

        notifyTheGodLikePlayer(activePlayer);

    }


    /**
     * Notify the godlike {@link Player} that he has to chose the Gods he wants to be part of the game
     *
     * @param godLikePlayer the god like {@link Player}
     */
    public void notifyTheGodLikePlayer(Player godLikePlayer) {

        int howManyPlayers = gameInstance.getNumberOfPlayers();

        setupGameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        ChooseGodsServerRequest chooseGodsServerRequest = new ChooseGodsServerRequest(howManyPlayers);
        gameInstance.putInChanges(godLikePlayer, chooseGodsServerRequest);
    }


    /**
     * Assign {@link God} to {@link Player}
     *
     * @param player the player
     * @param god    the god
     */
    public void assignGodToPlayer(Player player, God god) {
        //setta la mappa nel power
        god.getGodPower().setMap(gameInstance.getGameMap());

        //aggiunge i god alla partita nella hashMap della GodsInGame
        GodsInGame.getIstance().addGodToGame(gameInstance, god);

        player.setPlayerGod(god);
        gameInstance.setGodAssigned(god);
        god.setAssigned(true);
        setupGameState = PossibleGameState.ASSIGNING_GOD;
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

        return gameInstance.getPlayers().get((currentPlayer % gameInstance.getPlayers().size()));
    }





    // helper
    private Player getPlayerByName(String messageSender) {
            return gameInstance.searchPlayerByName(messageSender);
    }


    /**
     * Handles the {@link Request} sent by the client
     *
     * @param request the request
     *
     * @return true if setup is completed
     */
    public boolean handleMessage(Request request) {

        if(!isYourTurn(request.getMessageSender())) {
            outgoingMessageManager.buildNegativeResponse(getPlayerByName(request.getMessageSender()), ResponseContent.CHECK, "It's not your turn!");
            return false;
        }

        switch (request.getRequestContent()) {
            case CHOSE_GODS -> {
                handleChooseGods((ChoseGodsRequest) request);
                return false;
            }
            case PICK_GOD -> {
                handlePickGod((PickGodRequest) request);
                return false;
            }
            case PLACE_WORKER -> {
                return handlePlaceWorker((PlaceWorkerRequest) request);
            }
            default -> outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Something went wrong!");
        }

        return false;
    }


    /**
     * It handles the {@link ChoseGodsRequest}
     *
     * @param request the request sent by the client
     */
    private void handleChooseGods(ChoseGodsRequest request) {
        ResponseContent responseContent = ResponseContent.CHOOSE_GODS;

        //Player requestSender = gameInstance.searchPlayerByName(request.getMessageSender());

        //verifico che è il momento del godlike player
        if(setupGameState != PossibleGameState.GODLIKE_PLAYER_MOMENT){
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "It's not the time to chose the gods");
            return;
        }

        //verifico che il nunmero di gods scelti sia corretto
        if(request.getChosenGods().size() != gameInstance.getPlayers().size()){
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "You sent the wrong number of gods! Try again!");
            return;
        }

        gameInstance.setChosenGodsFromDeck( (ArrayList<God>) request.getChosenGods());
        outgoingMessageManager.buildPositiveResponse(activePlayer, responseContent, "Gods selected!");

        //Turno del giocatore successivo
        activePlayer = nextPlayer();
        setupGameState = PossibleGameState.ASSIGNING_GOD;

        // response: scegli un god
        PickGodServerRequest pickGodServerRequest = new PickGodServerRequest(gameInstance.getChosenGodsFromDeck());
        gameInstance.putInChanges(activePlayer, pickGodServerRequest);

    }

    /**
     * It handles the {@link PickGodRequest}
     *
     * @param request the request sent by the client who own
     */
    private void handlePickGod(PickGodRequest request) {
        ResponseContent responseContent = ResponseContent.PICK_GOD;


        if(setupGameState != PossibleGameState.ASSIGNING_GOD){
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "It's not the time to pick a god");
            return;
        }


        assignGodToPlayer(activePlayer, request.getGod());

        outgoingMessageManager.buildPositiveResponse(activePlayer, responseContent, "confirm!");



        activePlayer = nextPlayer();
        playerLoop++;

        if(playerLoop < gameInstance.getPlayers().size()) {
            PickGodServerRequest pickGodServerRequest = new PickGodServerRequest( gameInstance.getUnassignedGods());
            gameInstance.putInChanges(activePlayer, pickGodServerRequest);
        }
        else {  // inizio la fase di piazzamento dei worker

            outgoingMessageManager.sendPlayersInfo();

            PlaceWorkerServerRequest placeWorkerServerRequest = new PlaceWorkerServerRequest(workerPlaced);
            gameInstance.putInChanges(activePlayer, placeWorkerServerRequest);

            playerLoop = 0;
            setupGameState = PossibleGameState.FILLING_BOARD;

        }
    }

    /**
     * It handles the {@link PlaceWorkerRequest}
     *
     * @param request the request sent by the client
     * @return true if the setup of game is done
     */
    private boolean handlePlaceWorker(PlaceWorkerRequest request) {

        ResponseContent responseContent = ResponseContent.PLACE_WORKER;

        if(setupGameState != PossibleGameState.FILLING_BOARD){
            outgoingMessageManager.buildNegativeResponse(activePlayer, responseContent, "It's not the time to place a worker!");
            return false;
        }

        //turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);

        Worker worker = activePlayer.getPlayerWorkers().get(request.getWorkerToPlace());

        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(worker, positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid(gameInstance.getGameMap())) {

            placeWorkerAction.doAction();
            workerPlaced += 1;

            outgoingMessageManager.buildPositiveResponse(activePlayer, responseContent, "Worker placed!");
            outgoingMessageManager.updateClients(activePlayer.getPlayerName(), UpdateType.PLACE, positionToPlaceWorker, worker.getNumber(), false);


            if(activePlayer.areWorkersPlaced()) {   // se activePlayer ha già posizionato 2 worker

                outgoingMessageManager.buildPositiveResponse(activePlayer, responseContent, "All workers are placed");

                activePlayer = nextPlayer();
                playerLoop++;
                workerPlaced = 0;


                // quando tutti hanno finito di piazzare i workers
                if(playerLoop >= gameInstance.getPlayers().size()){

                    playerLoop = 0;
                    return true;
                }

            }

            PlaceWorkerServerRequest placeWorkerServerRequest = new PlaceWorkerServerRequest(workerPlaced);
            gameInstance.putInChanges(activePlayer, placeWorkerServerRequest);



        }
        else {
            outgoingMessageManager.buildNegativeResponse(activePlayer, ResponseContent.PLACE_WORKER, "Errore nel posizionamento del worker");

        }

        return false;
    }




    // test

    public PossibleGameState getSetupGameState() {
        return setupGameState;
    }

    public void setSetupGameState(PossibleGameState gameState) {
        setupGameState = gameState;
    }


}