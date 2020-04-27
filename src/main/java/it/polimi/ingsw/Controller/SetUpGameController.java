package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Model.Action.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Requests.AssignGodRequest;
import it.polimi.ingsw.Network.Message.Requests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.Requests.PlaceWorkerRequest;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Response;

public class SetUpGameController {

    private final Game gameInstance;

    public static Player lastActivePlayer;
    public static Worker lastActiveWorker;

    public static Player activePlayer;


    private PossibleGameState gameState;



    public SetUpGameController(Game game, Player activePlayer){

        gameState = PossibleGameState.GAME_INIT;

        this.gameInstance = game;
        this.activePlayer = activePlayer;

        notifyTheGodLikePlayer(activePlayer);

    }




    // getter
    public Game getGameInstance() {
        return this.gameInstance;
    }

    public PossibleGameState getGameState() {
        return gameState;
    }




    // function
    public void notifyTheGodLikePlayer(Player player) {

        //scegli un player a caso
        /*Random rand = new Random();
        int n = rand.nextInt(gameInstance.getNumberOfPlayers());
        Player godLikePlayer = gameInstance.getPlayers().get(n);*/

        Player godLikePlayer = player;

        gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        //Notifico al player in questione che deve scegliere i god
        ActionManager.buildPositiveResponse(godLikePlayer, MessageContent.GODS_CHOSE, "Let's chose which gods you want to be part of the game!");

    }

    public void assignGodToPlayer(Player player, God god) {
        player.setPlayerGod(god);
        god.setAssigned(true);
        gameState = PossibleGameState.ASSIGNING_GOD;
    }




    // helper
    private Player getPlayerByName(String messageSender) {
            return gameInstance.searchPlayerByName(messageSender);
    }

    private boolean checkTurnOwnership(String username) {
        return username.equals(activePlayer.getPlayerName());
    }





    // handler
    public void handleMessage(Request message) {

        if(!checkTurnOwnership(message.getMessageSender())) {
            ActionManager.buildNegativeResponse(getPlayerByName(message.getMessageSender()), message.getMessageContent(), "Not ur turn");
            return;
        }

        switch (message.getMessageContent()) {

            case GODS_CHOSE -> handleGodsChosen((ChoseGodsRequest) message);
            case PLACE_WORKER -> handlePlaceWorkerAction((PlaceWorkerRequest) message);
            case PICK_GOD -> handleGodAssign((AssignGodRequest) message);

        }
    }

    private void handleGodsChosen(ChoseGodsRequest request) {

        gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        gameInstance.setChosenGodsFromDeck(request.getChosenGod());
        ActionManager.buildPositiveResponse(activePlayer, MessageContent.GODS_CHOSE, "Gods selezionati");
        
        //activePlayer = TurnManager.nextPlayer();

        // response: scegli un god
        ActionManager.buildPositiveResponse(activePlayer, MessageContent.PICK_GOD, "Piglia un god");

    }

    private void handlePlaceWorkerAction(PlaceWorkerRequest request) {

        gameState = PossibleGameState.FILLING_BOARD;

        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(request.getWorkerToPlace(), positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {

            placeWorkerAction.doAction();

        }

    }

    private void handleGodAssign(AssignGodRequest request) {

        gameState = PossibleGameState.ASSIGNING_GOD;

        Player p = getPlayerByName(request.getMessageSender());
        assignGodToPlayer(p, request.getGod());

        //activePlayer = TurnManager.nextPlayer();
    }







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