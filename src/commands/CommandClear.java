package commands;

import item.SpaceMarine;
import utils.dao.SpaceMarineDAO;
import utils.dao.UserDAO;
import utils.dataSource.database.UserDatabase;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command which clear all collection
 */
public class CommandClear extends Command {
    private Map<Integer, SpaceMarine> lhm;
    public CommandClear(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandClear() {
        this.name = "clear";
        this.description = "Clear collection";
    }

    @Override
    public String execute() {
        int count = lhm.size();
        SpaceMarineDAO dao = new SpaceMarineDAO(UserDatabase.getInstance());

        Map<Integer, SpaceMarine> copy = null;
        lock.readLock().lock();
        try {
            copy = lhm.entrySet().stream()
                    .filter(s -> s.getValue().getOwnerId() != new UserDAO(UserDatabase.getInstance()).getByLogin(user.getLogin()).getId())
                    .collect(Collectors.toMap((p) -> p.getKey(), (p) -> p.getValue()));
        } finally {
            lock.readLock().unlock();
        }

        dao.deleteAllByUser(user);

        lock.writeLock().lock();
        try {
            lhm.clear();
            lhm.putAll(copy);
        } finally {
            lock.writeLock().unlock();
        }
        return (count - lhm.size()) + " items were deleted";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) {

    }

    @Override
    public void setParameters(ObjectInputStream ois) {

    }
}
