package it.polimi.ingsw.network.message.Server.ServerResponse;

import it.polimi.ingsw.network.message.Enum.MessageStatus;
import it.polimi.ingsw.network.message.Enum.ResponseContent;

public class EndTurnServerResponse extends ServerResponse {

    public EndTurnServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_TURN, messageStatus, gameManagerSays);
    }
}
