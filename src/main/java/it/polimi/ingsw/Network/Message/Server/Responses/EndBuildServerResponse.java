package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndBuildServerResponse extends ServerResponse {

    public EndBuildServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_BUILD, messageStatus, gameManagerSays);
    }
}
