package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class SelectWorkerResponse extends Response{

    public SelectWorkerResponse(String messageSender, MessageStatus messageStatus,  String gameManagerSays) {
        super(messageSender, ResponseContent.SELECT_WORKER, messageStatus, gameManagerSays);
    }
}
