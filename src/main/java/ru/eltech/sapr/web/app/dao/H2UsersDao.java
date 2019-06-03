package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;
import ru.eltech.sapr.web.app.exception.UserServiceException;

import org.apache.log4j.Logger;
import javax.sql.DataSource;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class H2UsersDao implements UsersDao {
    private final DataSource dataSource;
    private final Logger log = Logger.getLogger(UsersDao.class);


    public H2UsersDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAll() {
        try(
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from USERS")
                )
        {
            log.info("Run sql request: select * from USERS");
            if (resultSet == null)
            {
                log.error("Unable to load users");
                throw new SQLException("Unable to load users");
            }

            List<User> users = new ArrayList<>();
            while (resultSet.next())
                users.add(retrieveUser(resultSet));
            return users;
        } catch (SQLException e) {
            throw new UserServiceException(e);
        }
    }

    private User retrieveUser (ResultSet resultSet) throws SQLException {
        UserType type;
        switch (resultSet.getInt(4)){
            case 0:
                type = UserType.USER;
                break;
            case 1:
                type = UserType.EDITOR;
                break;
            case 2:
                type = UserType.ADMIN;
                break;
                default:
                    log.error("Unknown index userType");
                    throw new RuntimeException("Unknown index userType");
        }
        return new User(
                resultSet.getLong(1),
                type,
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(5),
                resultSet.getInt(6)
        );
    }

    @Override
    public User getById(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from USERS where id = ?")
                ){
            log.info("Get User By ID");
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                return retrieveUser(resultSet);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public User create(UserType levelAccess, String firstName, String lastName, String login, int password) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS (FIRST_NAME, LAST_NAME, TYPE, LOGIN, PASSWORD)" +
                        "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                ){
                log.info("Create new User");
                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setInt(3, levelAccess.ordinal());
                statement.setString(4, login);
                statement.setInt(5, password);

                int createdRows = statement.executeUpdate();
                if (createdRows != 1) { log.error("Unable to create new user"); throw new SQLException("Unable to create new user"); }

                try (ResultSet geteratedKeys = statement.getGeneratedKeys()) {
                    if (geteratedKeys.next()) {
                        long id = geteratedKeys.getLong(1);
                        return new User(id, levelAccess, firstName, lastName, login, password);
                    }
                    else{
                        log.error("Creating user failed, no ID obtained");
                        throw new SQLException("Creating user failed, no ID obtained");
                    }
                }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public void update(User user) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE USERS " +
                        "Set type = ?, first_name = ?, last_name = ?, login = ?, password = ?" +
                        "where id = ?")
                ) {
            statement.setInt(1, user.getLevelAccess().ordinal());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getLogin());
            statement.setInt(5, user.getPassword());
            statement.setLong(6, user.getId());

            int updateRows = statement.executeUpdate();
            if (updateRows!=1)
            {
                log.error("Unable to update user");
                throw new SQLException("Unable to update user");
            }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM USERS WHERE id = ? ")
                ) {
            statement.setLong(1, id);

            int deleteRows = statement.executeUpdate();
            if (deleteRows != 1)
            {
                log.error("Unable to delete user");
                throw new SQLException("Unable to delete user");
            }
            return true;
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }
}
