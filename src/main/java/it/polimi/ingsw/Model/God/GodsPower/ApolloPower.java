package it.polimi.ingsw.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.Action;
import it.polimi.ingsw.Model.Action.MoveAction;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

public class ApolloPower extends Power {

    public ApolloPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
    public boolean move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

        //Controllo se attiva il potere di Apollo
        if (squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(activeWorker.getColor()) ) {

            Worker worker = squareWhereToMove.getWorkerOnSquare();
            Position pos = activeWorker.getWorkerPosition();


            Action moveAction = new MoveAction(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);
            moveAction.doAction();

            Action moveActionInvert = new MoveAction(worker, pos, squareWhereToMove, squareWhereTheWorkerIs);
            moveActionInvert.doAction();
        }

        //Faccio la classica moveAction
        Action moveAction = new MoveAction(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

        moveAction.doAction();

        return false;
    }

}

