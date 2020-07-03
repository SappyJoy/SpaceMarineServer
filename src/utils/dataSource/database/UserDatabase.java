package utils.dataSource.database;

import utils.dataSource.DataSourceException;

public class UserDatabase extends Database {
    private volatile static UserDatabase database;

    private UserDatabase(String login, String password) throws DataSourceException {
        super(login, password);
    }

    public static UserDatabase getInstance() {
        if (database == null) {
            throw new DatabaseException("Database is not created", new Exception());
        }
        return database;
    }

    public static synchronized void setDatabase(String login, String password) {
        database = new UserDatabase(login, password);
    }
}
