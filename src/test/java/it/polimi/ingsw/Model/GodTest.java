package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God.God;
import it.polimi.ingsw.Model.God.GodsPower.ApolloPower;
import it.polimi.ingsw.Model.God.GodsPower.Power;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodTest {

    static God god;

    @BeforeClass
    public static void initClass() {
        god = new God("testGod", "godDescr", new ApolloPower("powerType", "powerDescr"));
    }

    @Test
    public void check() {
    }
}
