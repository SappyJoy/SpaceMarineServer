package commands;

import item.SpaceMarine;
import utils.ValidateInput;
import utils.dao.SpaceMarineDAO;
import utils.dataSource.database.UserDatabase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.commands.Command which changes an element by given id
 */
public class CommandUpdate extends Command {
    private Map<Integer, SpaceMarine> lhm;
    private SpaceMarine sm;
    private int id;

    public CommandUpdate(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandUpdate() {
        this.name = "update";
        this.description = "Updates the value of a collection element whose id is equal to the specified";
    }

    @Override
    public String execute() {
        SpaceMarineDAO dao = new SpaceMarineDAO(UserDatabase.getInstance());
        // Необходимо найти элемент в коллекции по id
        // Здесь будет реализовано полным перебором, впоследствии возможны изменения
        for (Integer key : lhm.keySet()) {
            SpaceMarine value = lhm.get(key);
            if (value.getId() == id) {
                if (value.getOwnerId() != user.getId()) {
                    return "You can't modify items that do not belong to you";
                }
                sm.setOwnerId(user.getId());
                dao.update(key, sm);
                lock.writeLock().lock();
                try {
                    lhm.put(key, sm);
                } finally {
                    lock.writeLock().unlock();
                }
                return "Success update";
            }
        }
        return ("Not found any elements with the given id\n");
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        int id = sc.nextInt();
        SpaceMarine sm = new SpaceMarine();
        sm.scan(sc);
        sm.setId(id);
        oos.writeInt(id);
        oos.writeObject(sm);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        id = ois.readInt();
        sm = (SpaceMarine) ois.readObject();
    }
}
