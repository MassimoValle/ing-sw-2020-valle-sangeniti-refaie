package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class WorkerResponse extends Response{

    Worker worker;

    public WorkerResponse(String messageSender, MessageContent messageContent, MessageStatus messageStatus, String gameManagerSays, Worker worker) {
        super(messageSender, messageContent, messageStatus, gameManagerSays);
        this.worker = worker;
    }
}
