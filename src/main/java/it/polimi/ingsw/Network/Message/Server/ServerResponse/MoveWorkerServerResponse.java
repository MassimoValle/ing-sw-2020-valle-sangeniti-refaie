package it.polimi.ingsw.Network.Message.Server.ServerResponse;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;

public class MoveWorkerServerResponse extends ServerResponse {


    public MoveWorkerServerResponse(MessageStatus messageStatus, String gameManagerSays) {
        super(ResponseContent.MOVE_WORKER, messageStatus, gameManagerSays);
    }

    public MoveWorkerServerResponse(ResponseContent responseContent,MessageStatus messageStatus, String gameManagerSays) {
        super(responseContent, messageStatus, gameManagerSays);
    }

}
