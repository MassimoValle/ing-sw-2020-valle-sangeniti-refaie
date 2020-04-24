package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.God.God;

import java.util.ArrayList;

public class ChoseGodsRequest extends Request {

    private ArrayList<God> chosenGod;

    public ChoseGodsRequest(String messageSender, ArrayList<God> chosenGod) {
        super(messageSender, MessageContent.GODS_CHOSE);
        this.chosenGod = chosenGod;
    }

    public ArrayList<God> getChosenGod() {
        return this.chosenGod;
    }
}
