package it.polimi.ingsw.Network.Message.Requests;

import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Network.Message.Enum.Dispatcher;
import it.polimi.ingsw.Network.Message.Enum.MessageContent;
import it.polimi.ingsw.Network.Message.Enum.MessageStatus;

public class BuildRequest extends Request {

    private Position positionWhereToBuildOn;

    public BuildRequest(String messageSender, Dispatcher messageDispatcher, MessageStatus messageStatus, Position positionWhereToBuildOn) {

        super(messageSender, messageDispatcher,  MessageContent.BUILD, messageStatus);
        this.positionWhereToBuildOn = positionWhereToBuildOn;

    }

    public Position getPositionWhereToBuild() {
        return this.positionWhereToBuildOn;
    }

}
