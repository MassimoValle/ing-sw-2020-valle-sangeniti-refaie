package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.message.server.ServerMessage;

/**
 * The type Message receiver.
 */
public class MessageReceiver implements Runnable {

    private final ClientManager clientManager;
    private final Client client;
    private final Thread thread;

    /**
     * Instantiates a new Message receiver.
     *
     * @param client        the client
     * @param clientManager the client manager
     */
    MessageReceiver(Client client, ClientManager clientManager) {
        this.clientManager = clientManager;
        this.client = client;
        this.thread = new Thread(this);
        this.thread.start();
    }

    /**
     * It pop a message from {@link Client} message queue and,
     * after waiting for 100 ms, pass the message to the clientManager
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (client) {

                ServerMessage message = client.getMessageFromQueue();
                try {
                    client.wait(100);
                } catch (InterruptedException e) {
                    ClientManager.LOGGER.severe(e.getMessage());
                    Thread.currentThread().interrupt();
                }

                if (message != null)
                    clientManager.takeMessage(message);

            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * start the process
     */
    public void start() {
        if (this.thread.isInterrupted()) {
            this.thread.start();
        }
    }

}
