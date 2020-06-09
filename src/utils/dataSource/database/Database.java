package utils.dataSource.database;

import utils.dao.DAOException;
import utils.dataSource.DataSource;
import utils.dataSource.DataSourceException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database extends DataSource {
    private final String CREATE_IF_NOT_EXISTS_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS USERS (" +
                    "ID SERIAL NOT NULL PRIMARY KEY," +
                    "LOGIN VARCHAR UNIQUE NOT NULL," +
                    "PASSWORD VARCHAR NOT NULL," +
                    "EMAIL VARCHAR NOT NULL)";
    private final String CREATE_IF_NOT_EXISTS_SPACE_MARINES_TABLE =
            "CREATE TABLE IF NOT EXISTS SPACE_MARINES (" +
                    "ID SERIAL NOT NULL PRIMARY KEY," +
                    "OWNER_ID SERIAL NOT NULL," +
                    "KEY SERIAL UNIQUE NOT NULL," +
                    "NAME VARCHAR NOT NULL CHECK(LENGTH(NAME)>0)," +
                    "COORDINATES_X REAL NOT NULL," +
                    "COORDINATES_Y REAL NOT NULL," +
                    "CREATION_DATE DATE NOT NULL," +
                    "HEALTH FLOAT NOT NULL CHECK(HEALTH>0)," +
                    "LOYAL BOOLEAN NOT NULL," +
                    "WEAPON_TYPE VARCHAR NOT NULL CHECK(LENGTH(WEAPON_TYPE)>0)," +
                    "MELEE_WEAPON VARCHAR NOT NULL CHECK(LENGTH(MELEE_WEAPON)>0)," +
                    "CHAPTER_NAME VARCHAR NOT NULL CHECK(LENGTH(CHAPTER_NAME)>0)," +
                    "MARINES_COUNT INT NOT NULL CHECK(MARINES_COUNT>0)," +
                    "WORLD VARCHAR NOT NULL CHECK(LENGTH(WORLD)>0)," +
                    "FOREIGN KEY (OWNER_ID) REFERENCES USERS (ID) ON DELETE CASCADE)";

    public Database(String user, String password) throws DataSourceException {
        super(user, password);
        initUsersTable();
        initSpaceMarineTable();
    }

    private void initUsersTable() throws DatabaseException {
        PreparedStatement preparedStatement = getPreparedStatement(CREATE_IF_NOT_EXISTS_USERS_TABLE);

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException | DAOException e) {
            throw new DatabaseException("Error while creation of users table", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

    private void initSpaceMarineTable() throws DatabaseException {
        PreparedStatement preparedStatement = getPreparedStatement(CREATE_IF_NOT_EXISTS_SPACE_MARINES_TABLE);

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException | DAOException e) {
            throw new DatabaseException("Error while createion of SpaceMarine table", e);
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }
}
