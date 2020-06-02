package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Server.Model.Player.Worker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageDecorator extends IimageDecorator {

    private Image img;

    private ImageView imageView;

    public ImageDecorator(Object object, Image img) {
        super(object);
        this.img = img;
        this.imageView = new ImageView(img);
    }

    public void setImg(Image img) {
        this.img = img;
        setImageView(img);
    }

    private void setImageView(Image img) {
        this.imageView.setImage(img);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
