package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Network.Message.Responses.Response;

public interface ClientManagerListener {

    public void update(Response response);
}
