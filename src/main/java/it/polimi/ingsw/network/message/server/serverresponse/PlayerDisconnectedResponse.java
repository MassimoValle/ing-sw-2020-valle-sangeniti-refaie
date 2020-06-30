package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class PlayerDisconnectedResponse extends ServerResponse {

    public PlayerDisconnectedResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.DISCONNECT, messageStatus, gameManagerSays);
    }

}
