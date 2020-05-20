package it.polimi.ingsw.Client.View.Cli;

import it.polimi.ingsw.Client.View.ClientView;
import it.polimi.ingsw.Network.Client;
import it.polimi.ingsw.Network.Message.Server.ServerResponse.ServerResponse;
import it.polimi.ingsw.Server.Model.God.Deck;
import it.polimi.ingsw.Server.Model.God.God;
import it.polimi.ingsw.Server.Model.Map.GameMap;
import it.polimi.ingsw.Server.Model.Player.Player;
import it.polimi.ingsw.Server.Model.Player.Position;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class CLI extends ClientView {

    private final Scanner consoleIn;
    private final PrintStream consoleOut;

    public CLI(){
        consoleIn = new Scanner(System.in);
        consoleOut = new PrintStream(System.out, true);

    }


    @Override
    public void start() {

        String ip = askIpAddress();


        Client client = new Client(ip, 8080, this);

        try {
            client.run();
        } catch (IOException ex) {
            //ex.printStackTrace();
            start();
        }
    }

    @Override
    public String askIpAddress() {
        consoleOut.print("IP Address: ");

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

        consoleOut.println("YOUR USERNAME: " + username);

        return username;
    }

    @Override
    public int askNumbOfPlayer() {
        int input;
        do{
            consoleOut.print("lobby size [MIN: 2, MAX: 3]: ");
            input = Integer.parseInt(consoleIn.nextLine());
        }while (input != 2 && input != 3);

        return input;
    }

    @Override
    public void showDeck() {

        consoleOut.println("\nLet's choose the gods!");
        consoleOut.println();

        Deck deck = Deck.getInstance();

        for(int i=0; i< deck.size(); i++) {
            consoleOut.println( i+1 + deck.getGod(i).toString() + "\n");
        }
    }

    @Override
    public ArrayList<God> selectGods(int howMany) {

        ArrayList<God> godsChosen = new ArrayList<>();

        int godsChosenNum = 0;

        consoleOut.println("\ntype in the the index or the gods name you want to play with: [ONLY INDEX WORKING FOR NOW]");

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

                God god = Deck.getInstance().getGod(n - 1);
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
                "\nGame Manager says: " + gameManagerSays +
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
                    consoleOut.println("Please insert a number!");
                }

                consoleOut.println();
            }

        }while (index <= 0 || index > hand.size() );

        return hand.get(index - 1);
    }

    @Override
    public void errorWhilePickinUpGod(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the God you selected" +
                "\nGame Manager says: " + gameManagerSays +
                "\nPlease select the correct God");

    }

    @Override
    public void godPickedUpSuccessfully() {

        consoleOut.println("God selected successfully");
        consoleOut.println();

    }

    @Override
    public void showAllPlayersInGame(Set<Player> playerSet) {

        consoleOut.println("\nYou are playing against: ");
        for (Player player: playerSet) {
            consoleOut.println(player.printInfoInCLi() + "\n");
        }
        consoleOut.println();

    }

    @Override
    public Position placeWorker(String worker) {
        int n = Integer.parseInt(worker) + 1;
        consoleOut.println("Let's place your worker!\nWhere do you want to place it?\n");
        consoleOut.println("(You are placing your "+ n + "° worker)");

        consoleOut.print("row: ");
        int row = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        consoleOut.print("col: ");
        int col = Integer.parseInt(consoleIn.nextLine());
        consoleOut.println();

        return new Position(row, col);
    }


    @Override
    public void errorWhilePlacingYourWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the worker you wanted to place" +
                "\nGame Manager says: " + gameManagerSays +
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
    public int selectWorker() {
        int worker;
        do{
            consoleOut.println("\nWhich worker do you want to move?\n" +
                    "1 or 2?");

            worker = Integer.parseInt(consoleIn.nextLine());

        }while (worker != 1 && worker != 2);

        return worker - 1;
    }

    @Override
    public void workerSelectedSuccessfully() {

        consoleOut.println("\nWorker selected succesfully!\n");
    }

    @Override
    public void errorWhileSelectingWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the worker you selected" +
                "\nGame Manager says: " + gameManagerSays +
                "\nPlease select another worker");

    }

    @Override
    public Position moveWorker(ArrayList<Position> nearlyPosValid) {

        Position p;

        consoleOut.println("These are all the possible position where you can move:");
        nearlyPosValid.forEach(consoleOut::println);

        do {
            consoleOut.print("row: ");
            int row = Integer.parseInt(consoleIn.nextLine());
            consoleOut.println();

            consoleOut.print("col: ");
            int col = Integer.parseInt(consoleIn.nextLine());
            consoleOut.println();

            p = new Position(row, col);
        }while (!nearlyPosValid.contains(p));

        consoleOut.println("Moving onto (" + p.getRow() + "," + p.getColumn() + ")\n");

        return p;

    }

    @Override
    public void errorWhileMovingWorker(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the move you performed" +
                "\nGame Manager says: " + gameManagerSays +
                "\nPlease move again");

    }

    @Override
    public boolean wantMoveAgain() {
        consoleOut.println("Do you want to move again?");
        consoleOut.println("1) sì ");
        consoleOut.println("2) no \n");
        int input;

        do{
            input = Integer.parseInt(consoleIn.nextLine());
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

        Position p;

        consoleOut.println("These are all the possible position where you can build:");
        possiblePosToBuild.forEach(consoleOut::println);

        do {
            consoleOut.print("row: ");
            int row = Integer.parseInt(consoleIn.nextLine());
            consoleOut.println();

            consoleOut.print("col: ");
            int col = Integer.parseInt(consoleIn.nextLine());
            consoleOut.println();

            p = new Position(row, col);
        }while (!possiblePosToBuild.contains(p));

        consoleOut.println("building onto (" + p.getRow() + "," + p.getColumn() + ")\n");

        return p;

    }

    @Override
    public void errorWhileBuilding(String gameManagerSays) {

        consoleOut.println("\n There was a problem with the built you performed" +
                "\nGame Manager says: " + gameManagerSays +
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
        int input;

        do{
            input = Integer.parseInt(consoleIn.nextLine());
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
                "Updating board..." +
                "\n");
    }

    @Override
    public void win(boolean imWinner) {
        consoleOut.println("#############");

        if(imWinner)
            consoleOut.println("YOU WIN");
        else consoleOut.println("YOU LOSE");

        consoleOut.println("#############");
    }

    @Override
    public void debug(ServerResponse serverResponse) {

        printMessageFromServer(serverResponse);

    }

    @Override
    public void showMap(GameMap clientMap) {
        clientMap.printBoard();
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
}
