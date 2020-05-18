package it.polimi.ingsw.Network.Message.Responses;

import it.polimi.ingsw.Network.Message.Enum.MessageStatus;
import it.polimi.ingsw.Network.Message.Enum.ResponseContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class BuildResponse extends Response{

    private ArrayList<Position> possiblePosToBuild;

    public BuildResponse(String messageSender, String gameManagerSays) {
        super(messageSender, ResponseContent.BUILD, MessageStatus.OK, gameManagerSays);
    }

    public ArrayList<Position> getPossiblePosToBuild() {
        return possiblePosToBuild;
    }
}
