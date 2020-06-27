package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Message.Enum.RequestContent;
import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.ClientRequests.Request;
import it.polimi.ingsw.Server.View.Observable;

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

    private final Object outLock = new Object();
    private final Object inLock = new Object();


    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;

        System.out.println("Connessione stabilita con un client");

        try {
            synchronized (inLock) {
                this.socketIn = new ObjectInputStream(socket.getInputStream());
            }

            synchronized (outLock) {
                this.socketOut = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
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
                ex.printStackTrace();
                closeConnection();
            }
        }
    }


    // close Connection

    public synchronized void closeConnection(){
        if (isActive()) {
            try {
                socket.close();
            } catch (IOException e){
                e.printStackTrace();
            }

            active = false;
        }

    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        Server.deregisterConnection(this);
        System.out.println("Done!");
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
                System.err.println(e.getMessage());
                close();
            }
        }
    }


}

