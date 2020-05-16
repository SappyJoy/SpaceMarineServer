package server;

import commands.Command;
import commands.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;

public class Server {
    private int port;
    private DatagramSocket socket;
    private CommandManager commandManager;
    private List<String> history;
    private InetAddress clientAddr;
    private int clientPort;

    public Server(int port, CommandManager commandManager, List<String> history) {
        this.port = port;
        this.commandManager = commandManager;
        this.history = history;
    }

    public void execute() {
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server is listening on port: " + port);
            new ServerConsoleThread(commandManager).start();

            while (true) {
                receive();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void receive() {
        byte[] input = new byte[10000];
        DatagramPacket inputPacket = new DatagramPacket(input, input.length);

        try {
            socket.receive(inputPacket);
        } catch (IOException e) {
            System.out.println("Error in the server receive: " + e.getMessage());
            e.printStackTrace();
        }

        clientAddr = inputPacket.getAddress();
        clientPort = inputPacket.getPort();

        process(input);
    }

    private void process(byte[] input) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command cmd = (Command) objectInputStream.readObject();
            cmd = commandManager.getCommand(cmd.getName());
            cmd.setParameters(objectInputStream);
            System.out.println("Request has been received: " + cmd.getName());

            byte[] output = new byte[10000];
            String answer = cmd.execute();
            ByteBuffer buffer = ByteBuffer.wrap(answer.getBytes("UTF-8"));
            output = answer.getBytes("UTF-8");
            history.add(cmd.getName());

//            send(output);
            send(buffer.array());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in the server process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void send(byte[] output) {
        try {
            DatagramPacket outputPacket = new DatagramPacket(output, output.length, clientAddr, clientPort);
            // System.out.println("Reply has been sent: \n" + new String(output).trim());
            socket.send(outputPacket);
            System.out.println("Reply has been sent");
        } catch (IOException e) {
            System.out.println("Error in the server send: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
