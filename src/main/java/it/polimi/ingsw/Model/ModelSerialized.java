package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Map.GameMap;

import java.io.Serializable;

/**
 * This is the model that will be sent every time a request is performed by any client to update their respective view
 *
 * As of now the class has only the gameMap inside, but
 * by the end it will grow with all the information required by the single client in order to upgrade their View
 *
 */
public class ModelSerialized implements Serializable {

    public GameMap gameMap;



    public ModelSerialized() {

        this.gameMap = Game.getInstance().getGameMap();

    }



}
