package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class SelectWorkerServerResponse extends ServerResponse {

    public SelectWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.SELECT_WORKER, messageStatus, gameManagerSays);
    }

}
