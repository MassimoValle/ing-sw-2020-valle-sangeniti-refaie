package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class PlaceWorkerResponse extends Response{

    private Worker worker;

    public PlaceWorkerResponse(String messageSender, String gameManagerSays, Worker worker) {
        super(messageSender, ResponseContent.PLACE_WORKER, MessageStatus.OK, gameManagerSays);
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }
}
