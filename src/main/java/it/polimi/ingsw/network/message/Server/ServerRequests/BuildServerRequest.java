package it.polimi.ingsw.network.message.Server.ServerRequests;

import it.polimi.ingsw.network.message.Enum.ServerRequestContent;
import it.polimi.ingsw.server.model.Player.Position;

import java.util.List;

public class BuildServerRequest extends ServerRequest {

    private final List<Position> buildableSquares;

    public BuildServerRequest(List<Position> buildableSquares) {
        super(ServerRequestContent.BUILD);
        this.buildableSquares = buildableSquares;
    }

    public List<Position> getBuildableSquares() {
        return buildableSquares;
    }
}
