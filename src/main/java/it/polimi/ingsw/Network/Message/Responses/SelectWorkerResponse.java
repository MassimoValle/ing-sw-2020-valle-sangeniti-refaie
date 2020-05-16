package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class SelectWorkerResponse extends Response{
    public SelectWorkerResponse(String messageSender, String gameManagerSays) {
        super(messageSender, ResponseContent.SELECT_WORKER, MessageStatus.OK, gameManagerSays);
    }
}
