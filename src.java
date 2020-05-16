Coordinates.java

package item;

import java.io.Serializable;

/**
 *Class which represents coordinates
 */
public class Coordinates implements Cloneable, Serializable {
    private long x; //Поле не может быть null
    private long y; //Максимальное значение поля: 254
    public Coordinates(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates clone() throws CloneNotSupportedException {
        Coordinates cloned = (Coordinates) super.clone();
        return cloned;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\t\"x\":" + x +
                ", \n\t\t\"y\":" + y +
                "\n\t}";
    }
}


MeleeWeapon.java

package item;

import java.io.Serializable;

/**
 * Class which represents types of melee weapons
 */
public enum MeleeWeapon implements Serializable {
    CHAIN_SWORD,
    CHAIN_AXE,
    POWER_BLADE;
}


SpaceMarine.java

package item;

import utils.ValidateInput;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Scanner;

/**
 * Class that represents space marine - element of collection
 */
public class SpaceMarine implements Cloneable, Comparable<SpaceMarine>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private static int curId = 0;

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float health; //Значение поля должно быть больше 0
    private boolean loyal;
    private Weapon weaponType = null; //Поле может быть null
    private MeleeWeapon meleeWeapon = null; //Поле может быть null
    private Chapter chapter; //Поле не может быть null

    public SpaceMarine(String name, Coordinates coordinates, float health, boolean loyal, Weapon weaponType,
                       MeleeWeapon meleeWeapon, Chapter chapter) {
        this();
        this.id = ++curId;
        this.name = name;
        this.coordinates = coordinates;
        this.health = health;
        this.loyal = loyal;
        this.weaponType = weaponType;
        this.meleeWeapon = meleeWeapon;
        this.chapter = chapter;
    }

    public SpaceMarine(int id, String name, Coordinates coordinates, java.time.LocalDateTime creationDate, float health,
                       boolean loyal, Weapon weaponType, MeleeWeapon meleeWeapon, Chapter chapter) {
        this(name, coordinates, health, loyal, weaponType, meleeWeapon, chapter);
        this.id = id;
        this.creationDate = creationDate;
    }

    public SpaceMarine() {
        creationDate = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getHealth() {
        return health;
    }

    public Weapon getWeaponType() {
        return weaponType;
    }

    public Chapter getChapter() {
        return chapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return Float.compare(that.health, health) == 0 &&
                loyal == that.loyal &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                weaponType == that.weaponType &&
                meleeWeapon == that.meleeWeapon &&
                Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, creationDate, health, loyal, weaponType, meleeWeapon, chapter);
    }

    /**
     * Scans element from input stream with invitation to enter
     * @param sc
     */
    public void scan(Scanner sc) {
        ValidateInput in = new ValidateInput(sc);

        name = sc.next();
        id = ++curId;
        long x = in.validateLong();
        long y = in.validateLong();
        coordinates = new Coordinates(x, y);
        health = in.validateFloat();
        loyal = in.validateBool();
        weaponType = in.validateWeapon();
        meleeWeapon = in.validateMeleeWeapon();
        String nameChapter = sc.next();
        int count = in.validatePositiveInt();
        String nameWorld = sc.next();
        chapter = new Chapter(nameChapter, count, nameWorld);
    }


    @Override
    public String toString() {
        return "\"SpaceMarine\":{" +
                "\n\t\"id\":" + id +
                ", \n\t\"name\":\"" + name + '\"' +
                ", \n\t\"coordinates\":" + coordinates +
                ", \n\t\"creationDate\":\"" + creationDate +
                "\", \n\t\"health\":" + health +
                ", \n\t\"loyal\":" + loyal +
                ", \n\t\"weaponType\":\"" + weaponType +
                "\", \n\t\"meleeWeapon\":\"" + meleeWeapon +
                "\", \n\t\"chapter\":" + chapter +
                '}';
    }

    @Override
    public int compareTo(SpaceMarine spaceMarine) {
        return name.compareTo(spaceMarine.name);
    }
}



Chapter.java

package item;

import java.io.Serializable;

/**
 * SpaceMarine.Chapter is a simple class which represents chapter
 */
public class Chapter implements Comparable<Chapter>, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private int marinesCount; //Значение поля должно быть больше 0, Максимальное значение поля: 1000
    private String world; //Поле не может быть null

    public Chapter(String name, int marinesCount, String world) {
        this.name = name;
        this.marinesCount = marinesCount;
        this.world = world;
    }

    @Override
    public String toString() {
        return "{" +
                "\n\t\t\"name\":\"" + name +
                "\", \n\t\t\"marinesCount\":" + marinesCount +
                ", \n\t\t\"world\":\"" + world +
                "\"\n\t}\n";
    }

    @Override
    public int compareTo(Chapter chapter) {
        if (world.equals(chapter.world)) {
            return name.compareTo(chapter.name);
        } else {
            return world.compareTo(chapter.world);
        }
    }
}


Weapon.java

package item;

import java.io.Serializable;

/**
 * Class which represents weapon types
 */
public enum Weapon implements Serializable {
    PLASMA_GUN,
    COMBI_PLASMA_GUN,
    FLAMER,
    INFERNO_PISTOL,
    HEAVY_FLAMER;
}


ServerConsoleThread.java

package server;

import commands.CommandManager;

import java.util.Scanner;

public class ServerConsoleThread extends Thread {
    private Scanner sc;
    private CommandManager commandManager;

    public ServerConsoleThread(CommandManager commandManager) {
        this.commandManager = commandManager;
        sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            String msg = sc.nextLine();
            if (msg.equals("exit")) {
                System.out.println("Saving..");
                commandManager.getCommand("save").execute();
                System.out.println("Exiting...");
                System.exit(0);
            } else if (msg.equals("save")) {
                System.out.println("Saving...");
                commandManager.getCommand("save").execute();
            } else {
                System.out.println("There is no such command");
            }
        }
    }
}


Server.java

package server;

import commands.Command;
import commands.CommandManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.List;

public class Server {
    private int port;
    private DatagramSocket socket;
    private CommandManager commandManager;
    private List<String> history;
    private InetAddress clientAddr;
    private int clientPort;

    public Server(int port, CommandManager commandManager, List<String> history) {
        this.port = port;
        this.commandManager = commandManager;
        this.history = history;
    }

    public void execute() {
        try {
            socket = new DatagramSocket(port);
            System.out.println("Server is listening on port: " + port);
            new ServerConsoleThread(commandManager).start();

            while (true) {
                receive();
            }
        } catch (IOException e) {
            System.out.println("Error in the server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void receive() {
        byte[] input = new byte[10000];
        DatagramPacket inputPacket = new DatagramPacket(input, input.length);

        try {
            socket.receive(inputPacket);
        } catch (IOException e) {
            System.out.println("Error in the server receive: " + e.getMessage());
            e.printStackTrace();
        }

        clientAddr = inputPacket.getAddress();
        clientPort = inputPacket.getPort();

        process(input);
    }

    private void process(byte[] input) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Command cmd = (Command) objectInputStream.readObject();
            cmd = commandManager.getCommand(cmd.getName());
            cmd.setParameters(objectInputStream);
            System.out.println("Request has been received: " + cmd.getName());

            byte[] output = new byte[10000];
            String answer = cmd.execute();
            ByteBuffer buffer = ByteBuffer.wrap(answer.getBytes("UTF-8"));
            output = answer.getBytes("UTF-8");
            history.add(cmd.getName());

//            send(output);
            send(buffer.array());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in the server process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void send(byte[] output) {
        try {
            DatagramPacket outputPacket = new DatagramPacket(output, output.length, clientAddr, clientPort);
            // System.out.println("Reply has been sent: \n" + new String(output).trim());
            socket.send(outputPacket);
            System.out.println("Reply has been sent");
        } catch (IOException e) {
            System.out.println("Error in the server send: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


WorkWithJson.java

package utils;

import item.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

/**
 * A simple json scanner
 */
public class WorkWithJson {
    File file;
    InputStreamReader isr;
    String str;
    LinkedHashMap<Integer, SpaceMarine> lhm;

    public WorkWithJson(File file) throws IOException {
        this.file = file;
        isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
        char[] data = new char[(int) file.length()];
        isr.read(data);
        isr.close();

        str = new String(data);
        lhm = new LinkedHashMap<>();
        makeMapFromJsonString(str);
    }

    public LinkedHashMap<Integer, SpaceMarine> getLhm() {
        return lhm;
    }

    private void makeMapFromJsonString(String str) {
        if (!str.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONObject(str);
                JSONArray mapArray = jsonObject.getJSONArray("array");
                for (int i = 0; i < mapArray.length(); i++) {
                    String jsonSMString = mapArray.get(i).toString();
                    JSONObject jsonKeyValue = new JSONObject(jsonSMString);
                    int key = jsonKeyValue.getInt("key");

                    jsonSMString = jsonKeyValue.get("SpaceMarine").toString();
                    JSONObject jsonSM = new JSONObject(jsonSMString);

                    int id = jsonSM.getInt("id");
                    String name = jsonSM.getString("name");

                    String jsonSMCoordinatesString = jsonSM.get("coordinates").toString();
                    JSONObject jsonCoordinates = new JSONObject(jsonSMCoordinatesString);
                    int x = jsonCoordinates.getInt("x");
                    int y = jsonCoordinates.getInt("y");
                    Coordinates coordinates = new Coordinates(x, y);

                    java.time.LocalDateTime creationDate = LocalDateTime.parse(jsonSM.get("creationDate").toString());

                    float health = jsonSM.getFloat("health");

                    boolean loyal = jsonSM.getBoolean("loyal");

                    Weapon weaponType = Weapon.valueOf(jsonSM.getString("weaponType"));

                    MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(jsonSM.getString("meleeWeapon"));

                    String jsonSMChapterString = jsonSM.get("chapter").toString();
                    JSONObject jsonChapter = new JSONObject(jsonSMChapterString);
                    String nameChapter = jsonChapter.getString("name");
                    int marinesCount = jsonChapter.getInt("marinesCount");
                    String world = jsonChapter.getString("world");
                    Chapter chapter = new Chapter(nameChapter, marinesCount, world);

                    SpaceMarine spaceMarine = new SpaceMarine(id, name, coordinates, creationDate, health, loyal, weaponType, meleeWeapon, chapter);

                    lhm.put(key, spaceMarine);
                }
            } catch (Exception e) {
                System.out.println("Wrong json file");
                System.exit(0);
            }
        }
    }
}


ValidateInput.java

package utils;

import item.MeleeWeapon;
import item.Weapon;

import java.util.Scanner;

public class ValidateInput {

    private Scanner sc;

    public ValidateInput(Scanner sc) {
        this.sc = sc;
    }

    public int validatePositiveInt() {
        int number;
        do {
            number = validateInt();
        } while (number < 0);
        return number;
    }

    public int validateInt() {
        while (!sc.hasNextInt()) {
            printNotValid(sc);
        }
        return sc.nextInt();
    }

    public float validateFloat() {
        while (!sc.hasNextFloat()) {
            printNotValid(sc);
        }
        return sc.nextFloat();
    }

    public long validateLong() {
        while (!sc.hasNextLong()) {
            printNotValid(sc);
        }
        return sc.nextLong();
    }

    public boolean validateBool() {
        boolean result = false;
        while (sc.hasNext()) {
            String s = sc.next();
            if (s.equals("true") || s.equals("false")) {
                result = Boolean.parseBoolean(s);
                break;
            }
            System.out.printf("\"%s\" is not a valid input.\n", s);
        }
        return result;
    }

    public Weapon validateWeapon() {
        boolean check = false;
        Weapon weapon = null;
        do {
            String name = sc.next();
            for (Weapon value : Weapon.values()) {
                if (value.toString().equals(name.toUpperCase())) {
                    check = true;
                    weapon = Weapon.valueOf(name.toUpperCase());
                    return weapon;
                }
            }
            System.out.println("There is no such weapon");
        } while (true);
    }

    public MeleeWeapon validateMeleeWeapon() {
        boolean check = false;
        MeleeWeapon weapon = null;
        do {
            String name = sc.next();
            for (MeleeWeapon value : MeleeWeapon.values()) {
                if (value.toString().equals(name.toUpperCase())) {
                    check = true;
                    weapon = MeleeWeapon.valueOf(name.toUpperCase());
                    return weapon;
                }
            }
            System.out.println("There is no such weapon");
        } while (true);
    }

    private static void printNotValid(Scanner sc) {
        String input = sc.next();
        System.out.printf("\"%s\" is not a valid input.\n", input);
    }
}


CommandPrintFieldAscendingChapter.java

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
        String s = lhm.values().stream()
                .sorted((t1, t2) -> t1.getChapter().compareTo(t2.getChapter()))
                .map(t -> t.getChapter().toString() + "\n")
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


CommandManager.java

package commands;

import java.io.Serializable;
import java.util.HashMap;

/**
 * SpaceMarine.commands.CommandManager executes a command corresponding to the input
 */
public class CommandManager implements Serializable {

    HashMap<String, Command> hm = new HashMap<>();

    public void addCommand(String name, Command cmd) {
        hm.put(name, cmd);
    }

    public Command getCommand(String name) {
        return hm.get(name);
    }

    public HashMap<String, Command> getHm() {
        return hm;
    }
}


CommandExit.java

package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Complete the entire program
 */
public class CommandExit extends Command {

    public CommandExit() {
        this.name = "exit";
        this.description = "Terminate the program (without saving to a file)";
    }

    public String execute() {
        System.exit(0);
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}


CommandCountByWeaponType.java

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
 * SpaceMarine.commands.Command that displays the number of elements whose weaponType field value is equal to the specified
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
        long ans = lhm.values().stream()
                .filter(s -> s.getWeaponType()
                .equals(weaponType))
                .count();
        return (ans + " weapon of type " + weaponType);
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        ValidateInput vi = new ValidateInput(sc);
        weaponType = vi.validateWeapon();
        oos.writeObject(weaponType);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        weaponType = (Weapon) ois.readObject();
    }
}


CommandHelp.java

package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * SpaceMarine.commands.Command that shows information about every command that user can use
 */
public class CommandHelp extends Command {

    CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        this();
        this.commandManager = commandManager;
    }

    public CommandHelp() {
        this.name = "help";
        this.description = "Shows info about every command";
    }

    @Override
    public String execute() {
        String result = "The options are:\n";
        result += commandManager.getHm().values().stream()
                .map(s -> String.format("%-40s%-150s\n", s.getName(), s.getDescription()))
                .collect(Collectors.joining());
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(commandManager);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        commandManager = (CommandManager) ois.readObject();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}


CommandSave.java

package commands;

import item.SpaceMarine;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

/**
 * Saves the collection to a json-file
 */
public class CommandSave extends Command {
    Map<Integer, SpaceMarine> lhm;
    File file;

    public CommandSave(Map<Integer, SpaceMarine> lhm, File file) {
        this();
        this.lhm = lhm;
        this.file = file;
    }

    public CommandSave() {
        this.name = "save";
        this.description = "Saves the collection to a file";
    }

    @Override
    public String execute() {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            return ("File not found\n");
        }
        pw.flush();
        if (!lhm.isEmpty()) {
            pw.write("{\"array\":[");
            int j = 0;
            for (int i : lhm.keySet()) {
                pw.write("{\"key\":" + i + "," + lhm.get(i) + "}");
                if (j != lhm.size()-1)
                    pw.write(",\n");
                else
                    pw.write("]}");
                j++;
            }
        }
        pw.close();
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}


Executable.java

package commands;

import java.util.Scanner;

/**
 * This interface imposes an execute method on the objects
 * of each class that implements it
 */
public interface Executable {
    String execute();
}


CommandRemoveLower.java

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
        return "";
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


CommandInsert.java

package commands;

import item.SpaceMarine;
import utils.ValidateInput;

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
        lhm.put(key, sm);
        String result = String.format("Element with key %d has been inserted\n", key);
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        // System.out.print("Input a key: ");
        ValidateInput in = new ValidateInput(sc);
        int key = in.validateInt();
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


CommandFilterLessThanHealth.java

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
        this.description = "Displays elements whose health field value is less than the specified";
    }

    @Override
    public String execute() {
        String s = lhm.values().stream()
                .filter(a -> a.getHealth() < health)
                .map(a -> a.toString() + "\n")
                .collect(Collectors.joining());
        return s;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ValidateInput(sc).validateFloat());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        health = (Float) ois.readObject();
    }
}


CommandRemoveGreater.java

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
        return "";
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


Command.java

package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;

/**
 * Abstract class for executable commands
 */
abstract public class Command implements Executable, Serializable {
    String name;
    String description;
    public abstract String execute();
    public abstract void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException;
    public abstract void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException;
    public String getName() {
        return name;
    }
    String getDescription() {
        return description;
    }
}


CommandInfo.java

package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.format;

/**
 * Shows info about collection
 */
public class CommandInfo extends Command {

    Map<Integer, SpaceMarine> lhm;

    public CommandInfo(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandInfo() {
        this.name = "info";
        this.description = "Display information about your collection";
    }

    @Override
    public String execute() {
        String typeOfCollection = lhm.getClass().getSimpleName();
        java.time.LocalDateTime creationDate = LocalDateTime.MAX;
        for (SpaceMarine spaceMarine : lhm.values()) {
            if (creationDate.compareTo(spaceMarine.getCreationDate()) > 0)
                creationDate = spaceMarine.getCreationDate();
        }
        int sizeOfCollection = lhm.size();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String formattedCreationDate = creationDate.format(formatter);

        String result = String.format("Type of collection: %s\nInitialization date: %s\nSize of collection: %d\n",
                typeOfCollection, formattedCreationDate, sizeOfCollection);
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}


CommandHistory.java

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


CommandUpdate.java

package commands;

import item.SpaceMarine;
import utils.ValidateInput;

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
        // Необходимо найти элемент в коллекции по id
        // Здесь будет реализовано полным перебором, впоследствии возможны изменения
        for (Integer key : lhm.keySet()) {
            SpaceMarine value = lhm.get(key);
            if (value.getId() == id) {
                lhm.put(key, sm);
                return "";
            }
        }
        return ("Not found any elements with the given id\n");
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        int id = new ValidateInput(sc).validateInt();
        SpaceMarine sm = new SpaceMarine();
        sm.scan(sc);
        sm.setId(id);
        oos.writeObject(id);
        oos.writeObject(sm);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        id = ois.readInt();
        sm = (SpaceMarine) ois.readObject();
    }
}


CommandExecuteScript.java

package commands;

import item.SpaceMarine;

import java.io.*;
import java.util.*;

/**
 * SpaceMarine.commands.Command to execute commands from a file
 */
public class CommandExecuteScript extends Command {

    Map<Integer, SpaceMarine> lhm;
    String fileName;
    CommandManager commandManager;
    Set<String> executedFiles;

    public CommandExecuteScript(Map<Integer, SpaceMarine> lhm, CommandManager commandManager) {
        this();
        this.lhm = lhm;
        this.commandManager = commandManager;
        this.executedFiles = new HashSet<>();
    }

    public CommandExecuteScript() {
        this.name = "execute_script";
        this.description = "Reads and executes the script from the specified file" +
                " The script contains commands in " +
                "the same form in which they are entered by the user interactively.";
    }

    @Override
    public String execute() {
        String result = "";

        List<String> history = new LinkedList<>();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            return ("File not found");
        }
        Scanner in = new Scanner(new InputStreamReader(fileInputStream));
        // Создать новый файл, если имя файла указано некорректно запросить повторить ввод
        while (in.hasNextLine()) {
            // !!!!! Если присутствует комманда execute_script, проверить не ссылается ли она на тот же файл
            String name = "";
            while (in.hasNextLine()) {
                try {
                    name = in.next();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input");
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
            Command cmd = commandManager.getCommand(name);

            try {
                byte[] output = new byte[10000];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                cmd.readParameters(in, objectOutputStream);
                objectOutputStream.flush();
                output = byteArrayOutputStream.toByteArray();

                if (name.equals("execute_script")) {
                    if (executedFiles.contains(fileName)) {
                        continue;
                    } else {
                        executedFiles.add(fileName);
                    }
                }

                ByteArrayInputStream bais = new ByteArrayInputStream(output);
                ObjectInputStream ois = new ObjectInputStream(bais);
                cmd.setParameters(ois);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            result += cmd.execute();
            history.add(name);
        }

        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(sc.next());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        fileName = (String) ois.readObject();
    }

    public void setExecutedFiles(Set<String> executedFiles) {
        this.executedFiles = executedFiles;
    }

    private static String makePath(String fileName) {
        return  fileName + "";
    }
}


CommandRemoveKey.java

package commands;

import item.SpaceMarine;
import utils.ValidateInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.commands.Command which removes an item from the collection by its key
 */
public class CommandRemoveKey extends Command {
    /**
     * Removes an item from the collection by its key
     */
    private Map<Integer, SpaceMarine> lhm;
    private int removeKey;

    public CommandRemoveKey(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandRemoveKey() {
        this.name = "remove_key key";
        this.description = "Removes an item from the collection by its key";
    }

    @Override
    public String execute() {
        for (Integer key : lhm.keySet()) {
            if (key.equals(removeKey)) {
                lhm.remove(removeKey);
                return ("Element has been removed\n");
            }
        }
        return String.format("Key %d not found\n", removeKey);
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ValidateInput(sc).validateInt());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        removeKey = (Integer) ois.readObject();
    }
}


CommandClear.java

package commands;

import item.SpaceMarine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

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
        lhm.clear();
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) {

    }

    @Override
    public void setParameters(ObjectInputStream ois) {

    }
}


CommandShow.java

package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * When calling this command, displays the entire collection in the console
 */
public class CommandShow extends Command {
    private Map<Integer, SpaceMarine> lhm;
    public CommandShow(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandShow() {
        this.name = "show";
        this.description = "Outputs to the standard output stream all the elements of the collection in a string representation";
    }

    @Override
    public String execute() {
        String s = lhm.values().stream()
                .sorted(Comparator.comparing(SpaceMarine::getName))
                .map(a -> a.toString() + "\n")
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


Main.java

import commands.*;
import item.SpaceMarine;
import server.Server;
import utils.WorkWithJson;

import java.io.*;
import java.util.*;

/**
 *  Class that runs the program. It's responsible for reading commands
 * @author Stepan Ponomaryov
 * @version 1.0 2020-03-12
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length < 1) {
            System.out.println("Syntax: java Main <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        File file = new File("data.json");

        LinkedHashMap<Integer, SpaceMarine> lhm;
        // добавить элементы из файла в lhm
        WorkWithJson workWIthJson = new WorkWithJson(file);
        lhm = workWIthJson.getLhm();

        List<String> history = new LinkedList<>();
        CommandManager commandManager = new CommandManager();
        addCommands(commandManager, lhm, file, history);

        Server server = new Server(port, commandManager, history);
        server.execute();
    }


    private static void addCommands(CommandManager commandManager, LinkedHashMap<Integer, SpaceMarine> lhm, File file,
                                    List<String> history) {
        commandManager.addCommand("insert", new CommandInsert(lhm));
        commandManager.addCommand("show", new CommandShow(lhm));
        commandManager.addCommand("update", new CommandUpdate(lhm));
        commandManager.addCommand("remove_key", new CommandRemoveKey(lhm));
        commandManager.addCommand("clear", new CommandClear(lhm));
        commandManager.addCommand("save", new CommandSave(lhm, file));
        commandManager.addCommand("exit", new CommandExit());
        commandManager.addCommand("remove_greater", new CommandRemoveGreater(lhm));
        commandManager.addCommand("remove_lower", new CommandRemoveLower(lhm));
        commandManager.addCommand("history", new CommandHistory(history));
        commandManager.addCommand("count_by_weapon_type", new CommandCountByWeaponType(lhm));
        commandManager.addCommand("filter_less_than_health", new CommandFilterLessThanHealth(lhm));
        commandManager.addCommand("print_field_ascending_chapter", new CommandPrintFieldAscendingChapter(lhm));
        commandManager.addCommand("info", new CommandInfo(lhm));
        commandManager.addCommand("help", new CommandHelp(commandManager));
        commandManager.addCommand("execute_script", new CommandExecuteScript(lhm, commandManager));
    }


    private static File checkFile(String[] args) {
        if (args.length <= 0) {
            System.out.println("You should enter name of file in main arguments");
            System.exit(0);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("There is no such file");
            System.exit(0);
        }
        if (!file.canWrite() || !file.canRead()) {
            System.out.println("You have not got enough permissions to this file");
            System.exit(0);
        }
        return file;
    }
}


