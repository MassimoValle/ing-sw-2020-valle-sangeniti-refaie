package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class EndBuildServerResponse extends ServerResponse {

    public EndBuildServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_BUILD, messageStatus, gameManagerSays);
    }
}
