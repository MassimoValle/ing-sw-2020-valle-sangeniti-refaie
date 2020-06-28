package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.SelectWorkerServerResponse;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;

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
            case CHOOSE_GODS_SERVER_REQUEST -> ClientManager.clientView.youAreNotTheGodLikePlayer(turnOwner);
            case PICK_GOD                   -> ClientManager.clientView.anotherPlayerIsPickingUpGod(turnOwner);
            case PLACE_WORKER               -> ClientManager.clientView.anotherPlayerIsPlacingWorker(turnOwner);
            case START_TURN                 -> ClientManager.clientView.startingPlayerTurn(turnOwner);
            case SELECT_WORKER              -> ClientManager.clientView.anotherPlayerIsSelectingWorker(turnOwner);
            case MOVE_WORKER                -> ClientManager.clientView.anotherPlayerIsMoving(turnOwner);
            case BUILD                      -> ClientManager.clientView.anotherPlayerIsBuilding(turnOwner);
            default                         -> ClientManager.clientView.doNothing();
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

            case CHOOSE_GODS                -> ClientManager.clientView.anotherPlayerHasSelectedGods(turnOwner);
            case PICK_GOD                   -> ClientManager.clientView.anotherPlayerHasPickedUpGod(turnOwner);
            case PLACE_WORKER               -> ClientManager.clientView.anotherPlayerHasPlacedWorker(turnOwner);
            case SELECT_WORKER              -> ClientManager.clientView.anotherPlayerHasSelectedWorker((SelectWorkerServerResponse) serverResponse);
            case MOVE_WORKER                -> ClientManager.clientView.anotherPlayerHasMoved(turnOwner);
            case BUILD                      -> ClientManager.clientView.anotherPlayerHasBuilt(turnOwner);
            case END_TURN                   -> ClientManager.clientView.anotherPlayerHasEndedHisTurn(turnOwner);
            case PLAYER_HAS_WON             -> ClientManager.clientView.youLose(turnOwner);
            default                         -> ClientManager.clientView.doNothing();
        }
    }

}
