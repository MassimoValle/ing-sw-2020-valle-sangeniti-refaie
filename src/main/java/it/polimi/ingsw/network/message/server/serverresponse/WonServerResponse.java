package it.polimi.ingsw.network.message.server.serverresponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class WonServerResponse extends ServerResponse {

    public WonServerResponse(String gameManagerSays) {
        super(ResponseContent.PLAYER_HAS_WON, MessageStatus.OK, gameManagerSays);
    }

}
