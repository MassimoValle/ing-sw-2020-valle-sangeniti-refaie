package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.server.serverrequests.ServerRequest;
import it.polimi.ingsw.network.message.server.serverresponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.server.serverresponse.ServerResponse;

/**
 * This class is used whenever the client receive a message but the player isn't the turnOwner
 */
public class ServerMessageManager {

    /**
     * The Turn owner.
     */
    String turnOwner = null;


    /**
     * Server request not for you.
     *
     * @param serverRequest the server request
     */
    public void serverRequestNotForYou(ServerRequest serverRequest) {
        turnOwner = serverRequest.getMessageRecipient();


        switch (serverRequest.getContent()) {
            case CHOOSE_GODS_SERVER_REQUEST -> ClientManager.getClientView().youAreNotTheGodLikePlayer(turnOwner);
            case PICK_GOD                   -> ClientManager.getClientView().anotherPlayerIsPickingUpGod(turnOwner);
            case PLACE_WORKER               -> ClientManager.getClientView().anotherPlayerIsPlacingWorker(turnOwner);
            case START_TURN                 -> ClientManager.getClientView().startingPlayerTurn(turnOwner);
            case SELECT_WORKER              -> ClientManager.getClientView().anotherPlayerIsSelectingWorker(turnOwner);
            case MOVE_WORKER                -> ClientManager.getClientView().anotherPlayerIsMoving(turnOwner);
            case BUILD                      -> ClientManager.getClientView().anotherPlayerIsBuilding(turnOwner);
            default                         -> { }
        }
    }

    /**
     * Server response not for you.
     *
     * @param serverResponse the server response
     */
    public void serverResponseNotForYou(ServerResponse serverResponse) {

        turnOwner = serverResponse.getMessageRecipient();

        if(serverResponse.getResponseStatus() == MessageStatus.ERROR)
            return;

        switch (serverResponse.getResponseContent()) {

            case CHOOSE_GODS                -> ClientManager.getClientView().anotherPlayerHasSelectedGods(turnOwner);
            case PICK_GOD                   -> ClientManager.getClientView().anotherPlayerHasPickedUpGod(turnOwner);
            case PLACE_WORKER               -> ClientManager.getClientView().anotherPlayerHasPlacedWorker(turnOwner);
            case SELECT_WORKER              -> ClientManager.getClientView().anotherPlayerHasSelectedWorker((SelectWorkerServerResponse) serverResponse);
            case MOVE_WORKER                -> ClientManager.getClientView().anotherPlayerHasMoved(turnOwner);
            case BUILD                      -> ClientManager.getClientView().anotherPlayerHasBuilt(turnOwner);
            case END_TURN                   -> ClientManager.getClientView().anotherPlayerHasEndedHisTurn(turnOwner);
            case PLAYER_HAS_WON             -> ClientManager.getClientView().youLose(turnOwner);
            default                         -> {}
        }
    }

}
