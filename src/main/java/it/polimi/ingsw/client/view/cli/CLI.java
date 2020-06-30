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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.File;
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
        consoleOut.println("Please, enter the SERVER IP address (just leave blank for 'localhost')" );

        String ip = consoleIn.nextLine();

        if (ip.equals(""))
            return "localhost";

        return ip;
    }

    @Override
    public String askUserName() {

        String username = "";
        consoleOut.println("Enter your username: ");

        //Setto il nome utente
        do {
            if (consoleIn.hasNextLine()) {
                username = consoleIn.nextLine();
            }
        } while (username == null);

        consoleOut.println("Invalid username, please insert a valid username: " + username);

        return username;
    }

    @Override
    public int askNumbOfPlayer() {
        int input;
        do{
            consoleOut.println("How many opponents do you want?");
            consoleOut.print("[MIN: 2, MAX: 3]: ");
            input = Integer.parseInt(consoleIn.nextLine());
        }while (input != 2 && input != 3);

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

        consoleOut.println("\nType in the respective god's index you want to play with: [ONLY INDEX WORKING FOR NOW]");

        do {
            consoleOut.println(">>>");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                if (entered.equals("")) {
                    consoleOut.println("You must select gods to start the game!");
                    break;
                }

                int n = Integer.parseInt(entered);

                if (n < 1 || n >= 15) {
                    consoleOut.println("Index not valid, please insert a valid index");
                    break;
                }

                Deck deck = BabyGame.getInstance().getDeck();
                God god = deck.getGod(n - 1);
                consoleOut.println("You choose " + god.getGodName() + "!");
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
    public void godsSelectedSuccesfully() {

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
    public void errorWhilePickinUpGod(String gameManagerSays) {

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
        consoleOut.println("Let's place your worker!\nWhere do you want to place it?\n");
        consoleOut.println("(You are placing your "+ n + "° worker)");

        int row = -1;
        int col = -1;

        do {
            consoleOut.print("row: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    row = Integer.parseInt(entered);

                    if (row < 0 || row > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( row < 0 || row > 4);

        do {
            consoleOut.print("col: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    col = Integer.parseInt(entered);

                    if (col < 0 || col > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( col < 0 || col > 4);

        return new Position(row, col);
    }


    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the worker you wanted to place" +
                SantoriniStream.GAME_MANAGER_SAYS + gameManagerSays +
                "\nPlease try place it again");
    }

    @Override
    public void workerPlacedSuccesfully() {

        consoleOut.println("Worker placed successfully!");
        consoleOut.println();
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

        }while (actionToDo < 0 || actionToDo > possibleActions.size());

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

        //consoleOut.println("These are all the possible position where you can move:");
        //nearlyPosValid.forEach(consoleOut::println);

        consoleOut.println("Let's move!");
        consoleOut.println("\n(x,y)");

        int row = -1;
        int col = -1;

        do {
            consoleOut.print("row: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    row = Integer.parseInt(entered);

                    if (row < 0 || row > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( row < 0 || row > 4);

        do {
            consoleOut.print("col: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    col = Integer.parseInt(entered);

                    if (col < 0 || col > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( col < 0 || col > 4);

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
        consoleOut.println("Do you want to move again?");
        consoleOut.println("1) sì ");
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

        return input == 1;
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

        //consoleOut.println("These are all the possible position where you can build:");
        //possiblePosToBuild.forEach(consoleOut::println);
        consoleOut.println("Let's build!");
        consoleOut.println("\n(x,y)");


        int row = -1;
        int col = -1;

        do {
            consoleOut.print("row: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    row = Integer.parseInt(entered);

                    if (row < 0 || row > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( row < 0 || row > 4);

        do {
            consoleOut.print("col: ");

            if (consoleIn.hasNextLine()) {
                String entered = consoleIn.nextLine();

                try {
                    col = Integer.parseInt(entered);

                    if (col < 0 || col > 4) {
                        consoleOut.println("You must select one of the insert a number between 0 and 4");
                    }

                } catch (NumberFormatException e) {
                    consoleOut.println(SantoriniStream.INSERT_A_NUMBER);
                }

                consoleOut.println();
            }

        }while ( col < 0 || col > 4);

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
        consoleOut.println("Do you want to build again?");
        consoleOut.println("1) sì");
        consoleOut.println("2) no ");
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

    }

    @Override
    public void anotherPlayerIsPlacingWorker(String turnOwner) {

    }

    @Override
    public void startingPlayerTurn(String turnOwner) {

        consoleOut.println("\nIt's " + turnOwner + "turn!");
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


        consoleOut.println("\n" + turnOwner + " has selected his " + workerIndex +"° worker");
        consoleOut.println();

    }

    @Override
    public void anotherPlayerHasMoved(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasBuilt(String turnOwner) {

    }

    @Override
    public void anotherPlayerHasEndedHisTurn(String turnOwner) {

    }


    @Override
    public void youWon() {
        consoleOut.println("\t#############");
        consoleOut.println("\t\tYOU WIN");
        consoleOut.println("\t#############");
    }

    @Override
    public void iLost() {
        consoleOut.println("\t#############");
        consoleOut.println("\t\tYOU LOST");
        consoleOut.println("\t#############");
    }

    @Override
    public void youLose(String winner) {
        consoleOut.println("\t#############");
        consoleOut.println("\t\tYOU LOSE");
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
        consoleOut.println(user + " left the game \nRestart the client to play again");
        consoleOut.println("\t#############");
    }

    @Override
    public void debug(ServerResponse serverResponse) {

        printMessageFromServer(serverResponse);

    }

    @Override
    public void showMap(GameMap clientMap) {
        clientMap.printBoard();
    }


    private void welcomeToSantorini() throws IOException {

        String santorini = "Welcome to Santorini! \nThe most incredible board game ever created!\n\n\nI know it could look like shit but I'm okay with that!";
        consoleOut.println(santorini);

        BufferedImage image = ImageIO.read(new File("/imgs/santorini-logo.png"));

        image.getGraphics().drawLine(1, 1, image.getWidth()-1, image.getHeight()-1);
        image.getGraphics().drawLine(1, image.getHeight()-1, image.getWidth()-1, 1);

        ImageIO.write(image, "png", new File("/imgs/santorini-logo.png"));


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

        try {
            welcomeToSantorini();
        } catch (IOException e) {
            ClientManager.LOGGER.severe(e.getMessage());
        }

        String ip = askIpAddress();


        Client client = new Client(ip, 8080, this);

        try {
            client.run();
        } catch (EOFException ex) {
            //this is thrown when the end of the stream is reached
            consoleOut.println("Server disconnected while sending message to it!");
        } catch (UnknownHostException ex) {
            consoleOut.println("Unknown host, please insert again:");
        } catch (ConnectException ex) {
            consoleOut.println("Server refused to connect");
        } catch (IOException ex) {
            consoleOut.println("Error server side");
            ClientManager.LOGGER.severe(ex.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            run();
        }
    }
}
