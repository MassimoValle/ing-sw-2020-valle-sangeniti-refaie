package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class ChooseGodsServerResponse extends ServerResponse {


    public ChooseGodsServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.CHOOSE_GODS, messageStatus, gameManagerSays);
    }

}
