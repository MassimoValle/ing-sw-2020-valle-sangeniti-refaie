package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;

public class EndMoveRequest extends Request{

    public EndMoveRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, MessageContent.END_MOVE);
    }

}
