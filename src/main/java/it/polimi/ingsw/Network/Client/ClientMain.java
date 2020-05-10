package it.polimi.ingsw.Network.Client;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {

        Client client1 = new Client("localhost", 8080);

        /*Scanner in = new Scanner(System.in);
        boolean doIt = true;

        while (doIt) {
            System.out.println("CLI or GUI?");

            switch (in.nextLine().toUpperCase()) {
                case "CLI":
                    CLI cli = new CLI(client1);
                    doIt = false;
                    break;
                case "GUI": // GUI gui = new GUI(client1); break;
                    doIt = false;

            }
        }*/
        try {
            client1.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
