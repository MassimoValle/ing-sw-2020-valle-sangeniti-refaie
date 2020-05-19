package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndTurnServerResponse extends ServerResponse {

    public EndTurnServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.END_TURN, messageStatus, gameManagerSays);
    }
}
