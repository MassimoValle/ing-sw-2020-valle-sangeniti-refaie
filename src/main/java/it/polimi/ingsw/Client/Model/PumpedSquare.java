package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Server.Model.Map.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PumpedSquare extends Square {

    private Image img;
    private ImageView imgView;

    public PumpedSquare(int row, int column, Image img) {
        super(row, column);
        this.setImg(img);

        this.imgView  = new ImageView(img);
    }

    public ImageView getImg() {
        return imgView;
    }

    public void setImg(Image img) {
        this.img = img;
        this.imgView  = new ImageView(img);
    }
}
