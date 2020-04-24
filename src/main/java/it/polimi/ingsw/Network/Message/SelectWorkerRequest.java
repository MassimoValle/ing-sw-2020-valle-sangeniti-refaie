package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class SelectWorkerRequest extends Request {

    private Worker workerToSelect;
    private Position workerToSelectPosition;



    public SelectWorkerRequest(String messageSender,  Worker workerToSelect, Position workerToSelectPosition) {
        super(messageSender,  MessageContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
        this.workerToSelectPosition = workerToSelectPosition;
    }


    //Used in case Worker hasn't been placed yet
    public SelectWorkerRequest(String messageSender, Worker workerToSelect) {
        super(messageSender, MessageContent.SELECT_WORKER);
        this.workerToSelect = workerToSelect;
    }

    public Worker getWorkerToSelect() {
        return this.workerToSelect;
    }

    public Position getWorkerToSelectPosition() {
        return this.workerToSelectPosition;
    }

}
