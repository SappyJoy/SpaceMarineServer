package utils.dataSource.database;

import utils.dataSource.DataSourceException;

public class UserDatabase extends Database {
    private volatile static UserDatabase database;

    private UserDatabase() throws DataSourceException {
        super("postgres", "123");
    }

    public static UserDatabase getInstance() {
        if (database == null) {
            synchronized (UserDatabase.class) {
                if (database == null) {
                    database = new UserDatabase();
                }
            }
        }
        return database;
    }
}
