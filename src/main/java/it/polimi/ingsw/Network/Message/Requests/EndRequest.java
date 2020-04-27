package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class EndRequest extends Request {

    public EndRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus){
        super(messageSender, messageDispatcher, MessageContent.END_OF_TURN, messageStatus);
    }
}
