package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Dispatcher;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageContent;
import it.polimi.ingsw.Network.Message.MessageStatus;

public class EndRequest extends Request {

    public EndRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus){
        super(messageSender, messageDispatcher, MessageContent.END_OF_TURN, messageStatus);
    }
}
