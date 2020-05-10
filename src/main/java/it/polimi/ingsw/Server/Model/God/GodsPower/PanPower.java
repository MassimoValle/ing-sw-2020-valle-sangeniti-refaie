package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.ActionOutcome;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.io.Serializable;

public class PanPower extends Power implements Serializable {

    private static boolean hasGoneDownMoreThan2Levels;


    public PanPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
    }

    @Override
        public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        hasGoneDownMoreThan2Levels = false;

            int a = squareWhereTheWorkerIs.getHeight();
            int b = squareWhereToMove.getHeight();
            if ((b - a) > 1) {
                hasGoneDownMoreThan2Levels = true;
            }
            return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

    }


}
