package it.polimi.ingsw.Server;

import it.polimi.ingsw.Network.Server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();

    }
}
