package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.God;
import it.polimi.ingsw.Model.Power;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodTest {

    God god;

    @Test
    public void check() {
        String name = "god";
        String description = "desc";

        god = new God(name, description, new Power("type", "desc"));

        assertEquals(name, god.getGodName());
        assertEquals(description, god.getGodDescription());
    }
}