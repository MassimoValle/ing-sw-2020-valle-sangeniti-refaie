package it.polimi.ingsw.Model.Action;

import it.polimi.ingsw.Model.Player.Worker;


/**
 * The Select worker action mark the Worker as SELECTED
 */
public class SelectWorkerAction implements Action{

    private Worker workerToSelect;

    public SelectWorkerAction(Worker workerToSelect) {
        this.workerToSelect = workerToSelect;
    }

    /**
     *  It doesn't really has to check for nothing here
     *
     * @return always true
     */
    @Override
    public boolean isValid() {

        return true;

    }

    /**
     *  Set the {@link Worker#isSelected()} true
     *
     * @return always true
     */
    @Override
    public void doAction() {

        this.workerToSelect.setSelected(true);

    }
}
