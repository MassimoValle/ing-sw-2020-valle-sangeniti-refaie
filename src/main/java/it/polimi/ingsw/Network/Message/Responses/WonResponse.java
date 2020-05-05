package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class WonResponse extends Response{

    public WonResponse(String messageSender, String gameManagerSays) {
        super(messageSender, MessageContent.PLAYER_WON, MessageStatus.OK, gameManagerSays);
    }

}
