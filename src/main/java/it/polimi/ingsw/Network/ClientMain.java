package it.polimi.ingsw.Network;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) {

        Client client1 = new Client("localhost", 8080);
        try {
            client1.run();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
