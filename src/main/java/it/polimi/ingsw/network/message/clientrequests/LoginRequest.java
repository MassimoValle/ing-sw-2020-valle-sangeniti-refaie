package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

public class LoginRequest extends Request {
    public LoginRequest(String messageSender) {
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.LOGIN);
    }
}
