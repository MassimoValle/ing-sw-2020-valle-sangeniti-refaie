package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class SelectWorkerRequest extends Request {

    private final Worker workerToSelect;
    private Position workerToSelectPosition;



    public SelectWorkerRequest(String messageSender,  Worker workerToSelect, Position workerToSelectPosition) {
        super(messageSender, Dispatcher.TURN, RequestContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
        this.workerToSelectPosition = workerToSelectPosition;
    }


    //Used in case Worker hasn't been placed yet
    public SelectWorkerRequest(String messageSender,  Worker workerToSelect) {
        super(messageSender, Dispatcher.TURN, RequestContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
    }

    public Worker getWorkerToSelect() {
        return this.workerToSelect;
    }

    public Position getWorkerToSelectPosition() {
        return this.workerToSelectPosition;
    }

}
