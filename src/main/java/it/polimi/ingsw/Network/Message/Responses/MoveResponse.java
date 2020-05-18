package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class MoveResponse extends Response{

    private ArrayList<Position> nearlyPositions;

    public MoveResponse(String messageSender, String gameManagerSays, ArrayList<Position> nearlyPositions) {
        super(messageSender, ResponseContent.MOVE_WORKER, MessageStatus.OK, gameManagerSays);
        this.nearlyPositions = nearlyPositions;
    }

    public ArrayList<Position> getNearlyPositions() {
        return nearlyPositions;
    }
}
