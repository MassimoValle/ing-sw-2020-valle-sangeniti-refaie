package it.polimi.ingsw.Server.Model.Building;

import it.polimi.ingsw.Server.Model.Player.Position;

/**
 * Every building that can be placed in Santorini is a block
 * What kind of block? Let's see the concrete Class that extends this one.
 */
public abstract class Block {

    abstract void build(Position pos);


}
