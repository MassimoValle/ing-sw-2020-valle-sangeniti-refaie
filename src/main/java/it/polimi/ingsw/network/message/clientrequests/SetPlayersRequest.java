package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

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
