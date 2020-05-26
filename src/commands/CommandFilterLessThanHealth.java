package commands;

import item.SpaceMarine;
import utils.ValidateInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command that displays elements whose health field value is less than the specified
 */
public class CommandFilterLessThanHealth extends Command {

    Map<Integer, SpaceMarine> lhm;
    private float health;

    public CommandFilterLessThanHealth(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandFilterLessThanHealth() {
        this.name = "filter_less_than_health";
        this.description = "Syntax: <float number> Displays elements whose health field value is less than the specified";
    }

    @Override
    public String execute() {
        String s = lhm.values().stream()
                .filter(a -> a.getHealth() < health)
                .map(a -> a.toString() + "\n")
                .collect(Collectors.joining());
        if (s.equals("")) {
            return "There is no such elements\n";
        }
        return s;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        health = sc.nextFloat();
        oos.writeObject(health);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        health = (Float) ois.readObject();
    }
}
