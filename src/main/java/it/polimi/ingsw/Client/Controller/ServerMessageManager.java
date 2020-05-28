package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Server.ServerRequests.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.SelectWorkerServerResponse;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;

public class ServerMessageManager {

    String turnOwner = null;


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
            case PLAYER_WON                 -> ClientManager.clientView.youLose(turnOwner);
            default                         -> ClientManager.clientView.doNothing();
        }
    }

}
