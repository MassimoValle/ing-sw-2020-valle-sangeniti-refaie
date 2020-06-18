package it.polimi.ingsw.Server.Model.Building;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A lvl1 block.
 */
public class LevelOneBlock extends Block {


    public LevelOneBlock(){

    }

    public void initGUIObj(){
        imageView = new ImageView(new Image(path + "levelOneBlock.png"));
    }

}
