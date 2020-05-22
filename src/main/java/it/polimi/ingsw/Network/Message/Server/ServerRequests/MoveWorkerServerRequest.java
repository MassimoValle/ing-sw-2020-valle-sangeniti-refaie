package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class MoveWorkerServerRequest extends ServerRequest {

    public MoveWorkerServerRequest() {
        super(ServerRequestContent.MOVE_WORKER);
    }

}
