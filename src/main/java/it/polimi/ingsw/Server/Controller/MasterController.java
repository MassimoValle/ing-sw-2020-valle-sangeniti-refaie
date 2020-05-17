package it.polimi.ingsw.Server.Controller;

import it.polimi.ingsw.Network.Message.Server.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequestContent;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.BuildServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.EndTurnServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.MoveWorkerServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.SelectWorkerServerRequest;
import it.polimi.ingsw.Server.Model.Game;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Network.Message.Requests.Request;
import it.polimi.ingsw.Network.Message.Responses.Response;
import it.polimi.ingsw.Network.Message.Responses.WonResponse;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.geometry.Pos;

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

    public static ServerRequest buildServerRequest(Player player, ServerRequestContent content, Worker activeWorker) {

            switch (content) {
                case SELECT_WORKER -> {
                    ServerRequest selectWorkerServerRequest = new SelectWorkerServerRequest();
                    gameInstance.putInChanges(player, selectWorkerServerRequest);
                    return selectWorkerServerRequest;
                }
                case MOVE_WORKER -> {
                    ArrayList<Position> nearlyPosition = gameInstance.getGameMap().getReachableAdjacentPlaces(activeWorker.getWorkerPosition());
                    ServerRequest moveWorkerServerRequest = new MoveWorkerServerRequest(nearlyPosition);
                    gameInstance.putInChanges(player, moveWorkerServerRequest);
                    return moveWorkerServerRequest;
                }
                case BUILD -> {
                    ArrayList<Position> possiblePlaceToBuild = gameInstance.getGameMap().getPlacesWhereYouCanBuildOn(activeWorker.getWorkerPosition());
                    ServerRequest buildServerRequest = new BuildServerRequest(possiblePlaceToBuild);
                    gameInstance.putInChanges(player, buildServerRequest);
                    return buildServerRequest;
                }
                default -> { //END TURN
                    ServerRequest endTurnServerRequest = new EndTurnServerRequest();
                    gameInstance.putInChanges(player, endTurnServerRequest);
                    return endTurnServerRequest;
                }
            }

    }

    /**
     * Build negative response.
     *
     * @param gameManagerSays the message from the Game Manager
     * @return the response
     */
    public static void buildNegativeResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.ERROR;

        Response res = new Response(player.getPlayerName(), responseContent, status, gameManagerSays);
        gameInstance.putInChanges(player, res);

        //return res;

    }

    /**
     * Build Positive response.
     *
     * @param gameManagerSays the message from the Game Manager
     * @return the response
     */
    public static Response buildPositiveResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.OK;

        Response res = new Response(player.getPlayerName(), responseContent, status, gameManagerSays);
        gameInstance.putInChanges(player, res);

        return res;
    }

    //Fa la stessa identica cosa del buildPositiveResponse solo che invia una WonResponse
    public static Response buildWonResponse(Player player, String gameManagerSays) {

        Response res = new WonResponse(player.getPlayerName(), gameManagerSays);
        gameInstance.putInChanges(player, res);

        //da notificare a tutti gli altri giocatori che un player ha vinto --> fine partita

        return res;
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
