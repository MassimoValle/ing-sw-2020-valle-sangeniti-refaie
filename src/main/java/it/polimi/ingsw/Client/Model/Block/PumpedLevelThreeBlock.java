package it.polimi.ingsw.Client.Model.Block;

import it.polimi.ingsw.Server.Model.Building.LevelThreeBlock;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PumpedLevelThreeBlock extends LevelThreeBlock {

    private Image img;
    private ImageView imgView;

    private String path = "/imgs/godCards/";


    public PumpedLevelThreeBlock() {
        this.img = new Image(path + "");
        this.imgView = new ImageView(this.img);
    }

    public ImageView getImgView() {
        return imgView;
    }

}
