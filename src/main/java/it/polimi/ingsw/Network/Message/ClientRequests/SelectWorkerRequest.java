package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

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
