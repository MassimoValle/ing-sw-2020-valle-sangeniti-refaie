package it.polimi.ingsw.server.model.Building;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A lvl3 block.
 */
public class LevelThreeBlock extends Block {

    public LevelThreeBlock(){

    }

    public void initGUIObj(){
        imageView = new ImageView(new Image(path + "levelThreeBlock.png"));
    }

}
