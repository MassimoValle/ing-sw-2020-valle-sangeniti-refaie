package it.polimi.ingsw.server.model.action;


import it.polimi.ingsw.server.model.Map.GameMap;

/**
 * Every action performed by the player that alter the Model has to implements this interface
 */
public interface Action {

    /**
     * It checks if the {@link Action} is legitimate
     *
     * @return the boolean
     */
    boolean isValid(GameMap map);

    /**
     * Do the action.
     */
    void doAction();

}
