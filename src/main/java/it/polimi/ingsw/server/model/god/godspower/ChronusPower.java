package it.polimi.ingsw.server.model.god.godspower;

import it.polimi.ingsw.server.model.map.GameMap;

import java.io.Serializable;

public class ChronusPower extends Power implements Serializable {

    public ChronusPower(String powerType, String powerDescription, GameMap map) {
        super(powerType, powerDescription, map);
    }

}
