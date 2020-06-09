package utils.dataSource.database;

import utils.dataSource.DataSourceException;

public class DatabaseException extends DataSourceException {

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
