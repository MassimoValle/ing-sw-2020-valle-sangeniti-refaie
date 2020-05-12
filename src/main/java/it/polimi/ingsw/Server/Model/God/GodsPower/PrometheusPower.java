package it.polimi.ingsw.Server.Model.God.GodsPower;



import it.polimi.ingsw.Server.Model.Action.ActionOutcome;
import it.polimi.ingsw.Server.Model.Map.Square;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.Model.Player.Worker;

import java.io.Serializable;

public class PrometheusPower extends Power implements Serializable {

    private static boolean buildFirst;
    private static Square firstBlockBuilt;

    public PrometheusPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);

        buildFirst = false;
        firstBlockBuilt = null;
    }
    /*
    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {

            if (buildFirst){
                return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            } else if(buildFirst = true){
                      return super.build(squareWhereToMove);
            }
    }   */
}
