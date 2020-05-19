package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class ShowDeckServerResponse extends ServerResponse {

    private final int howMany;

    public ShowDeckServerResponse(String messageSender, String gameManagerSays, int howMany) {
        super(ResponseContent.SHOW_DECK, MessageStatus.OK, gameManagerSays);
        this.howMany = howMany;
    }

    public int getHowMany() {
        return howMany;
    }
}
