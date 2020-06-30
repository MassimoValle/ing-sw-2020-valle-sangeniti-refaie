package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

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
