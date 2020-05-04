package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class SelectWorkerRequest extends Request {

    private final Worker workerToSelect;
    private Position workerToSelectPosition;



    public SelectWorkerRequest(String messageSender, MessageStatus messageStatus,  Worker workerToSelect, Position workerToSelectPosition) {
        super(messageSender, Dispatcher.TURN, MessageContent.SELECT_WORKER, messageStatus);
        this.workerToSelect = workerToSelect;
        this.workerToSelectPosition = workerToSelectPosition;
    }


    //Used in case Worker hasn't been placed yet
    public SelectWorkerRequest(String messageSender, MessageStatus messageStatus,  Worker workerToSelect) {
        super(messageSender, Dispatcher.TURN, MessageContent.SELECT_WORKER, messageStatus);
        this.workerToSelect = workerToSelect;
    }

    public Worker getWorkerToSelect() {
        return this.workerToSelect;
    }

    public Position getWorkerToSelectPosition() {
        return this.workerToSelectPosition;
    }

}
