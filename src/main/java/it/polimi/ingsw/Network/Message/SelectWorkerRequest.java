package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class SelectWorkerRequest extends Request {

    private Worker workerToSelect;
    private Position workerToSelectPosition;


    public SelectWorkerRequest(String messageSender, Worker workerToSelect, Position workerToSelectPosition) {
        super(messageSender);
        this.workerToSelect = workerToSelect;
        this.workerToSelectPosition = workerToSelectPosition;
    }

}
