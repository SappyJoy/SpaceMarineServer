package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command that prints the values of the chapter field of all elements in ascending order
 */
public class CommandPrintFieldAscendingChapter extends Command {

    Map<Integer, SpaceMarine> lhm;

    public CommandPrintFieldAscendingChapter(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandPrintFieldAscendingChapter() {
        this.name = "print_field_ascending_chapter";
        this.description = "Print the values of the chapter field of all elements in ascending order";
    }

    @Override
    public String execute() {
        lock.readLock().lock();
        String s;
        try {
            s = lhm.values().stream()
                    .sorted((t1, t2) -> t1.getChapter().compareTo(t2.getChapter()))
                    .map(t -> t.getChapter().toString() + "\n")
                    .collect(Collectors.joining());
        } finally {
            lock.readLock().unlock();
        }
        if (s.equals(""))
            return "Collection is empty\n";
        return s;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
