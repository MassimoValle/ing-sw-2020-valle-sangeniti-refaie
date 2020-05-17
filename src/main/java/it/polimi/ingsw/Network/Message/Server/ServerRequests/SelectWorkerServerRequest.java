package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Server.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequestContent;

public class SelectWorkerServerRequest extends ServerRequest {

    public SelectWorkerServerRequest() {
        super(ServerRequestContent.SELECT_WORKER);
    }
}
