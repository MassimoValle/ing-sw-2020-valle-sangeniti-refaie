package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Message.ClientRequests.ChoseGodsRequest;
import it.polimi.ingsw.Network.Message.ClientRequests.PowerButtonRequest;
import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.*;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.*;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdateBoardMessage;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdatePlayersMessage;
import it.polimi.ingsw.Server.Controller.Enum.PossibleGameState;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.Network.Message.Enum.ResponseContent.MOVE_WORKER_AGAIN;


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

    public static void startFirstRound() {
        actionManager.startNextRound(true);
    }

    public static void gameOver() {
        //e mo?
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

                    List<Position> reachablePositions = gameInstance.getGameMap().getReachableAdjacentPlaces(activeWorker.getWorkerPosition());

                    ServerRequest moveWorkerServerRequest = new MoveWorkerServerRequest(reachablePositions);
                    gameInstance.putInChanges(player, moveWorkerServerRequest);
                }
                case BUILD -> {

                    List<Position> buildableSquares = gameInstance.getGameMap().getPlacesWhereYouCanBuildOn(activeWorker.getWorkerPosition());

                    ServerRequest buildServerRequest = new BuildServerRequest(buildableSquares);
                    gameInstance.putInChanges(player, buildServerRequest);
                }
                default -> { //END TURN
                    ServerRequest endTurnServerRequest = new EndTurnServerRequest();
                    gameInstance.putInChanges(player, endTurnServerRequest);
                }
            }

    }

    /**
     * Build the a {@link ServerResponse} with {@link MessageStatus#ERROR}
     *
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public static void buildNegativeResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.ERROR;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    /**
     * Build the a {@link ServerResponse} with {@link MessageStatus#OK}
     *
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public static void buildPositiveResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.OK;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    private static void buildResponse(Player player, ResponseContent content, MessageStatus status, String gameManagerSays) {

        switch (content) {

            case CHOOSE_GODS ->
                    gameInstance.putInChanges(player,
                            new ChooseGodsServerResponse(status, gameManagerSays)
                    );

            case PICK_GOD ->
                    gameInstance.putInChanges(player,
                            new PickGodServerResponse(status, gameManagerSays)
                    );

            case PLACE_WORKER ->
                    gameInstance.putInChanges(player,
                            new PlaceWorkerServerResponse(status, gameManagerSays));

            case SELECT_WORKER -> {

                int workerIndex = actionManager.getTurnManager().getActiveWorker().getWorkersNumber() + 1;
                gameInstance.putInChanges(player,
                        new SelectWorkerServerResponse(status, gameManagerSays, workerIndex)
                ); //in caso di risposta NEGTIVA il workerIndex non è significativo

            }

            case POWER_BUTTON ->
                    gameInstance.putInChanges(player,
                            new PowerButtonServerResponse(status, gameManagerSays)
                    );
            case MOVE_WORKER ->
                    gameInstance.putInChanges(player,
                            new MoveWorkerServerResponse(status, gameManagerSays)
                    );
            case MOVE_WORKER_AGAIN ->
                    gameInstance.putInChanges(player,
                            new MoveWorkerServerResponse(ResponseContent.MOVE_WORKER_AGAIN, status, gameManagerSays)
                    );

            case BUILD ->
                    gameInstance.putInChanges(player,
                            new BuildServerResponse(status, gameManagerSays)
                    );
            case BUILD_AGAIN ->
                    gameInstance.putInChanges(player,
                            new BuildServerResponse(ResponseContent.BUILD_AGAIN,status, gameManagerSays)
                    );
            case END_MOVE ->
                    gameInstance.putInChanges(player,
                            new EndMoveServerResponse(status, gameManagerSays)
                    );
            case END_BUILD ->
                    gameInstance.putInChanges(player,
                            new EndBuildServerResponse(status, gameManagerSays));
            case END_TURN ->
                    gameInstance.putInChanges(player,
                            new EndTurnServerResponse(status, gameManagerSays)
                    );
            default ->
                    gameInstance.putInChanges(player,
                            new ServerResponse(content, status, gameManagerSays)
                    );
        }

    }


    public static void buildWonResponse(Player player, String gameManagerSays) {

        WonServerResponse wonServerResponse = new WonServerResponse(gameManagerSays);
        gameInstance.putInChanges(player, wonServerResponse);

    }

    public static void updateClients(String player, UpdateType updateType, Position position, Integer workerIndex, boolean domePresent){


        UpdateBoardMessage updateMessage =  new UpdateBoardMessage(player, updateType, position, workerIndex, domePresent);
        gameInstance.putInChanges(new Player("ALL"), updateMessage);

    }

    public static void sendPlayersInfo(){

        ArrayList<UpdatePlayersMessage.ClientPlayer> clientPlayers = new ArrayList<>();

        for (Player player : gameInstance.getPlayers()) {
            clientPlayers.add(new UpdatePlayersMessage.ClientPlayer(player.getPlayerGod(), player.getColor(), player.getPlayerName()));
        }

        UpdatePlayersMessage updatePlayersMessage = new UpdatePlayersMessage(clientPlayers);

        Player ALL = new Player("ALL");
        gameInstance.putInChanges(ALL, updatePlayersMessage);
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
