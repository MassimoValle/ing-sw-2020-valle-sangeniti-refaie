package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

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
