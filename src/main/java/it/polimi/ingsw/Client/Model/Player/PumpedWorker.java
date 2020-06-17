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

        this.setImg();
        this.imgView = new ImageView(img);
    }


    private void setImg() {
        String path = "/imgs/workers/";
        switch (getOwner().getColor()){
            case RED -> path += "redWorker.png";
            case BLUE -> path += "blueWorker.png";
            case GREEN -> path += "greenWorker.png";
        }

        this.img = new Image(path);
    }

    public ImageView getImgView() {
        return imgView;
    }


    public Image getImg() {
        return img;
    }
}
