package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

import java.util.ArrayList;
import java.util.List;

public class ChoseGodsRequest extends Request {

    private final ArrayList<God> chosenGod;

    public ChoseGodsRequest(String messageSender, List<God> chosenGod) {
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.CHOSE_GODS);
        this.chosenGod = (ArrayList<God>) chosenGod;
    }

    public List<God> getChosenGods() {
        return this.chosenGod;
    }
}
