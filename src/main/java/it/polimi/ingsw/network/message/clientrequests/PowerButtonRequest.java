package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class PowerButtonRequest extends Request {

    public PowerButtonRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, RequestContent.POWER_BUTTON);
    }

}
