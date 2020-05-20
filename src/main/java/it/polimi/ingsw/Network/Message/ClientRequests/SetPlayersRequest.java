package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class SetPlayersRequest extends Request{

    private final String howMany;

    public SetPlayersRequest(String messageSender, Dispatcher messageDispatcher, RequestContent requestContent, String howMany) {
        super(messageSender, messageDispatcher, requestContent);
        this.howMany = howMany;
    }

    public String getHowMany() {
        return howMany;
    }


}
