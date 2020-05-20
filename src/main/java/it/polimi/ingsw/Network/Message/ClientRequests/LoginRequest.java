package it.polimi.ingsw.Network.Message.ClientRequests;

import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

public class LoginRequest extends Request {
    public LoginRequest(String messageSender) {
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.LOGIN);
    }
}
