package it.polimi.ingsw.Network.Message.Server.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class BuildResponse extends Response{

    public BuildResponse(String messageSender, MessageStatus messageStatus, String gameManagerSays) {
        super(messageSender, ResponseContent.BUILD, messageStatus, gameManagerSays);
    }

}
