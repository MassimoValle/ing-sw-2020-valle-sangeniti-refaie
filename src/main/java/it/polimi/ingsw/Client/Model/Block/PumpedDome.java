package it.polimi.ingsw.Client.Model.Block;

import it.polimi.ingsw.Server.Model.Building.Dome;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PumpedDome extends Dome {

    private Image img;
    private final ImageView imgView;

    private String path = "/imgs/godCards/";


    public PumpedDome() {
        this.img = new Image(path + "");
        this.imgView = new ImageView(this.img);
    }

    public ImageView getImgView() {
        return imgView;
    }

}
