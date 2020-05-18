package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class MoveWorkerResponse extends Response{


    public MoveWorkerResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, ResponseContent.MOVE_WORKER, messageStatus, gameManagerSays);
    }

}
