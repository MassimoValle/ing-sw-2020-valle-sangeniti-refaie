package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class BuildRequest extends Request {

    private Worker workerToUseToBuild;
    private Position positionWhereToBuildOn;

    public BuildRequest(String messageSender, Worker workerToUseToBuild, Position positionWhereToBuildOn) {
        super(messageSender, MessageContent.BUILD);
        this.workerToUseToBuild = workerToUseToBuild;
        this.positionWhereToBuildOn = positionWhereToBuildOn;
    }

    public Position getPositionWhereToBuild() {
        return this.positionWhereToBuildOn;
    }

}
