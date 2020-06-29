package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class EndBuildRequest extends Request{

    public EndBuildRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, RequestContent.END_BUILD);
    }

}
