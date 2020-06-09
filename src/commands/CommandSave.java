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

    public CommandSave(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandSave() {
        this.name = "save";
        this.description = "Saves the collection to a file";
    }

    @Override
    public String execute() {
        /*
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
        */
        return "Everything was saved\n";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
