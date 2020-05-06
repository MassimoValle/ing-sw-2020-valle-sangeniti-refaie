package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Map.Square;

import java.io.Serializable;

public class DemeterPower extends Power implements Serializable {

    public DemeterPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public boolean[] build(Square squareWhereToBuild) {
        return super.build(squareWhereToBuild);
    }
}
