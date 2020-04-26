package it.polimi.ingsw.Model.Action;


/**
 * Every action performed by the player that alter the Model has to implements this interface
 */
public interface Action {

    /**
     * It checks if the {@link Action} is legitimate
     *
     * @return the boolean
     */
    boolean isValid();

    /**
     * Do the action.
     */
    void doAction();

}
