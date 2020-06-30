package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

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
