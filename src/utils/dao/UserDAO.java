package utils.dao;

import user.User;
import utils.dataSource.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UserDAO {
    private static final String SELECT_ALL = "SELECT * FROM USERS";
    private static final String SELECT_BY_LOGIN = SELECT_ALL + " WHERE LOGIN = ?";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE ID = ?";
    private static final String INSERT = "INSERT INTO USERS (LOGIN, PASSWORD, EMAIL) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE USERS SET LOGIN = ?, PASSWORD = ? WHERE ID = ?";
    private static final String DELETE = "DELETE FROM USERS WHERE ID = ?";
    private final DataSource dataSource;

    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void get(ResultSet resultSet, User user) throws SQLException {
        user.setId(resultSet.getLong("ID"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setPassword(resultSet.getString("PASSWORD"));
    }

    public Set<User> getAll() throws DAOException {
        Set<User> users = new HashSet<>();
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(SELECT_ALL);

        try {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                get(resultSet, user);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while getting list of users");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return users;
    }

    public User getByLogin(String login) throws DAOException {
        User user = null;
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(SELECT_BY_LOGIN);

        try {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                get(resultSet, user);
            }
        } catch (SQLException e) {
            throw new DAOException("Error in getting user by login");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return user;
    }

    public User insert(User user) throws DAOException {
        PreparedStatement preparedStatement = dataSource.getPreparedStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());

            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            throw new DAOException("Error while adding new user in database");
        } finally {
            dataSource.closePreparedStatement(preparedStatement);
        }

        return user;
    }
}
