package it.polimi.ingsw.Server.Model.Building;

import it.polimi.ingsw.Server.Model.Player.Position;
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
