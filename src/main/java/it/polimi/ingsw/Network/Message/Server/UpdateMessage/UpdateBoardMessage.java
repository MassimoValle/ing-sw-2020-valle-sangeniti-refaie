package it.polimi.ingsw.Network.Message.Server.UpdateMessage;

import it.polimi.ingsw.Network.Message.Enum.UpdateType;
import it.polimi.ingsw.Server.Model.Player.Position;

public class UpdateBoardMessage extends UpdateMessage {

    private final UpdateType updateType;
    private final Position position;
    private final Integer workerIndex;
    private final String playerName; //the player who did the move
    private final boolean domePresent;

    public UpdateBoardMessage(String playerName, UpdateType updateType, Position position, Integer workerIndex, boolean domePresent){
        super();
        this.playerName = playerName;
        this.updateType = updateType;
        this.position = position;
        this.workerIndex = workerIndex;
        this.domePresent = domePresent;
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

    public String getPlayerName() {
        return playerName;
    }

    public boolean isDomePresent() {
        return domePresent;
    }

    @Override
    public void execute() {
        super.getClientManager().boardUpdate(this);
    }
}
