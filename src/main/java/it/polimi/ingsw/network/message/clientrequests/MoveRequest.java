package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.player.Position;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

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
