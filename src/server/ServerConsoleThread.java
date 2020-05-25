package server;

import commands.CommandManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConsoleThread extends Thread {
    private Scanner sc;
    private CommandManager commandManager;

    public ServerConsoleThread(CommandManager commandManager) {
        this.commandManager = commandManager;
        sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            String msg = null;
            try {
                msg = sc.nextLine();
            } catch (NoSuchElementException e) {
                System.exit(0);
            }
            if (msg.equals("exit")) {
                System.out.println("Saving..");
                commandManager.getCommand("save").execute();
                System.out.println("Exiting...");
                System.exit(0);
            } else if (msg.equals("save")) {
                System.out.println("Saving...");
                commandManager.getCommand("save").execute();
            } else {
                System.out.println("There is no such command");
            }
        }
    }
}
