package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class LostServerResponse extends ServerResponse {


    public LostServerResponse(String gameManagerSays) {
        super(ResponseContent.PLAYER_HAS_LOST, MessageStatus.OK, gameManagerSays);
    }
}
