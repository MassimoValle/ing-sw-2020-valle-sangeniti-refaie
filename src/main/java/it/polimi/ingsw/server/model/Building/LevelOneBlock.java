package it.polimi.ingsw.server.model.Building;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A lvl1 block.
 */
public class LevelOneBlock extends Block {


    public LevelOneBlock(){
        //Doesn't need anything
    }

    public void initGUIObj(){
        imageView = new ImageView(new Image(path + "levelOneBlock.png"));
    }

}
