package it.polimi.ingsw.server.model.Building;

import javafx.scene.image.ImageView;

/**
 * Every building that can be placed in Santorini is a block
 * What kind of block? Let's see the concrete Class that extends this one.
 */
public abstract class Block {

    protected ImageView imageView;
    protected String path = "/imgs/blocks/";

    public ImageView getImageView() {
        return imageView;
    }

    public void initGUIObj(){}

}
