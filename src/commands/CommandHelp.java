package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command that shows information about every command that user can use
 */
public class CommandHelp extends Command {

    CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        this();
        this.commandManager = commandManager;
    }

    public CommandHelp() {
        this.name = "help";
        this.description = "Shows info about every command";
    }

    @Override
    public String execute() {
        String result = "The options are:\n";
        result += commandManager.getHm().values().stream()
                .map(s -> String.format("%-40s%-150s\n", s.getName(), s.getDescription()))
                .collect(Collectors.joining());
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(commandManager);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        commandManager = (CommandManager) ois.readObject();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
