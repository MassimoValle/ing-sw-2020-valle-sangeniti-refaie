package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class AssignGodRequest extends Request {

    private final God god;

    public AssignGodRequest(String messageSender, God god){
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.PICKED_GOD);
        this.god = god;
    }


    public God getGod() {
        return god;
    }
}
