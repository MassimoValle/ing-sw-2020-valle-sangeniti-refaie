package it.polimi.ingsw.server.model.Building;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A lvl2 block.
 */
public class LevelTwoBlock extends Block {

    public LevelTwoBlock(){

    }

    public void initGUIObj(){
        imageView = new ImageView(new Image(path + "levelTwoBlock.png"));
    }

}
