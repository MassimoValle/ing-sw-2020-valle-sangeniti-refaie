package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class EndTurnRequest extends Request {

    public EndTurnRequest(String messageSender){
        super(messageSender, Dispatcher.TURN, MessageContent.END_TURN);
    }
}
