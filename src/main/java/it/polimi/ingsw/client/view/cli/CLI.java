package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.PossibleClientAction;
import it.polimi.ingsw.client.model.BabyGame;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.message.server.serverresponse.SelectWorkerServerResponse;
import it.polimi.ingsw.network.message.server.serverresponse.ServerResponse;
import it.polimi.ingsw.server.model.god.Deck;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.map.GameMap;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Position;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CLI extends ClientView {

    private final Scanner consoleIn;
    private final PrintStream consoleOut;

    public CLI(){
        consoleIn = new Scanner(System.in);
        consoleOut = new SantoriniStream();

    }


    @Override
    public String askIpAddress() {
        consoleOut.println("\nLet's get started!\n");
        consoleOut.print("Please, enter the SERVER IP address (just leave blank for 'localhost'): " );

        String ip = consoleIn.nextLine();

        if (ip.equals(""))
            return "localhost";

        return ip;
    }

    @Override
    public String askUserName() {

        String username = "";

        //Setto il nome utente
        do {
            consoleOut.print("\nEnter your username (use only letters & numbers) ");
            consoleOut.print(">>>");

            if (consoleIn.hasNextLine()) {
                username = consoleIn.nextLine();
            }

            if (username.equals(""))
                consoleOut.println("Please insert a valid username!");


        } while (!username.matches("[a-zA-Z0-9]+"));

        return username;
    }

    @Override
    public void loginError() {

        consoleOut.println("\nYou cannot choose this username!");
        consoleOut.println();

    }

    @Override
    public void loginSuccessful() {
        consoleOut.println("\nYou are now logged in!");
        consoleOut.println();
    }

    @Override
    public int askNumbOfPlayer() {
        int input = 0;

        /*
        do{
            consoleOut.println("How many opponents do you want?");
            consoleOut.print("[MIN: 2, MAX: 3]: ");
            input = Integer.parseInt(consoleIn.nextLine());
        }while (input != 2 && input != 3);
         */

        do {
            consoleOut.println("How many opponents do you want?");
            consoleOut.print("[MIN: 2, MAX: 3]: ");
            consoleOut.print(">>>");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    input = Integer.parseInt(entered);

                    if (input < 2 || input > 3) {
                        consoleOut.println("You can create a 2 or 3-players lobby");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while (input < 2 || input > 3);

        return input;
    }

    @Override
    public void youAreNotTheGodLikePlayer(String godLikePlayer) {

        consoleOut.println("\n " + godLikePlayer + " is the Chosen One.");
        consoleOut.println("Please wait while he is choosing the gods");
        consoleOut.println();

    }

    @Override
    public void youAreTheGodLikePlayer() {

        consoleOut.println("\nYou are the Chosen One");
        consoleOut.println("\nPlease select the gods");
        consoleOut.println();

    }

    @Override
    public void showDeck() {

        consoleOut.println("\nLet's choose the gods!");
        consoleOut.println();

        Deck deck = BabyGame.getInstance().getDeck();

        for(int i=0; i< deck.size(); i++) {
            consoleOut.println( i+1 + deck.getGod(i).toString() + "\n");
        }
    }

    @Override
    public ArrayList<God> selectGods(int howMany) {

        ArrayList<God> godsChosen = new ArrayList<>();

        int godsChosenNum = 0;

        consoleOut.print("\nType in the respective god's index you want to play with:");

        do {
            consoleOut.print("\n>>>");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                if (entered.equals("")) {
                    consoleOut.println("You must select gods to start the game!");
                    break;
                }

                int n;
                try {
                    n = Integer.parseInt(entered);
                }catch (NumberFormatException e){
                    break;
                }


                if (n < 1 || n >= 15) {
                    consoleOut.println("Index not valid, please insert a valid index");
                    break;
                }

                Deck deck = BabyGame.getInstance().getDeck();
                God god = deck.getGod(n - 1);
                consoleOut.println("\nYou choose " + god.getGodName() + "!");
                godsChosen.add(god);
                godsChosenNum++;
            }


        } while (godsChosenNum != howMany);

        return godsChosen;
    }

    @Override
    public void errorWhileChoosingGods(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the Gods you selected" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease select the correct Gods");

    }

    @Override
    public void godsSelectedSuccessfully() {

        consoleOut.println("Gods selected successfully");
        consoleOut.println();

    }

    @Override
    public God pickFromChosenGods(ArrayList<God> hand) {

        for( int i=0; i< hand.size(); i++) {
            int n = i+1;
            consoleOut.println( "\n" + n + hand.get(i).toString());
        }

        int index = 0;

        do {
            consoleOut.println("Pick up yor God:");
            consoleOut.println(">>>");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    index = Integer.parseInt(entered);

                    if (index < 1 || index > hand.size()) {
                        consoleOut.println("You must select one of the available gods");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while (index <= 0 || index > hand.size() );

        return hand.get(index - 1);
    }

    @Override
    public void errorWhilePickingUpGod(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the God you selected" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease select the correct God");

    }

    @Override
    public void godPickedUpSuccessfully() {

        consoleOut.println("God picked successfully");
        consoleOut.println();

    }

    @Override
    public void showAllPlayersInGame(Set<Player> playerSet, Player me) {

        consoleOut.println("Players in game:");
        for (Player player: playerSet) {
            consoleOut.println(player.printInfoInCLi() + "");
        }
        consoleOut.println();

    }

    @Override
    public Position placeWorker(String worker) {
        int n = Integer.parseInt(worker) + 1;
        String nString = n == 1 ? "1st" : "2nd";
        consoleOut.println("Let's place your worker!\nWhere do you want to place it?\n");
        consoleOut.println("(You are placing your "+ nString + " worker)");

        int row;
        int col;

        row = getCoordinate("row");

        col = getCoordinate("col");

        return new Position(row, col);
    }

    private int getCoordinate(String s){

        int par = -1;
        do {
            consoleOut.print(s + ": ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    par = Integer.parseInt(entered);

                    if (par < 0 || par > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( par < 0 || par > 4);

        return par;
    }


    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the worker you wanted to place" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease try place it again");
    }

    @Override
    public void workerPlacedSuccessfully() {

        consoleOut.println("Worker placed successfully!");
        consoleOut.println();
    }

    @Override
    public void placingPhaseEnding() {

    }


    @Override
    public void startingTurn() {

        consoleOut.println("It's your turn!");
        consoleOut.println();
    }

    @Override
    public int selectWorker(List<Position> workersPositions) {
        int worker = -1;
        do{
            consoleOut.println("\nWhich worker do you want to move?\n" +
                    "1 ("
                            + workersPositions.get(0).getRow() + "," +
                            workersPositions.get(0).getColumn() + ")\n" +
                    "2 ("
                    + workersPositions.get(1).getRow() + "," +
                    workersPositions.get(1).getColumn() + ")\n"
            );

            String entered = consoleIn.nextLine();

            try {
                worker = Integer.parseInt(entered);

                if (worker != 1 && worker != 2) {
                    consoleOut.println("You must select one of the worker gods");
                }

            } catch (NumberFormatException e) {
                consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
            }

        }while (worker != 1 && worker != 2);

        return worker - 1;
    }

    @Override
    public void workerSelectedSuccessfully() {

        consoleOut.println("\nWorker selected succesfully!\n");
    }

    @Override
    public PossibleClientAction choseActionToPerform(List<PossibleClientAction> possibleActions) {

        consoleOut.println("\nHere it is what you can do:");

        int i = 1;
        for (PossibleClientAction possibleClientAction : possibleActions) {
            consoleOut.println( i +" " + possibleClientAction + "\n");
            i++;
        }

        consoleOut.println(">>>");

        int actionToDo = -1;
        do{

            String entered = consoleIn.nextLine();

            try {
                actionToDo = Integer.parseInt(entered);

                if (actionToDo < 0 || actionToDo > possibleActions.size()) {
                    consoleOut.println("Please select an action");
                }

            } catch (NumberFormatException e) {
                consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
            }

        }while (actionToDo < 1 || actionToDo > possibleActions.size());

        return possibleActions.get(actionToDo - 1);

    }


    @Override
    public void errorWhileActivatingPower(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the power you want to use" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease do a different action");

    }

    @Override
    public void powerActivated(God god) {

        consoleOut.println("Power activated: ");
        consoleOut.println("Now you can: ");
        consoleOut.println(god.getGodPower().getPowerDescription());
        consoleOut.println();

    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the worker you selected" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease select another worker");

    }

    @Override
    public Position moveWorker(List<Position> nearlyPosValid) {

        Position p;

        consoleOut.println("Let's move!");
        consoleOut.println("\n(x,y)");

        int row;
        int col;

        row = getCoordinate("row");

        col = getCoordinate("col");

        p = new Position(row, col);

        consoleOut.println("Moving onto (" + p.getRow() + "," + p.getColumn() + ")\n");

        return p;

    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the move you performed" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease move again");

    }

    @Override
    public boolean wantMoveAgain() {

        int input = doItAgain("move");

        return input == 1;
    }

    private int doItAgain(String string){
        consoleOut.println("Do you want to " + string + " again?");
        consoleOut.println("1) yes ");
        consoleOut.println("2) no \n");
        int input = -1;

        do{
            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    input = Integer.parseInt(entered);

                    if (input != 1 && input != 2) {
                        consoleOut.println("You have to choose between this 2 options");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }
        }while (input != 1 && input != 2);

        return input;
    }

    @Override
    public void workerMovedSuccessfully() {

        consoleOut.println("Worker moved successfully!");
        consoleOut.println();

    }

    @Override
    public void printCanMoveAgain(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }

    @Override
    public void endMoveRequestError(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }

    @Override
    public void endMovingPhase(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }



    @Override
    public Position build(ArrayList<Position> possiblePosToBuild) {

        consoleOut.println("Let's build!");
        consoleOut.println("\n(x,y)");


        int row;
        int col;

        row = getCoordinate("row");

        col = getCoordinate("col");

        Position p = new Position(row, col);

        consoleOut.println("building onto (" + p.getRow() + "," + p.getColumn() + ")\n");

        return p;

    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the built you performed" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease build again");

    }

    @Override
    public void builtSuccessfully() {

        consoleOut.println("Built successfully!");
        consoleOut.println();
    }


    @Override
    public boolean wantBuildAgain() {
        int input = doItAgain("build");

        return input == 1;
    }

    @Override
    public void printCanBuildAgain(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }

    @Override
    public void endBuildRequestError(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }

    @Override
    public void endBuildingPhase(String gameManagerSays) {

        consoleOut.println(gameManagerSays);
        consoleOut.println();
    }

    @Override
    public void endTurn() {

        consoleOut.println("\nEnding turn!");
    }

    @Override
    public void someoneElseDoingStuff() {

        consoleOut.println("It's not your turn\n" +
                //"Updating board..." +
                "\n");
    }

    @Override
    public void anotherPlayerIsPickingUpGod(String turnOwner) {
        //do nothing
    }

    @Override
    public void anotherPlayerIsPlacingWorker(String turnOwner) {
        //do nothing
    }

    @Override
    public void startingPlayerTurn(String turnOwner) {

        consoleOut.println("\nIt's " + turnOwner + " turn!");
        consoleOut.println();
    }

    @Override
    public void anotherPlayerIsSelectingWorker(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " is selecting his worker...");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerIsMoving(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " is moving his worker...");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerIsBuilding(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " is building with his worker...");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasSelectedGods(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " has chosen the Gods!");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasPickedUpGod(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " picked up his God!");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasPlacedWorker(String turnOwner) {

        consoleOut.println("\n" + turnOwner + " has placed his worker");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasSelectedWorker(SelectWorkerServerResponse serverResponse) {
        String turnOwner = serverResponse.getMessageRecipient();
        int workerIndex = serverResponse.getWorkerSelected();
        String nString = workerIndex == 1 ? "1st" : "2nd";

        consoleOut.println("\n" + turnOwner + " has selected his " + nString +" worker");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasMoved(String turnOwner) {
        //do nothing
    }

    @Override
    public void anotherPlayerHasBuilt(String turnOwner) {
        //do nothing
    }

    @Override
    public void anotherPlayerHasEndedHisTurn(String turnOwner) {
        //do nothing
    }


    @Override
    public void youWin() {
        consoleOut.println("\t#############");
        consoleOut.println("\t   YOU WIN");
        consoleOut.println("\t#############");
    }

    @Override
    public void iLose() {
        consoleOut.println("\t#############");
        consoleOut.println("\t   YOU LOST");
        consoleOut.println("\t#############");
    }

    @Override
    public void youLose(String winner) {
        consoleOut.println("\t#############");
        consoleOut.println("\t   YOU LOSE");
        consoleOut.println("\t#############");
        consoleOut.println("Restart the client to play again");
    }

    @Override
    public void someoneHasLost(String loser) {
        consoleOut.println("#############");
        consoleOut.println(loser + " cannot complete a full round, he is out of the game");
        consoleOut.println("\t#############");

    }

    @Override
    public void playerLeftTheGame(String user) {

        consoleOut.println("#############");
        consoleOut.println(user + " left the game \n");
        consoleOut.println("\t#############");

    }

    @Override
    public void closeClient() {
        consoleOut.println("\nRestart the client to play again\nPress any key to terminate");
        consoleIn.nextLine();
        Thread.currentThread().interrupt();

    }

    @Override
    public void debug(ServerResponse serverResponse) {

        printMessageFromServer(serverResponse);

    }

    @Override
    public void showMap(GameMap clientMap) {
        clientMap.printBoard();
    }


    private void welcomeToSantorini() {

        String santorini = "Welcome to Santorini! \nThe most incredible board game ever created!\n\n";
        consoleOut.println(santorini);

    }

    // test
    private void printMessageFromServer(ServerResponse message){
        String out = "#### [SERVER] ####\n";
        out += "Message content: " + message.getResponseContent() + "\n";
        out += "Message status: " + message.getMessageStatus() + "\n";
        out += "Message value: " + message.getGameManagerSays() + "\n";
        out += "________________\n";

        consoleOut.println(out);
    }

    @Override
    public void run() {

        welcomeToSantorini();

        String ip = askIpAddress();


        Client client = new Client(ip, 8080, this);

        try {
            client.run();
        } catch (EOFException ex) {
            //this is thrown when the end of the stream is reached
            consoleOut.println("Server disconnected while sending message to it!");
        } catch (UnknownHostException ex) {
            consoleOut.println("Unknown host! Restarting cli...\n\n\n");
            run();
        } catch (ConnectException ex) {
            consoleOut.println("Server refused to connect");
        } catch (IOException ex) {
            consoleOut.println("Error server side");
            ClientManager.LOGGER.severe(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
