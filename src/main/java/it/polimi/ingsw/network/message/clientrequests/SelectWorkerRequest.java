package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class SelectWorkerRequest extends Request {

    private final Integer workerToSelect;

    public SelectWorkerRequest(String messageSender,  Integer workerToSelect) {
        super(messageSender, Dispatcher.TURN, RequestContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
    }

    public Integer getWorkerToSelect() {
        return this.workerToSelect;
    }

}
