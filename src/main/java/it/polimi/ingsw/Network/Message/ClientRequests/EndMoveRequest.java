package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class EndMoveRequest extends Request{

    public EndMoveRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, RequestContent.END_MOVE);
    }

}
