package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command that removes from the collection all items that exceed the specified
 */
public class CommandRemoveGreater extends Command {

    Map<Integer, SpaceMarine> lhm;
    private SpaceMarine spaceMarine;

    public CommandRemoveGreater(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandRemoveGreater() {
        this.name = "remove_greater";
        this.description = "Removes from the collection all items that greater than the specified";
    }

    @Override
    public String execute() {
        Map<Integer, SpaceMarine> copy = lhm.entrySet().stream()
                .filter(s -> s.getValue().compareTo(spaceMarine) <= 0)
                .collect(Collectors.toMap((p) -> p.getKey(), (p) -> p.getValue()));
        lhm.clear();
        lhm.putAll(copy);
        return "Removed\n";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        SpaceMarine spaceMarine = new SpaceMarine();
        spaceMarine.scan(sc);
        oos.writeObject(spaceMarine);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        spaceMarine = (SpaceMarine) ois.readObject();
    }
}
