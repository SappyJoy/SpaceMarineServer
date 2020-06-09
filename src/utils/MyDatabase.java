package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MyDatabase {
    Connection connection;

    public MyDatabase(String url, String user, String password) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }
}
