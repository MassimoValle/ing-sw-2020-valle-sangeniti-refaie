package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class ShowDeckResponse extends Response {

    private final int howMany;

    public ShowDeckResponse(String messageSender, String gameManagerSays, int howMany) {
        super(messageSender, ResponseContent.SHOW_DECK, MessageStatus.OK, gameManagerSays);
        this.howMany = howMany;
    }

    public int getHowMany() {
        return howMany;
    }
}
