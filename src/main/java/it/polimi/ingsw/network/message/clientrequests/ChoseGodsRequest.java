package it.polimi.ingsw.network.message.clientrequests;

import it.polimi.ingsw.server.model.God.God;
import it.polimi.ingsw.network.message.Enum.Dispatcher;
import it.polimi.ingsw.network.message.Enum.RequestContent;

import java.util.ArrayList;

public class ChoseGodsRequest extends Request {

    private final ArrayList<God> chosenGod;

    public ChoseGodsRequest(String messageSender, ArrayList<God> chosenGod) {
        super(messageSender, Dispatcher.SETUP_GAME, RequestContent.CHOSE_GODS);
        this.chosenGod = chosenGod;
    }

    public ArrayList<God> getChosenGods() {
        return this.chosenGod;
    }
}
