package it.polimi.ingsw.Network;

import it.polimi.ingsw.Network.Message.Message;
import it.polimi.ingsw.Network.Message.MessageType;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.View.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Connection extends Observable<String> implements Runnable {

    private Server server;
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private String name;

    private boolean active;

    private final Object outLock = new Object();
    private final Object inLock = new Object();


    public Connection(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
        this.active = true;

        try {
            synchronized (inLock) {
                this.in = new ObjectInputStream(socket.getInputStream());
            }

            synchronized (outLock) {
                this.out = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    private synchronized boolean isActive(){
        return active;
    }

    public void sendMessage(Message message) {
        if (isActive()) {
            try {
                synchronized (outLock) {
                    out.writeObject(message);
                    out.flush();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                closeConnection();
            }
        }
    }

    public void asyncSend(final Message message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessage(message);
            }
        }).start();
    }

    public synchronized void closeConnection(){
        if (isActive()) {
            try {
                socket.close();
            } catch (IOException e){
                System.err.println(e.getMessage());
            }

            active = false;
        }

    }

    private void close(){
        closeConnection();
        System.out.println("Deregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }

    @Override
    public void run() {
        while (isActive()) {
            try {
                synchronized (inLock) {
                    Message message = (Message) in.readObject();
                    if (message.getMessageType() == MessageType.FIRST_CONNECTION) {
                        server.login(message.getMessageSender(), this);
                    } else {
                        server.handleMessage(message);
                    }
                }

                /*
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream());
                send("Welcome! What's your name?");
                name = in.nextLine();
                server.lobby(this, name);
                while(isActive()){
                    String read = in.nextLine();
                    notify(read);
                }
                */

            } catch (IOException | ClassNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

