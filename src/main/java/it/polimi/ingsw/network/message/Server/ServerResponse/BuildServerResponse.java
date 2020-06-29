package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class BuildServerResponse extends ServerResponse {

    public BuildServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.BUILD, messageStatus, gameManagerSays);
    }

    public BuildServerResponse(ResponseContent responseContent, MessageStatus messageStatus, String gameManagerSays) {
        super(responseContent, messageStatus, gameManagerSays);
    }

}
