package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class PlaceWorkerServerResponse extends ServerResponse {


    public PlaceWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.PLACE_WORKER, messageStatus, gameManagerSays);
    }

}
