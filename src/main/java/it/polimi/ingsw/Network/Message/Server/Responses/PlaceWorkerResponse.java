package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class PlaceWorkerResponse extends Response{

    private Integer worker;

    public PlaceWorkerResponse(String messageSender, String gameManagerSays, int worker) {
        super(messageSender, ResponseContent.PLACE_WORKER, MessageStatus.OK, gameManagerSays);
        this.worker = worker;
    }

    public Integer getWorker() {
        return worker;
    }
}
