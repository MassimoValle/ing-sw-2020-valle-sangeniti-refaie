package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class EndMoveResponse extends Response {

    public EndMoveResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, ResponseContent.END_MOVE, messageStatus, gameManagerSays);
    }

}
