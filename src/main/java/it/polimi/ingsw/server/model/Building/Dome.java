package it.polimi.ingsw.server.model.Building;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A dome.
 */
public class Dome extends Block {


    public Dome(){
    }

    public void initGUIObj(){
        imageView = new ImageView(new Image(path + "Dome.png"));
    }



}
