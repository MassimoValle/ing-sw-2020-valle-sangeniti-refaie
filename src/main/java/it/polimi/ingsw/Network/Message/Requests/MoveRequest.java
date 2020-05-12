package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class MoveRequest extends Request {

    private final Position senderMovePosition;


    public MoveRequest(String messageSender, Position senderMovePosition) {
        super(messageSender, Dispatcher.TURN, RequestContent.MOVE);
        this.senderMovePosition = senderMovePosition;
    }

    public Position getSenderMovePosition() {
        return senderMovePosition;
    }

}
