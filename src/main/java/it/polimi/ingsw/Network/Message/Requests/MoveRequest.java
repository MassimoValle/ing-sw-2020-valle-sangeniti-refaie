package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class MoveRequest extends Request {

    private final Position senderMovePosition;


    public MoveRequest(String messageSender, Position senderMovePosition) {
        super(messageSender, Dispatcher.TURN, MessageContent.MOVE);
        this.senderMovePosition = senderMovePosition;
    }

    public Position getSenderMovePosition() {
        return senderMovePosition;
    }

}
