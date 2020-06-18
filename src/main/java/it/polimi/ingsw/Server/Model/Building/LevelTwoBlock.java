package it.polimi.ingsw.Server.Model.Building;

import it.polimi.ingsw.Server.Model.Player.Position;
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
