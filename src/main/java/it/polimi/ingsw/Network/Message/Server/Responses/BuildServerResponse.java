package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class BuildServerResponse extends ServerResponse {

    public BuildServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.BUILD, messageStatus, gameManagerSays);
    }

}
