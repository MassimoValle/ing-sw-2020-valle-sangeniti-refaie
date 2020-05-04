package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

import java.util.ArrayList;

public class ChoseGodsRequest extends Request {

    private final ArrayList<God> chosenGod;

    public ChoseGodsRequest(String messageSender, MessageStatus messageStatus, ArrayList<God> chosenGod) {
        super(messageSender, Dispatcher.SETUP_GAME, MessageContent.GODS_CHOSE, messageStatus);
        this.chosenGod = chosenGod;
    }

    public ArrayList<God> getChosenGod() {
        return this.chosenGod;
    }
}
