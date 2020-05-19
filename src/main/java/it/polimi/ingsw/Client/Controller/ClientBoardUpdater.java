package it.polimi.ingsw.Client.Controller;

import it.polimi.ingsw.Client.Model.BabyGame;
import it.polimi.ingsw.Client.Model.CLIclientMap;
import it.polimi.ingsw.Network.Message.Server.UpdateMessage.UpdateBoardMessage;
import it.polimi.ingsw.Server.Model.Player.Position;
import it.polimi.ingsw.Server.View.Observer;

public class ClientBoardUpdater implements Observer<UpdateBoardMessage>{

        BabyGame babyGame = BabyGame.getInstance();
        CLIclientMap map = babyGame.clientMap;

        @Override
        public void update(UpdateBoardMessage message) {

            String playerName = message.getPlayerName();
            Integer worker = message.getWorkerIndex();
            Position position = message.getPosition();

            switch (message.getUpdateType()){
                case PLACE -> map.placeUpdate(playerName, worker, position);
                case MOVE -> map.moveUpdate(playerName, worker, position);
                case BUILD -> map.buildUpdate(position);
            }

        }

}
