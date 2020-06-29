package it.polimi.ingsw.server;

import it.polimi.ingsw.network.Server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        server.run();

    }
}
