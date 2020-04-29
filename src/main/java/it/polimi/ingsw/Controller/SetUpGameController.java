package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Requests.*;

public class SetUpGameController {

    private final Game gameInstance;

    public Player activePlayer;
    private int currentPlayer;

    private int playerLoop = 0; // temporary var loop for player iteration





    public SetUpGameController(Game game, Player activePlayer){

        SuperMegaController.gameState = PossibleGameState.GAME_INIT;

        this.gameInstance = game;
        this.activePlayer = activePlayer;
        currentPlayer = gameInstance.getPlayers().indexOf(activePlayer);

        notifyTheGodLikePlayer(activePlayer);

    }





    // function
    public void notifyTheGodLikePlayer(Player godLikePlayer) {

        //scegli un player a caso
        /*Random rand = new Random();
        int n = rand.nextInt(gameInstance.getNumberOfPlayers());
        Player godLikePlayer = gameInstance.getPlayers().get(n);*/

        SuperMegaController.gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        //Notifico al player in questione che deve scegliere i god
        SuperMegaController.buildPositiveResponse(godLikePlayer, MessageContent.GODS_CHOSE, "Let's chose which gods you want to be part of the game!");

    }

    public void assignGodToPlayer(Player player, God god) {
        player.setPlayerGod(god);
        god.setAssigned(true);
        SuperMegaController.gameState = PossibleGameState.ASSIGNING_GOD;
    }

    private boolean checkTurnOwnership(String username) {
        return username.equals(activePlayer.getPlayerName());
    }

    public Player nextPlayer() {
        currentPlayer++;
        return gameInstance.getPlayers().get((currentPlayer % gameInstance.getPlayers().size() +1));
    }





    // helper
    private Player getPlayerByName(String messageSender) {
            return gameInstance.searchPlayerByName(messageSender);
    }





    // handler
    public void handleMessage(Request request) {

        if(!checkTurnOwnership(request.getMessageSender())) {
            SuperMegaController.buildNegativeResponse(getPlayerByName(request.getMessageSender()), request.getMessageContent(), "Not ur turn");
            return;
        }

        switch (request.getMessageContent()) {

            case GODS_CHOSE -> handleGodsChosen((ChoseGodsRequest) request);
            case PICK_GOD -> handleGodAssignment((AssignGodRequest) request);
            case PLACE_WORKER -> handlePlaceWorkerAction((PlaceWorkerRequest) request);

        }
    }

    private void handleGodsChosen(ChoseGodsRequest request) {

        SuperMegaController.gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        gameInstance.setChosenGodsFromDeck(request.getChosenGod());
        SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.GODS_CHOSE, "Gods selezionati");

        activePlayer = nextPlayer();

        // response: scegli un god
        SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.PICK_GOD, "Piglia un god");

    }

    private void handleGodAssignment(AssignGodRequest request) {

        SuperMegaController.gameState = PossibleGameState.ASSIGNING_GOD;
        //turnManager.updateTurnState(PossibleGameState.ASSIGNING_GOD);

        //Player p = getPlayerByName(request.getMessageSender());
        assignGodToPlayer(activePlayer, request.getGod());

        activePlayer = nextPlayer();
        playerLoop++;

        if(playerLoop < gameInstance.getPlayers().size())
            SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.PICK_GOD, "Piglia un god");

        else {

            SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.PLACE_WORKER, "Posizione un worker");
            playerLoop = 0;

        }
    }

    private void handlePlaceWorkerAction(PlaceWorkerRequest request) {

        SuperMegaController.gameState = PossibleGameState.FILLING_BOARD;
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
                    SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.SELECT_WORKER, "Piglia un god");
                    playerLoop = 0;
                    return;
                }

            }

            SuperMegaController.buildPositiveResponse(activePlayer, MessageContent.PLACE_WORKER, "Posizione un worker");

        }
        else
            SuperMegaController.buildNegativeResponse(activePlayer, MessageContent.PLACE_WORKER, "Errore nel posizionamento del worker");

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
        gameInstance.setChosenGodsFromDeck(request.getChosenGod());
        turnManager.setGodsInGame(request.getChosenGod());


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