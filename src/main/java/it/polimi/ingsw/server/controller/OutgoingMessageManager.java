package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;
import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.network.message.Enum.UpdateType;
import it.polimi.ingsw.network.message.server.serverrequests.*;
import it.polimi.ingsw.network.message.server.serverresponse.*;
import it.polimi.ingsw.network.message.server.updatemessage.UpdateBoardMessage;
import it.polimi.ingsw.network.message.server.updatemessage.UpdatePlayersMessage;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.server.model.player.Worker;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to send every kind of {@link it.polimi.ingsw.network.message.Message} to the clients
 */
public class OutgoingMessageManager {

    private final Game gameInstance;
    private final TurnManager turnManager;





    public OutgoingMessageManager(Game gameInstance, TurnManager turnManager){

        this.gameInstance = gameInstance;
        this.turnManager = turnManager;

    }

    /**
     * Build the a {@link ServerResponse} with {@link MessageStatus#ERROR}
     *
     * @param player          the player
     * @param responseContent the response content
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public void buildNegativeResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.ERROR;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    /**
     * Build the a {@link ServerResponse} with {@link MessageStatus#OK}
     *
     * @param player          the player
     * @param responseContent the response content
     * @param gameManagerSays the message from the {@link ActionManager}
     */
    public void buildPositiveResponse(Player player, ResponseContent responseContent, String gameManagerSays) {

        MessageStatus status = MessageStatus.OK;

        buildResponse(player, responseContent, status, gameManagerSays);

    }

    public void buildWonResponse(Player player, String gameManagerSays) {

        WonServerResponse wonServerResponse = new WonServerResponse(gameManagerSays);
        gameInstance.putInChanges(player, wonServerResponse);

    }

    private void buildResponse(Player player, ResponseContent content, MessageStatus status, String gameManagerSays) {

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
                int workerIndex;
                if (status == MessageStatus.OK)
                    workerIndex = turnManager.getActiveWorker().getNumber() + 1;
                else
                    workerIndex = -1;
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

            case DISCONNECT ->
                    gameInstance.putInChanges(player,
                            new PlayerDisconnectedResponse(status, "")
                    );

            default ->
                    gameInstance.putInChanges(player,
                            new ServerResponse(content, status, gameManagerSays)
                    );
        }

    }





    // REQUEST
    /**
     * Build a specific {@link ServerRequest} based on the {@link ServerRequestContent} and
     * send it to the {@link Player}
     * @param player       the {@link Player} to whom the request is intended
     * @param content      the {@link ServerRequestContent}
     * @param activeWorker the {@link Worker} the player has to move/built with.
     */
    public void buildServerRequest(Player player, ServerRequestContent content, Worker activeWorker) {

        switch (content) {

            case SELECT_WORKER -> {
                ServerRequest selectWorkerServerRequest = new SelectWorkerServerRequest();
                gameInstance.putInChanges(player, selectWorkerServerRequest);
            }
            case MOVE_WORKER -> {

                List<Position> reachablePositions = gameInstance.getGameMap().getReachableAdjacentPlaces(activeWorker.getPosition());

                ServerRequest moveWorkerServerRequest = new MoveWorkerServerRequest(reachablePositions);
                gameInstance.putInChanges(player, moveWorkerServerRequest);
            }
            case BUILD -> {

                List<Position> buildableSquares = gameInstance.getGameMap().getPlacesWhereYouCanBuildOn(activeWorker.getPosition());

                ServerRequest buildServerRequest = new BuildServerRequest(buildableSquares);
                gameInstance.putInChanges(player, buildServerRequest);
            }
            default -> { //END TURN
                ServerRequest endTurnServerRequest = new EndTurnServerRequest();
                gameInstance.putInChanges(player, endTurnServerRequest);
            }
        }

    }




    // UPDATE
    public void updateClients(String player, UpdateType updateType, Position position, Integer workerIndex, boolean domePresent){


        UpdateBoardMessage updateMessage =  new UpdateBoardMessage(player, updateType, position, workerIndex, domePresent);
        gameInstance.putInChanges(new Player("ALL"), updateMessage);

    }

    public void sendPlayersInfo(){

        ArrayList<UpdatePlayersMessage.ClientPlayer> clientPlayers = new ArrayList<>();

        for (Player player : gameInstance.getPlayers()) {
            clientPlayers.add(new UpdatePlayersMessage.ClientPlayer(player.getPlayerGod(), player.getColor(), player.getPlayerName()));
        }

        UpdatePlayersMessage updatePlayersMessage = new UpdatePlayersMessage(clientPlayers);

        Player ALL = new Player("ALL");
        gameInstance.putInChanges(ALL, updatePlayersMessage);
    }
}
