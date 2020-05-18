package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndBuildResponse extends Response {

    public EndBuildResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, ResponseContent.END_BUILD, messageStatus, gameManagerSays);
    }
}
