package it.polimi.ingsw.Network.Message.Server.UpdateMessage;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Player.ColorEnum;
import javafx.scene.paint.Color;


public class UpdatePlayersMessage extends UpdateMessage{

    private God god;
    private ColorEnum color;
    private String playerName;

    public UpdatePlayersMessage(String name, God god, ColorEnum color) {
        super();
        this.god = god;
        this.color = color;
        this.playerName = name;
    }

    public ColorEnum getColor() {
        return color;
    }

    public God getGod() {
        return god;
    }

    public String getName() {
        return playerName;
    }
}
