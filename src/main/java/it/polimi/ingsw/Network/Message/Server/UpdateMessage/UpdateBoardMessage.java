package it.polimi.ingsw.Network.Message.Server.UpdateMessage;

import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Server.Model.Player.Position;

public class UpdateBoardMessage extends UpdateMessage {

    private final UpdateType updateType;
    private final Position position;
    private final Integer workerIndex;

    public UpdateBoardMessage(String player, UpdateType updateType, Position position, Integer workerIndex){
        super(player);
        this.updateType = updateType;
        this.position = position;
        this.workerIndex = workerIndex;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public Position getPosition() {
        return position;
    }

    public Integer getWorkerIndex() {
        return workerIndex;
    }
}
