package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.Player.Position;

public class BuildDomeRequest extends BuildRequest {

    public BuildDomeRequest(String messageSender, Position positionWhereToBuildOn) {
        super(messageSender, positionWhereToBuildOn);
    }

}
