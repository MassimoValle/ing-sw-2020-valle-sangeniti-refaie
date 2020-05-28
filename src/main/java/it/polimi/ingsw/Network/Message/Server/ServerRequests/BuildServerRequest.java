package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;
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
