package it.polimi.ingsw.Server.Model.God.GodsPower;

import it.polimi.ingsw.Model.Action.ActionOutcome;
import it.polimi.ingsw.Model.Map.Square;
import it.polimi.ingsw.Model.Player.Position;
import it.polimi.ingsw.Model.Player.Worker;

import java.io.Serializable;

public class MinotaurPower extends Power implements Serializable {

    private static Position actualPosition;
    private static Square backWardSquare;

    public MinotaurPower(String powerType, String powerDescription) {
        super(powerType, powerDescription);
        Square backWardSquare = null;

    }

    @Override
    public ActionOutcome move(Worker activeWorker, Position positionWhereToMove, Square squareWhereTheWorkerIs, Square squareWhereToMove) {
        actualPosition = squareWhereTheWorkerIs.getPosition();


        if(squareWhereToMove.hasWorkerOn() && !squareWhereToMove.getWorkerOnSquare().getColor().equals(activeWorker.getColor()) ){

            //salvo il worker avversario
            Worker oppWorker = squareWhereToMove.getWorkerOnSquare();
            squareWhereToMove.freeSquare();
            Position newPos = backWardSquare.getPosition();
            //sposto worker che ha minotauro
            super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);

            //sposto worker avversario
            super.move(oppWorker, newPos, squareWhereToMove, backWardSquare);

            return ActionOutcome.DONE;

        }return super.move(activeWorker, positionWhereToMove, squareWhereTheWorkerIs, squareWhereToMove);




    }

    private void findBackWardSquare(Position actualPosition, Position positionWhereToMove){

       int x1 = actualPosition.getRow();
       int x2 = positionWhereToMove.getRow();
       int y1 = actualPosition.getColumn();
       int y2 = positionWhereToMove.getColumn();
       int x3 = backWardSquare.getRow();
       int y3 = backWardSquare.getColumn();



        if (x1 == x2){
            if (y1 < y2){
                    x3 = x2;
                    y3 = y2 + 1;
            }else {         //y1 > y2 non ha senso vedere quando sono uguali.
                    x3 = x2;
                    y3 = y2 - 1;
            }
        }else if (x1 < x2) {
            if (y1 < y2) {
                    x3 = x2 + 1;
                    y3 = y2 + 1;
            }else if (y1 == y2){
                    x3 = x2 + 1;
                    y3 = y2;
            }else {
                    x3 = x2 + 1;
                    y3 = y2 - 1;
            }
        }else {
                if(y1 < y2){
                    x3 = x2 - 1;
                    y3 = y2 + 1;
                }else if (y1 == y2){
                    x3 = x2 -1;
                    y3 = y2;
                }else {
                    x3 = x2 - 1;
                    y3 = y2 - 1;
                }
        }
    }


    }


