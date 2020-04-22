package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.Player.Position;

public class MoveRequest extends Request {

    private Position senderMovePosition;


    public MoveRequest(String messageSender, String message, Position senderMovePosition) {
        super(messageSender, message);
        this.senderMovePosition = senderMovePosition;
    }

    public Position getSenderMovePosition() {
        return senderMovePosition;
    }

}
