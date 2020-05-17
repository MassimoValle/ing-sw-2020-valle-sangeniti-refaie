package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class SelectWorkerRequest extends Request {

    private final Worker workerToSelect;


    public SelectWorkerRequest(String messageSender,  Worker workerToSelect) {
        super(messageSender, Dispatcher.TURN, RequestContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
    }

    public Worker getWorkerToSelect() {
        return this.workerToSelect;
    }



}
