package it.polimi.ingsw.Network.Message;

import it.polimi.ingsw.Model.God.God;

public class PickGodRequest extends Request {

    private God pickedGod;

    public PickGodRequest(String messageSender, God pickedGod) {

        super(messageSender, MessageContent.PICK_GOD);
        this.pickedGod = pickedGod;

    }

    public God getPickedGod() {
        return this.pickedGod;
    }
}
