package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class WonServerResponse extends ServerResponse {

    public WonServerResponse(String gameManagerSays) {
        super(ResponseContent.PLAYER_WON, MessageStatus.OK, gameManagerSays);
    }

}
