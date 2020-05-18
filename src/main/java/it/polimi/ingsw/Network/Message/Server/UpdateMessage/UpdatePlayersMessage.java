package it.polimi.ingsw.Network.Message.Server.UpdateMessage;

import it.polimi.ingsw.Server.Model.God.God;
import javafx.scene.paint.Color;


public class UpdatePlayersMessage extends UpdateMessage{

    private God god;
    private Color color;
    private String playerName;

    public UpdatePlayersMessage(String messageSender, String name, God god, Color color) {
        super(messageSender);
        this.god = god;
        this.color = color;
        this.playerName = name;
    }

    public Color getColor() {
        return color;
    }

    public God getGod() {
        return god;
    }

    public String getName() {
        return playerName;
    }
}
