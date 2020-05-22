package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Server.Model.Player.Position;

public class BuildDomeRequest extends BuildRequest {

    public BuildDomeRequest(String messageSender, Position positionWhereToBuildOn) {
        super(messageSender, positionWhereToBuildOn);
    }

}
