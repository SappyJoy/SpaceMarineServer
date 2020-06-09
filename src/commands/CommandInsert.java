package commands;

import item.SpaceMarine;
import utils.ValidateInput;
import utils.dao.SpaceMarineDAO;
import utils.dataSource.database.Database;
import utils.dataSource.database.UserDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.commands.Command adding a new item to the collection
 */
public class CommandInsert extends Command {
    private Map<Integer, SpaceMarine> lhm;
    private int key;
    private SpaceMarine sm;
    public CommandInsert(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandInsert() {
        this.name = "insert";
        this.description = "Add a new item with the given key";
    }

    @Override
    public String execute() {
        if (lhm.containsKey(key)) {
            return String.format("Item with key %d already exists\n", key);
        }
        sm.setOwnerId(user.getId());
        lock.writeLock().lock();
        try {
            lhm.put(key, sm);
        } finally {
            lock.writeLock().unlock();
        }
        new SpaceMarineDAO(UserDatabase.getInstance()).insert(key, sm);
        String result = String.format("Element with key %d has been inserted\n", key);
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        // System.out.print("Input a key: ");
        ValidateInput in = new ValidateInput(sc);
        // int key = in.validateInt();
        key = sc.nextInt();
        SpaceMarine sm = new SpaceMarine();
        sm.scan(sc);
        oos.writeObject(key);
        oos.writeObject(sm);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        key = (Integer) ois.readObject();
        sm = (SpaceMarine) ois.readObject();
    }
}
