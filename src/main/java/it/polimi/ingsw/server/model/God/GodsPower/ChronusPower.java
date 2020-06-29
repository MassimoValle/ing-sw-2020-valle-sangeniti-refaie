package it.polimi.ingsw.server.model.God.GodsPower;

import it.polimi.ingsw.server.model.Map.GameMap;

import java.io.Serializable;

public class ChronusPower extends Power implements Serializable {

    public ChronusPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

}
