package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Server.Model.Map.GameMap;

import java.io.Serializable;

public class ChronusPower extends Power implements Serializable {

    public ChronusPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

}
