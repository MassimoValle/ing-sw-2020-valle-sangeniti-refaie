package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class BuildRequest extends Request {

    private final Position positionWhereToBuildOn;

    public BuildRequest(String messageSender, Position positionWhereToBuildOn) {

        super(messageSender, Dispatcher.TURN, RequestContent.BUILD);
        this.positionWhereToBuildOn = positionWhereToBuildOn;

    }

    public Position getPositionWhereToBuild() {
        return this.positionWhereToBuildOn;
    }

}
