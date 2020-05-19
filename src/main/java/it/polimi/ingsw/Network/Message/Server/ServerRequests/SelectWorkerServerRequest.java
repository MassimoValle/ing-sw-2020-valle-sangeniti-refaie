package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;

public class SelectWorkerServerRequest extends ServerRequest {

    public SelectWorkerServerRequest() {
        super(ServerRequestContent.SELECT_WORKER);
    }

}
