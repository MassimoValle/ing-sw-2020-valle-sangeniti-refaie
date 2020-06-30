package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class PlaceWorkerRequest extends Request{

    private final Integer workerToPlace;
    private final Position positionToPlaceWorker;

    public PlaceWorkerRequest(String messageSender, Integer workerToPlace, Position positionToPlaceWorker) {
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.PLACE_WORKER);

        this.workerToPlace = workerToPlace;
        this.positionToPlaceWorker = positionToPlaceWorker;

    }

    public Integer getWorkerToPlace() {
        return this.workerToPlace;
    }

    public Position getPositionToPlaceWorker() {
        return this.positionToPlaceWorker;
    }

}
