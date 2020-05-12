package it.polimi.ingsw.Network;

import org.junit.BeforeClass;

import java.io.IOException;

public class ServerTest {

    private static Server server;

    @BeforeClass
    public static void initClass() throws IOException {
        server = new Server();
        server.run();
    }


}
