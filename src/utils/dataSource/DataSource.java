package utils.dataSource;

import utils.dao.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DataSource {
    protected final Connection connection;
    private final String url;
    private final String user;
    private final String password;

    public DataSource(String user, String password) throws DataSourceException {
        this.url = "jdbc:postgresql://localhost:5432/studs";
        this.user = user;
        this.password = password;

        connection = setupConnection();
    }

    private Connection setupConnection() throws DataSourceException {
        Connection connection;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DataSourceException("Error with database connection");
        } catch (ClassNotFoundException e) {
            throw new DataSourceException("org.postgresql.Driver is not available");
        }

        return connection;
    }

    public final PreparedStatement getPreparedStatement(String statement) throws DAOException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(statement);
        } catch (SQLException e) {
            throw  new DAOException("Error of SQL statement preparation");
        }

        return preparedStatement;
    }

    public final PreparedStatement getPreparedStatement(String sqlStatement, int statement) throws DAOException {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement, statement);
        } catch (SQLException e) {
            throw new DAOException("Error of SQL statement preparation");
        }

        return preparedStatement;
    }

    public final void closePreparedStatement(PreparedStatement preparedStatement) throws DAOException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DAOException("Error of SQL statement closing");
            }
        }
    }
}
