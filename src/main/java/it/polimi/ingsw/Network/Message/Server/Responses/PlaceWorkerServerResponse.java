package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class PlaceWorkerServerResponse extends ServerResponse {

    private Integer worker;

    public PlaceWorkerServerResponse(String gameManagerSays, int worker) {
        super(ResponseContent.PLACE_WORKER, MessageStatus.OK, gameManagerSays);
        this.worker = worker;
    }

    public Integer getWorker() {
        return worker;
    }
}
