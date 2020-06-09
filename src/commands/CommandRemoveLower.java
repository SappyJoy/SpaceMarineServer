package commands;

import item.SpaceMarine;
import utils.dao.SpaceMarineDAO;
import utils.dao.UserDAO;
import utils.dataSource.database.UserDatabase;

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
        int count = lhm.size();
        SpaceMarineDAO dao = new SpaceMarineDAO(UserDatabase.getInstance());

        Map<Integer, SpaceMarine> copy = lhm.entrySet().stream()
                .filter(s -> ((s.getValue().compareTo(spaceMarine)) >= 0 ||
                        (s.getValue().getOwnerId() != new UserDAO(UserDatabase.getInstance()).getByLogin(user.getLogin()).getId())))
                .collect(Collectors.toMap((p) -> p.getKey(), (p) -> p.getValue()));
        lhm.entrySet().stream()
                .filter(s -> ((s.getValue().compareTo(spaceMarine)) < 0 &&
                        (s.getValue().getOwnerId() == new UserDAO(UserDatabase.getInstance()).getByLogin(user.getLogin()).getId())))
                .forEach(s -> dao.delete(s.getValue()));
        lock.writeLock().lock();
        try {
            lhm.clear();
            lhm.putAll(copy);
        } finally {
            lock.writeLock().unlock();
        }
        return "Removed " + (count - lhm.size()) + " items\n";
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
