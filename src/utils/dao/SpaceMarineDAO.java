package utils.dao;

import item.*;
import user.User;
import utils.dataSource.DataSource;

import javax.xml.bind.ValidationException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SpaceMarineDAO {
    private static final String SELECT_ALL = "SELECT * FROM SPACE_MARINES";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE ID = ?";
    private static final String SELECT_BY_KEY = SELECT_ALL + " WHERE KEY = ?";
    private static final String INSERT =
            "INSERT INTO SPACE_MARINES (" +
                    "OWNER_ID, KEY, NAME, COORDINATES_X, COORDINATES_Y, " +
                    "CREATION_DATE, HEALTH, LOYAL, WEAPON_TYPE, MELEE_WEAPON," +
                    "CHAPTER_NAME, MARINES_COUNT, WORLD) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE SPACE_MARINES SET OWNER_ID = ?, KEY = ?, NAME = ?, " +
                    "COORDINATES_X = ?, COORDINATES_Y = ?, CREATION_DATE = ?, " +
                    "HEALTH = ?, LOYAL = ?, WEAPON_TYPE = ?, MELEE_WEAPON = ?, " +
                    "CHAPTER_NAME = ?, MARINES_COUNT = ?, WORLD = ? WHERE ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM SPACE_MARINES WHERE ID = ?";
    private static final String DELETE_BY_OWNER_ID = "DELETE FROM SPACE_MARINES WHERE OWNER_ID = ?";
    private final DataSource dataSource;

    public SpaceMarineDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<Integer, SpaceMarine> getAll() throws DAOException {
        Map<Integer, SpaceMarine> spaceMarines = new LinkedHashMap<>();
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(SELECT_ALL);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();

                spaceMarine.setId(resultSet.getInt("ID"));
                spaceMarine.setOwnerId(resultSet.getLong("OWNER_ID"));
                int key = resultSet.getInt("KEY");
                spaceMarine.setName(resultSet.getString("NAME"));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(resultSet.getLong("COORDINATES_X"));
                coordinates.setY(resultSet.getLong("COORDINATES_Y"));
                spaceMarine.setCoordinates(coordinates);

                spaceMarine.setCreationDate(resultSet.getDate("CREATION_DATE").toLocalDate().atStartOfDay());
                spaceMarine.setHealth(resultSet.getFloat("HEALTH"));
                spaceMarine.setLoyal(resultSet.getBoolean("LOYAL"));
                spaceMarine.setWeaponType(Weapon.valueOf(resultSet.getString("WEAPON_TYPE")));
                spaceMarine.setMeleeWeapon(MeleeWeapon.valueOf(resultSet.getString("MELEE_WEAPON")));

                Chapter chapter = new Chapter();
                chapter.setName(resultSet.getString("CHAPTER_NAME"));
                chapter.setMarinesCount(resultSet.getInt("MARINES_COUNT"));
                chapter.setWorld(resultSet.getString("WORLD"));
                spaceMarine.setChapter(chapter);

                spaceMarines.put(key, spaceMarine);
            }
        } catch (SQLException e) {
            throw new DAOException("Error in get all rows from database");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return spaceMarines;
    }

    public SpaceMarine insert(int key, SpaceMarine spaceMarine) throws DAOException {
        PreparedStatement preparedStatement =
                dataSource.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        try {
            preparedStatement.setLong(1, spaceMarine.getOwnerId());
            preparedStatement.setInt(2, key);
            preparedStatement.setString(3, spaceMarine.getName());
            preparedStatement.setLong(4, spaceMarine.getCoordinates().getX());
            preparedStatement.setLong(5, spaceMarine.getCoordinates().getY());
            preparedStatement.setDate(6, Date.valueOf(spaceMarine.getCreationDate().toLocalDate()));
            preparedStatement.setFloat(7, spaceMarine.getHealth());
            preparedStatement.setBoolean(8, spaceMarine.getLoyal());
            preparedStatement.setString(9, spaceMarine.getWeaponType().toString());
            preparedStatement.setString(10, spaceMarine.getMeleeWeapon().toString());
            preparedStatement.setString(11, spaceMarine.getChapter().getName());
            preparedStatement.setInt(12, spaceMarine.getChapter().getMarinesCount());
            preparedStatement.setString(13, spaceMarine.getChapter().getWorld());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                spaceMarine.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException("Error in adding row to database");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return spaceMarine;
    }

    public void delete(SpaceMarine spaceMarine) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(DELETE_BY_ID);

        try {
            preparedStatement.setLong(1, spaceMarine.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while item removing");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }
    }

    public SpaceMarine update(int key, SpaceMarine spaceMarine) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(UPDATE);

        try {
            preparedStatement.setLong(1, spaceMarine.getOwnerId());
            preparedStatement.setInt(2, key);
            preparedStatement.setString(3, spaceMarine.getName());
            preparedStatement.setLong(4, spaceMarine.getCoordinates().getX());
            preparedStatement.setLong(5, spaceMarine.getCoordinates().getY());
            preparedStatement.setDate(6, Date.valueOf(spaceMarine.getCreationDate().toLocalDate()));
            preparedStatement.setFloat(7, spaceMarine.getHealth());
            preparedStatement.setBoolean(8, spaceMarine.getLoyal());
            preparedStatement.setString(9, spaceMarine.getWeaponType().toString());
            preparedStatement.setString(10, spaceMarine.getMeleeWeapon().toString());
            preparedStatement.setString(11, spaceMarine.getChapter().getName());
            preparedStatement.setInt(12, spaceMarine.getChapter().getMarinesCount());
            preparedStatement.setString(13, spaceMarine.getChapter().getWorld());

            preparedStatement.setInt(14, spaceMarine.getId());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                spaceMarine.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new DAOException("Error in updating row to database");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return spaceMarine;
    }

    public void deleteAllByUser(User user) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(DELETE_BY_OWNER_ID);

        try {
            preparedStatement.setLong(1, new UserDAO(dataSource).getByLogin(user.getLogin()).getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DAOException("Error while item removing");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }
    }
}
