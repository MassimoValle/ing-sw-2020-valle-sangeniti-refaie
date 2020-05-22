package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;

public class PlaceWorkerServerRequest extends ServerRequest{

    private final Integer workNum;

    public PlaceWorkerServerRequest(Integer workNum) {
        super(ServerRequestContent.PLACE_WORKER);
        this.workNum = workNum;
    }

    public Integer getWorker(){
        return workNum;
    }
}
