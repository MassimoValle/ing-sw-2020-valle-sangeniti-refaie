package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Model.PowerDecorators.Decorator;
import it.polimi.ingsw.Network.Message.Dispatcher;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.MessageStatus;

public class SelectWorkerRequest extends Request {

    private Worker workerToSelect;
    private Position workerToSelectPosition;



    public SelectWorkerRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus,  Worker workerToSelect, Position workerToSelectPosition) {
        super(messageSender, messageDispatcher, MessageContent.SELECT_WORKER, messageStatus);
        this.workerToSelect = workerToSelect;
        this.workerToSelectPosition = workerToSelectPosition;
    }


    //Used in case Worker hasn't been placed yet
    public SelectWorkerRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus,  Worker workerToSelect) {
        super(messageSender, messageDispatcher, MessageContent.SELECT_WORKER, messageStatus);
        this.workerToSelect = workerToSelect;
    }

    public Worker getWorkerToSelect() {
        return this.workerToSelect;
    }

    public Position getWorkerToSelectPosition() {
        return this.workerToSelectPosition;
    }

}
