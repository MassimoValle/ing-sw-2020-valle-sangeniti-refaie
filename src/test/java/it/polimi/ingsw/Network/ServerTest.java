package it.polimi.ingsw.Network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {

    Server server = new Server();

    public ServerTest() throws IOException {
    }

    @Before
    public void setUp() throws Exception {
        server.run();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void deregisterConnection() {
    }

    @Test
    public void lobby() {
        System.out.println("ds");
    }

    @Test
    public void run() {
    }
}