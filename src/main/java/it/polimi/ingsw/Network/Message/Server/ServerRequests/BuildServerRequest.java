package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Network.Message.Server.ServerRequest;
import it.polimi.ingsw.Network.Message.Server.ServerRequestContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class BuildServerRequest extends ServerRequest {

    private ArrayList<Position> possiblePlaceToBuildOn;

    public BuildServerRequest(ArrayList<Position> possiblePlaceToBuildOn) {
        super(ServerRequestContent.BUILD);
        this.possiblePlaceToBuildOn = possiblePlaceToBuildOn;
    }

    public ArrayList<Position> getPossiblePlaceToBuildOn() {
        return possiblePlaceToBuildOn;
    }
}
