package it.polimi.ingsw.network.message.Server.ServerRequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.server.model.Player.Position;

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
