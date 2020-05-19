package it.polimi.ingsw.Client.Model;

import it.polimi.ingsw.Server.Model.Map.Square;
import javafx.scene.image.Image;

public class GUImap extends CLIclientMap {

    public GUImap(){
        board = new PumpedSquare[COLUMNS][ROWS];

        String basePath = "/imgs/board/";

        for (int i=0; i<ROWS; i++) {

            String rowPath = basePath + (i+1) + "row/" + i + "x";

            for ( int j=0; j<COLUMNS; j++)  {

                String pngPath = rowPath + j + ".png";
                board[i][j] = new PumpedSquare(i,j, new Image(pngPath));

            }
        }
    }



}
