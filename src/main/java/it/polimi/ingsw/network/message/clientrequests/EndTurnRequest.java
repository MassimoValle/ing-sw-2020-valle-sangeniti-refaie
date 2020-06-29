package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class EndTurnRequest extends Request {

    public EndTurnRequest(String messageSender){
        super(messageSender, Dispatcher.TURN, RequestContent.END_TURN);
    }
}
