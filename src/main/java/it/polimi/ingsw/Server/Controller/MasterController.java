package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Network.Message.Server.Responses.*;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ServerRequest;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.BuildServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.EndTurnServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.MoveWorkerServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.SelectWorkerServerRequest;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdateBoardMessage;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;


public class MasterController {

    private static SetUpGameManager setUpGameManager;
    private final TurnManager turnManager;
    private static ActionManager actionManager;

    private static Game gameInstance;





    public MasterController(Game gameInstance, Player activePlayer){

        MasterController.gameInstance = gameInstance;

        setUpGameManager = new SetUpGameManager(gameInstance, activePlayer);
        turnManager = new TurnManager(gameInstance.getPlayers());
        actionManager = new ActionManager(gameInstance, turnManager);

    }


    public Game getGameInstance() {
        return gameInstance;
    }


    /**
     * It dispatch the request based on game phases
     *
     * @param request the request sent by the client
     */
    public static void dispatcher(Request request){

        switch (request.getMessageDispatcher()) {
            case SETUP_GAME -> setUpGameManager.handleMessage(request);
            case TURN -> actionManager.handleRequest(request);
        }

    }





    // FIXME: da decidere se tenere qui

    /**
     * Build a specific {@link ServerRequest} based on the {@link ServerRequestContent} and
     * send it to the {@link Player}
     * @param player       the {@link Player} to whom the request is intended
     * @param content      the {@link ServerRequestContent}
     * @param activeWorker the {@link Worker} the player has to move/built with.
     */
    public static void buildServerRequest(Player player, ServerRequestContent content, Worker activeWorker) {

            switch (content) {
                case SELECT_WORKER -> {
                    ServerRequest selectWorkerServerRequest = new SelectWorkerServerRequest();
                    gameInstance.putInChanges(player, selectWorkerServerRequest);
                }
                case MOVE_WORKER -> {
                    ArrayList<Position> nearlyPosition = gameInstance.getGameMap().getReachableAdjacentPlaces(activeWorker.getWorkerPosition());
                    ServerRequest moveWorkerServerRequest = new MoveWorkerServerRequest(nearlyPosition);
                    gameInstance.putInChanges(player, moveWorkerServerRequest);
                }
                case BUILD -> {
                    ArrayList<Position> possiblePlaceToBuild = gameInstance.getGameMap().getPlacesWhereYouCanBuildOn(activeWorker.getWorkerPosition());
                    ServerRequest buildServerRequest = new BuildServerRequest(possiblePlaceToBuild);
                    gameInstance.putInChanges(player, buildServerRequest);
                }
                default -> { //END TURN
                    ServerRequest endTurnServerRequest = new EndTurnServerRequest();
                    gameInstance.putInChanges(player, endTurnServerRequest);
                }
            }

    }

    /**
     * Build the a {@link Response} with {@link MessageStatus#ERROR}
     *
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public static void buildNegativeResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.ERROR;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    /**
     * Build the a {@link Response} with {@link MessageStatus#OK}
     *
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public static void buildPositiveResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.OK;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    private static void buildResponse(Player player, ResponseContent content, MessageStatus status, String gameManagerSays) {

        String playerName = player.getPlayerName();

        switch (content) {
            case SELECT_WORKER ->
                    gameInstance.putInChanges(player,
                            new SelectWorkerResponse(playerName, status, gameManagerSays)
                    );

            case MOVE_WORKER ->
                    gameInstance.putInChanges(player,
                            new MoveWorkerResponse(playerName, status, gameManagerSays)
                    );
            case BUILD ->
                    gameInstance.putInChanges(player,
                            new BuildResponse(playerName, status, gameManagerSays)
                    );
            case END_MOVE ->
                    gameInstance.putInChanges(player,
                            new EndMoveResponse(playerName, status, gameManagerSays)
                    );
            case END_BUILD ->
                    gameInstance.putInChanges(player,
                            new EndBuildResponse(playerName, status, gameManagerSays));
            case END_TURN ->
                    gameInstance.putInChanges(player,
                            new EndTurnResponse(playerName, status, gameManagerSays)
                    );
            default ->
                    gameInstance.putInChanges(player,
                            new Response(playerName, content, status, gameManagerSays)
                    );
        }

    }

    //Fa la stessa identica cosa del buildPositiveResponse solo che invia una WonResponse
    public static Response buildWonResponse(Player player, String gameManagerSays) {

        Response res = new WonResponse(player.getPlayerName(), gameManagerSays);
        gameInstance.putInChanges(player, res);

        //da notificare a tutti gli altri giocatori che un player ha vinto --> fine partita

        return res;
    }

    public static void updateClients(String player, UpdateType updateType, Position position, Integer workerIndex){

        for(Player playerToSendUpdate: gameInstance.getPlayers()) {
            UpdateBoardMessage updateMessage =  new UpdateBoardMessage(player, updateType, position, workerIndex);
            gameInstance.putInChanges(playerToSendUpdate, updateMessage);
        }



    }

    public static void sendListOfPlayer(){

        for(Player playerToSendUpdate : gameInstance.getPlayers()) {

            for (Player player : gameInstance.getPlayers()) {

                UpdatePlayersMessage updatePlayersMessage = new UpdatePlayersMessage(

                        player.getPlayerName(),
                        player.getPlayerGod(),
                        player.getColor()

                );

                gameInstance.putInChanges(playerToSendUpdate, updatePlayersMessage);
            }

        }
    }

    //      ####    TESTING-ONLY    ####
    public SetUpGameManager _getSetUpGameController() {
        return setUpGameManager;
    }
    public ActionManager _getActionManager() {
        return actionManager;
    }
    public TurnManager _getTurnManager() {
        return turnManager;
    }
}
