package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Dispatcher;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.MessageStatus;

public class MoveRequest extends Request {

    private Position senderMovePosition;


    public MoveRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus, Position senderMovePosition) {
        super(messageSender, messageDispatcher, MessageContent.MOVE, messageStatus);
        this.senderMovePosition = senderMovePosition;
    }

    public Position getSenderMovePosition() {
        return senderMovePosition;
    }

}
