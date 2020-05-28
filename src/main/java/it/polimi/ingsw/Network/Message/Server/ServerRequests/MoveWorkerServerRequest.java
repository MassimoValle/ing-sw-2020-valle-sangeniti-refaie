package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;
import java.util.List;

public class MoveWorkerServerRequest extends ServerRequest {

    private final List<Position> reachablePositions;

    public MoveWorkerServerRequest(List<Position> reachablePositions) {
        super(ServerRequestContent.MOVE_WORKER);
        this.reachablePositions = reachablePositions;
    }

    public List<Position> getReachablePositions() {
        return reachablePositions;
    }

}
