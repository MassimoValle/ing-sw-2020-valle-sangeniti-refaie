package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class SelectWorkerServerResponse extends ServerResponse {

    private final Integer workerSelected;

    public SelectWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays, Integer workerIndex) {
        super(ResponseContent.SELECT_WORKER, messageStatus, gameManagerSays);
        this.workerSelected = workerIndex;
    }


    public Integer getWorkerSelected() {
        return workerSelected;
    }

}
