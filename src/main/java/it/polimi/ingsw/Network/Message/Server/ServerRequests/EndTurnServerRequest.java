package it.polimi.ingsw.Network.Message.Server.ServerRequests;

import it.polimi.ingsw.Client.Controller.ClientManager;
import it.polimi.ingsw.Network.Message.Enum.ServerRequestContent;

public class EndTurnServerRequest extends ServerRequest {

    public EndTurnServerRequest() {
        super(ServerRequestContent.END_TURN);
    }

}
