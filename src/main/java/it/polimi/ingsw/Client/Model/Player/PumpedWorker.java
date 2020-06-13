package it.polimi.ingsw.Client.Model.Player;

import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PumpedWorker extends Worker {

    private Image img;
    private final ImageView imgView;

    public PumpedWorker(Player owner, int workersNumber, Image img) {
        super(owner, workersNumber);

        this.setImg(img);
        this.imgView = new ImageView(img);
    }


    public void setImg(Image img) {
        this.img = img;
    }

    public ImageView getImgView() {
        return imgView;
    }


    public Image getImg() {
        return img;
    }
}
