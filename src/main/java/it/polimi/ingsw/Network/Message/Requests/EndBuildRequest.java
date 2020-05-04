package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;

public class EndBuildRequest extends Request{

    public EndBuildRequest(String messageSender) {
        super(messageSender, Dispatcher.TURN, MessageContent.END_BUILD);
    }

}
