package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;

public class MoveRequest extends Request {

    private Position senderMovePosition;


    public MoveRequest(String messageSender, Position senderMovePosition) {
        super(messageSender, MessageContent.MOVE);
        this.senderMovePosition = senderMovePosition;
    }

    public Position getSenderMovePosition() {
        return senderMovePosition;
    }

}
