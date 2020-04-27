package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Exceptions.Game.PlayerNotFoundException;
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
        gameInstance.buildPositiveResponse(godLikePlayer, MessageContent.GODS_CHOSE, "Let's chose which gods you want to be part of the game!");

    }

    public void assignGodToPlayer(Player player, God god) {
        player.setPlayerGod(god);
        god.setAssigned(true);
        gameState = PossibleGameState.IN_LOBBY;
    }




    // helper
    private Player getPlayerByName(String messageSender) {
        try {
            return gameInstance.searchPlayerByName(messageSender);
        }
        catch (PlayerNotFoundException e){
            System.out.println("### PLAYER NOT FOUND");
            e.printStackTrace();
            return null;
        }

    }

    public static boolean checkTurnOwnership(String username) {
        return username.equals(activePlayer.getPlayerName());
    }





    // handler
    public void handleMessage(Request message) {

        if(!checkTurnOwnership(message.getMessageSender()))
            return;

        switch (message.getMessageContent()){
            case GODS_CHOSE:
                handleGodsChosen((ChoseGodsRequest) message);
                break;

            case PLACE_WORKER:
                handlePlaceWorkerAction((PlaceWorkerRequest) message);
                break;

            case GOD_SELECTION:
                handleGodAssign((AssignGodRequest) message);
                break;
        }



        /*//SONO PRONTO A PARTIRE ==> INIZIALIZZO IL TURN MANAGER
        if (gameState == PossibleGameState.GODLIKE_PLAYER_MOMENT && veryFirstRound ) {
            return handleGodsChosen((ChoseGodsRequest) message);
        }
        switch(message.getMessageContent()) {
            case GODS_CHOSE:
                if (gameState == PossibleGameState.GODLIKE_PLAYER_MOMENT) {
                    return handleGodsChosen((ChoseGodsRequest) message);
                }
                break;
            //CONTROLLO IL PROSEGUO DELLA PARTITA
            case SELECT_WORKER:
                //Seleziono il worker da muovere/posizionare
                if (gameState == PossibleGameState.PLACING_WORKERS || gameState == PossibleGameState.SELECTING_WORKER ) {
                    return handleSelectWorkerAction((SelectWorkerRequest) message);
                }
                break;
            case PLACE_WORKER:
                //Piazzo i worker del singolo player
                if (gameState == PossibleGameState.PLACING_WORKERS && veryFirstRound) {
                    return handlePlaceWorkerAction((PlaceWorkerRequest) message );
                }
                break;
            case MOVE:
                //FACCIO MUOVERE IL GIOCATORE
                if (gameState == PossibleGameState.READY_TO_PLAY || gameState == PossibleGameState.FIRST_MOVE || gameState == PossibleGameState.WORKER_SELECTED) {
                    return handleMoveAction((MoveRequest) message);
                }
                break;
            case BUILD: //
                if (gameState == PossibleGameState.WORKER_MOVED) {
                    return handleBuildAction((BuildRequest) message);
                }
                break;
            case WORKER_MOVED: //
                break;
            case WORKER_CHOSEN: //
                break;
            case PLAYERS_HAS_BUILT: //
                break;
            case CHECK:
                break;
        }
        return buildNegativeResponse("Non si dovrebbe mai arrivare qui");*/
    }

    private void handleGodsChosen(ChoseGodsRequest request) {

        gameState = PossibleGameState.GODLIKE_PLAYER_MOMENT;

        gameInstance.setChosenGodsFromDeck(activePlayer, request.getChosenGod());

        activePlayer = TurnManager.nextPlayer();

        gameState = PossibleGameState.ACTION_DONE;
    }

    private void handlePlaceWorkerAction(PlaceWorkerRequest request) {

        gameState = PossibleGameState.PLACING_WORKERS;

        Position positionToPlaceWorker = request.getPositionToPlaceWorker();

        Square squareWhereToPlaceWorker = gameInstance.getGameMap().getSquare(positionToPlaceWorker);

        Action placeWorkerAction = new PlaceWorkerAction(activePlayer, request.getWorkerToPlace(), positionToPlaceWorker, squareWhereToPlaceWorker);

        if (placeWorkerAction.isValid()) {

            placeWorkerAction.doAction();
            gameState = PossibleGameState.ACTION_DONE;

        }

    }

    private void handleGodAssign(AssignGodRequest request) {

        gameState = PossibleGameState.ASSIGN_GOD;

        Player p = getPlayerByName(request.getMessageSender());
        assignGodToPlayer(p, request.getGod());

        activePlayer = TurnManager.nextPlayer();

        gameState = PossibleGameState.ACTION_DONE;
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