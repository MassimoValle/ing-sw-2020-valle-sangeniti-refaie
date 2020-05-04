package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class PlaceWorkerRequest extends Request{

    private final Worker workerToPlace;
    private final Position positionToPlaceWorker;

    public PlaceWorkerRequest(String messageSender, Worker workerToPlace, Position positionToPlaceWorker) {
        super(messageSender, Dispatcher.SETUP_GAME, MessageContent.PLACE_WORKER);

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
