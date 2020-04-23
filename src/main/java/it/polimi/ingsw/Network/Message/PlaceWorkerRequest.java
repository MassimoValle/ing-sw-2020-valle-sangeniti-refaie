package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class PlaceWorkerRequest extends Request{

    private Worker workerToPlace;
    private Position positionToPlaceWorker;

    public PlaceWorkerRequest(String messageSender, Worker workerToPlace, Position positionToPlaceWorker) {
        super(messageSender, MessageContent.PLACE_WORKER);

        this.workerToPlace = workerToPlace;
        this.positionToPlaceWorker = positionToPlaceWorker;

    }

    public Worker getWorkerToPlace() {
        return this.workerToPlace;
    }

    public Position getPositionToPlaceWorker() {
        return this.positionToPlaceWorker;
    }

}
