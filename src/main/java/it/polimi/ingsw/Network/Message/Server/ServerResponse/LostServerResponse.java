package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class LostServerResponse extends ServerResponse {


    public LostServerResponse(String gameManagerSays) {
        super(ResponseContent.PLAYER_HAS_LOST, MessageStatus.OK, gameManagerSays);
    }
}
