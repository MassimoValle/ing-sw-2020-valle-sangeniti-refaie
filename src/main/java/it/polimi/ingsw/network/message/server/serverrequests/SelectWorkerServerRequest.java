package it.polimi.ingsw.network.message.server.serverrequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;

public class SelectWorkerServerRequest extends ServerRequest {

    public SelectWorkerServerRequest() {
        super(ServerRequestContent.SELECT_WORKER);
    }

}
