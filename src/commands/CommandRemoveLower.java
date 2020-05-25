package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command which removes from the collection all elements smaller than the specified
 */
public class CommandRemoveLower extends Command {

    Map<Integer, SpaceMarine> lhm;
    private SpaceMarine spaceMarine;

    public CommandRemoveLower(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandRemoveLower() {
        this.name = "remove_lower";
        this.description = "Removes from the collection all elements smaller than the specified";
    }

    @Override
    public String execute() {
        Map<Integer, SpaceMarine> copy = lhm.entrySet().stream()
                .filter(s -> s.getValue().compareTo(spaceMarine) >= 0)
                .collect(Collectors.toMap((p) -> p.getKey(), (p) -> p.getValue()));
        lhm.clear();
        lhm.putAll(copy);
        return "Removed";
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
