package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class PowerButtonRequest extends Request {

    public PowerButtonRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, RequestContent.POWER_BUTTON);
    }

}
