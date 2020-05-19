package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class WonResponse extends Response{

    public WonResponse(String messageSender, String gameManagerSays) {
        super(messageSender, ResponseContent.PLAYER_WON, MessageStatus.OK, gameManagerSays);
    }

}
