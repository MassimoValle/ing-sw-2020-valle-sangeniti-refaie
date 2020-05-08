package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.RequestContent;

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
