package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class AssignGodRequest extends Request {

    private God god;

    public AssignGodRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus, God god){
        super(messageSender, messageDispatcher, MessageContent.PICK_GOD, messageStatus);
        this.god = god;
    }


    public God getGod() {
        return god;
    }
}
