package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class PowerButtonServerResponse extends ServerResponse {
    public PowerButtonServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.POWER_BUTTON, messageStatus, gameManagerSays);
    }
}
