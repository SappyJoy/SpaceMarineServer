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