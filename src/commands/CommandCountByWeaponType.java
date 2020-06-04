package commands;

import item.SpaceMarine;
import item.Weapon;
import utils.ValidateInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.Command that displays the number of elements whose weaponType field value is equal to the specified
 */
public class CommandCountByWeaponType extends Command {

    private Map<Integer, SpaceMarine> lhm;
    private Weapon weaponType;

    public CommandCountByWeaponType(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandCountByWeaponType() {
        this.name = "count_by_weapon_type";
        this.description = "Displays the number of elements whose weaponType field value is equal to the specified";
    }

    @Override
    public String execute() {
        long ans = 0;
        ans = lhm.values().stream().filter(x -> x.getWeaponType().equals(weaponType)).count();
//        for (SpaceMarine spaceMarine : lhm.values()) {
//            if (spaceMarine.getWeaponType().equals(weaponType)) {
//                ans++;
//            }
//        }
        return (ans + " weapon of type " + weaponType + "\n");
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        weaponType = Weapon.valueOf(sc.next().toUpperCase());
        oos.writeObject(weaponType);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        weaponType = (Weapon) ois.readObject();
    }
}
