package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.God.GodsPower.Power;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PumpedGod extends God {

    private final Image img;
    private ImageView imgView;

    public PumpedGod(String godName, String godDescription, Power godPower, Image img) {
        super(godName, godDescription, godPower);
        this.img = img;
        this.imgView  = new ImageView(img);
    }

    public ImageView getImg() {
        return imgView;
    }
}
