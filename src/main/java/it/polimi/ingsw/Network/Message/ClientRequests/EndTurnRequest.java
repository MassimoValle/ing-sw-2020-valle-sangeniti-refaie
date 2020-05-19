package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class EndTurnRequest extends Request {

    public EndTurnRequest(String messageSender){
        super(messageSender, Dispatcher.TURN, RequestContent.END_TURN);
    }
}
