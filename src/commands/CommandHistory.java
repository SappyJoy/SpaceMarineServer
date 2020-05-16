package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Show last 9 used commands
 */
public class CommandHistory extends Command {

    List<String> history;

    public CommandHistory(List<String> history) {
        this();
        this.history = history;
    }

    public CommandHistory() {
        this.name = "history";
        this.description = "Displays the last 9 commands (without their arguments)";
    }

    @Override
    public String execute() {
        String s = history.subList(Math.max(history.size() - 9, 0), history.size())
                .stream()
                .map(a -> a + "\n")
                .collect(Collectors.joining());
        return s;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
