package it.polimi.ingsw.Network;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();

    }
}
