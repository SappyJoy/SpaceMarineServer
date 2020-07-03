package server;

import commands.Command;
import commands.CommandManager;
import user.User;
import utils.ValidateUser;
import utils.dao.UserDAO;
import utils.dataSource.database.Database;
import utils.dataSource.database.UserDatabase;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Server {
    private int port;
    private DatagramSocket socket;
    private CommandManager commandManager;
    private List<String> history;
    private InetAddress clientAddr;
    private int clientPort;
    private Database database;

    private final ExecutorService receiveExecutorService;
    private final ExecutorService sendExecutorService;

    public Server(int port, CommandManager commandManager, List<String> history, Database database) {
        receiveExecutorService = Executors.newCachedThreadPool();
        sendExecutorService = Executors.newFixedThreadPool(3);

        this.port = port;
        this.commandManager = commandManager;
        this.history = history;
        this.database = database;
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
        byte[] input = new byte[500000];
        DatagramPacket inputPacket = new DatagramPacket(input, input.length);

        try {
            socket.receive(inputPacket);
        } catch (IOException e) {
            System.out.println("Error in the server receive: " + e.getMessage());
            e.printStackTrace();
        }

        receiveExecutorService.execute(() -> {
            clientAddr = inputPacket.getAddress();
            clientPort = inputPacket.getPort();
        });

        new Thread(() -> process(input)).start();
    }

    private void process(byte[] input) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            User user = (User) objectInputStream.readObject();
            String answer = "";
            if (user.isRegistered()) {
                boolean checkUser = ValidateUser.checkUser(user, database);
                if (checkUser) {
                    if (!user.isEntry()) {
                        user.setId(new UserDAO(UserDatabase.getInstance()).getByLogin(user.getLogin()).getId());
                        Command cmd = (Command) objectInputStream.readObject();
                        cmd = commandManager.getCommand(cmd.getName());
                        cmd.setUser(user);
                        cmd.setParameters(objectInputStream);
                        if (!cmd.getName().equals("show")) {
                            System.out.println("Request has been received: " + cmd.getName());
                            history.add(cmd.getName());

                        }
                        answer += cmd.execute();
                    } else {
                        answer = String.valueOf(user.getId());
                    }
                } else {
                    answer = "Wrong login or password";
                }
            } else {
                answer = ValidateUser.register(user, database);
            }
            byte[] output = new byte[500000];
            ByteBuffer buffer = ByteBuffer.wrap(answer.getBytes("UTF-8"));
            output = answer.getBytes("UTF-8");


//            send(output);

            sendExecutorService.execute(() -> send(buffer.array()));
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
//            System.out.println("Reply has been sent");
        } catch (IOException e) {
            System.out.println("Error in the server send: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
