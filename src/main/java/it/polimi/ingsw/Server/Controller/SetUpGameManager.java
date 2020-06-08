package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ChooseGodsServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.PickGodServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.PlaceWorkerServerRequest;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Model.Action.Action;
import it.polimi.ingsw.Server.Model.Action.PlaceWorkerAction;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.ClientRequests.*;
import it.polimi.ingsw.Server.Model.Player.Worker;


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
    private Integer workerNum = 0;

    private PossibleGameState setupGameState;




    public SetUpGameManager(Game game, Player activePlayer){

        this.setupGameState = PossibleGameState.GAME_INIT;

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
            case CHOSE_GODS -> handleChooseGods((ChoseGodsRequest) request);
            case PICK_GOD -> handlePickGod((PickGodRequest) request);
            case PLACE_WORKER -> handlePlaceWorker((PlaceWorkerRequest) request);
            default -> MasterController.buildNegativeResponse(activePlayer, ResponseContent.CHECK, "Something went wrong!");
        }
    }


    /**
     * It handles the {@link ChoseGodsRequest}
     *
     * @param request the request sent by the client
     */
    private void handleChooseGods(ChoseGodsRequest request) {
        ResponseContent responseContent = ResponseContent.CHOOSE_GODS;

        Player requestSender = gameInstance.searchPlayerByName(request.getMessageSender());

        //verifico che è il momento del godlike player
        if(setupGameState != PossibleGameState.GODLIKE_PLAYER_MOMENT){
            MasterController.buildNegativeResponse(requestSender, responseContent, "It's not the time to chose the gods");
            return;
        }

        //verifico che il nunmero di gods scelti sia corretto
        if(request.getChosenGods().size() != gameInstance.getPlayers().size()){
            MasterController.buildNegativeResponse(requestSender, responseContent, "You sent the wrong number of gods! Try again!");
            return;
        }

        gameInstance.setChosenGodsFromDeck(request.getChosenGods());
        MasterController.buildPositiveResponse(requestSender, responseContent, "Gods selected!");

        //Turno del giocatore successivo
        activePlayer = nextPlayer();
        setupGameState = PossibleGameState.ASSIGNING_GOD;

        // response: scegli un god
        PickGodServerRequest pickGodServerRequest = new PickGodServerRequest(gameInstance.getChosenGodsFromDeck());
        //PickGodServerResponse pickGodResponse = new PickGodServerResponse("Pick a god", (ArrayList<God>) gameInstance.getChosenGodsFromDeck());
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
            MasterController.buildNegativeResponse(activePlayer, responseContent, "It's not the time to pick a god");
            return;
        }


        assignGodToPlayer(activePlayer, request.getGod());

        MasterController.buildPositiveResponse(activePlayer, responseContent, "confirm!");



        activePlayer = nextPlayer();
        playerLoop++;

        if(playerLoop < gameInstance.getPlayers().size()) {
            PickGodServerRequest pickGodServerRequest = new PickGodServerRequest( gameInstance.getUnassignedGods());
            gameInstance.putInChanges(activePlayer, pickGodServerRequest);
        }
        else {

            MasterController.sendPlayersInfo();

            PlaceWorkerServerRequest placeWorkerServerRequest = new PlaceWorkerServerRequest( workerNum);
            gameInstance.putInChanges(activePlayer, placeWorkerServerRequest);

            playerLoop = 0;
            setupGameState = PossibleGameState.FILLING_BOARD;

        }
    }

    /**
     * It handles the {@link PlaceWorkerRequest}
     *
     * @param request the request sent by the client
     */
    private void handlePlaceWorker(PlaceWorkerRequest request) {

        ResponseContent responseContent = ResponseContent.PLACE_WORKER;

        if(setupGameState != PossibleGameState.FILLING_BOARD){
            MasterController.buildNegativeResponse(activePlayer, responseContent, "It's not the time to place a worker!");
            return;
        }

        //turnManager.updateTurnState(PossibleGameState.FILLING_BOARD);

        Worker worker = activePlayer.getPlayerWorkers().get(request.getWorkerToPlace());

        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(worker, positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {

            workerNum++;
            if(workerNum > 1) workerNum = 0;

            placeWorkerAction.doAction();

            MasterController.buildPositiveResponse(activePlayer, responseContent, "Worker placed!");
            MasterController.updateClients(activePlayer.getPlayerName(), UpdateType.PLACE, positionToPlaceWorker, worker.getNumber(), false);


            if(activePlayer.areWorkersPlaced()) {   // se activePlayer ha già posizionato 2 worker

                MasterController.buildPositiveResponse(activePlayer, responseContent, "All workers are placed");

                activePlayer = nextPlayer();
                playerLoop++;


                // quando tutti hanno finito di piazzare i workers
                if(playerLoop >= gameInstance.getPlayers().size()){

                    MasterController.startFirstRound();
                    return;
                }

            }

            PlaceWorkerServerRequest placeWorkerServerRequest = new PlaceWorkerServerRequest(workerNum);
            gameInstance.putInChanges(activePlayer, placeWorkerServerRequest);



        }
        else {
            MasterController.buildNegativeResponse(activePlayer, ResponseContent.PLACE_WORKER, "Errore nel posizionamento del worker");

        }

    }




    // test

    public PossibleGameState getSetupGameState() {
        return setupGameState;
    }

    public void setSetupGameState(PossibleGameState gameState) {
        setupGameState = gameState;
    }


}