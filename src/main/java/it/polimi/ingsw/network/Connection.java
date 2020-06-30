package it.polimi.ingsw.network;

import it.polimi.ingsw.network.message.Enum.RequestContent;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.clientrequests.Request;
import it.polimi.ingsw.server.view.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Observable<Message> implements Runnable {

    private final Server server;
    private final Socket socket;

    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    private boolean active;
    private String name;

    private final Object outLock = new Object();
    private final Object inLock = new Object();


    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;

        Server.LOGGER.info("Connessione stabilita con un client");

        try {
            synchronized (inLock) {
                this.socketIn = new ObjectInputStream(socket.getInputStream());
            }

            synchronized (outLock) {
                this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            Server.LOGGER.severe(e.getMessage());
        }

    }





    private synchronized boolean isActive(){
        return active;
    }



    // send Message

    public void sendMessage(Message message) {
        if (isActive()) {
            try {
                synchronized (outLock) {
                    socketOut.writeObject(message);
                    socketOut.flush();
                }
            } catch (IOException ex) {
                Server.LOGGER.severe(ex.getMessage());
                closeConnection();
                Thread.currentThread().interrupt();
            }
        }
    }


    // close Connection

    public synchronized void closeConnection(){
        if (isActive()) {
            try {
                socket.close();
            } catch (IOException e){
                Server.LOGGER.severe(e.getMessage());
            }

            active = false;
        }

    }

    private void close(){
        closeConnection();
        Server.LOGGER.info("Deregistering client...");

        server.clientConnectionException(this);

        Thread.currentThread().interrupt();
        Server.LOGGER.info("Done!");
    }



    @Override
    public void run() {
        while (isActive()) {
            try {
                synchronized (inLock) {
                    Request message = (Request) socketIn.readObject();

                    if(message.getRequestContent() != RequestContent.LOGIN ||
                        message.getRequestContent() != RequestContent.NUM_PLAYER)
                        notify(message);


                    server.handleMessage(message, this);
                }
            } catch (IOException | ClassNotFoundException e) {
                Server.LOGGER.severe(e.getMessage());

                close();
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

