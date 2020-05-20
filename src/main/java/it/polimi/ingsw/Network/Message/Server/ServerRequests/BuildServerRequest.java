package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.util.ArrayList;

public class BuildServerRequest extends ServerRequest {

    public BuildServerRequest() {
        super(ServerRequestContent.BUILD);
    }

}
