package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.exception.UserServiceException;
import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.PhoneType;

import java.sql.*;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class H2PhoneDao implements PhoneDao {
    private final static Logger log = Logger.getLogger(H2PhoneDao.class);
    private DataSource dataSource;

    public H2PhoneDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Phone retrievePhone(ResultSet resultSet) throws SQLException
    {
        PhoneType phoneType;
        switch (resultSet.getInt(3))
        {
            case 0:
                phoneType = PhoneType.HOME;
                break;
            case 1:
                phoneType = PhoneType.MOBILE;
                break;
                default:
                    log.error("Unknown index phoneType");
                    throw new RuntimeException("Unknown index phoneType");
        }


        return new Phone(
                resultSet.getLong(1),
                resultSet.getString(2),
                phoneType
        );
    }

    @Override
    public List<Phone> GetAllPhoneForUser(long userId) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from PHONES where User_id = ?")
                ){
                log.info("get all contact for User from H2DataSource");

                statement.setLong(1, userId);

                List<Phone> allPhones = new ArrayList<>();

                ResultSet resultSet = statement.executeQuery();
                if (resultSet == null)
                {
                    log.error("Unable to load phones");
                    throw new SQLException("Unable to load phones");
                }

                while(resultSet.next())
                    allPhones.add(retrievePhone(resultSet));
                return allPhones;
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public Phone getById(long id, long userId) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from PHONES where USER_ID = ? and ID = ?")
                ){
                log.info("get contact By id from H2DataSource");
                statement.setLong(1, userId);
                statement.setLong(2, id);

            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                return retrievePhone(resultSet);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public Phone createPhone(String number, PhoneType type, long userId) {
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO PHONES (NUMBER, TYPE, USER_ID)" +
                        "values (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
                ) {
            log.info("create new Contact for User from H2DataSource");
            statement.setString(1, number);
            statement.setInt(2, type.ordinal());
            statement.setLong(3, userId);

            int createdRows = statement.executeUpdate();
            if (createdRows != 1)
            {
                log.error("Unable Create new Contact");
                throw new UserServiceException("Unable Create new Contact");
            }

            try( ResultSet geteratedKeys = statement.getGeneratedKeys())
            {
                if (geteratedKeys.next())
                {
                    long id = geteratedKeys.getLong(1);
                    return new Phone(id, number, type);
                }
                else {
                    log.error("Creating contact failed, no ID obtained");
                    throw new SQLException("Creating contact failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }

    }

    @Override
    public boolean deletePhone(long phoneId, long userId) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE from PHONES WHERE ID = ? and USER_ID = ?")
                ){
                log.info("Delete Phone from H2DataSource");
                statement.setLong(1, phoneId);
                statement.setLong(2, userId);

                int deletedRows = statement.executeUpdate();
                if (deletedRows != 1)
                {
                    log.error("Unable to delete phone");
                    throw new SQLException("Unable to delete phone");
                }
                return true;

        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }

    @Override
    public void updatePhone(Phone phone, long userId) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE Phones " +
                        "SET NUMBER = ?, TYPE = ?, USER_ID = ? " +
                        "where ID = ?")
                ){
            log.info("Update Phone from H2DataSource");
            statement.setString(1, phone.getNumber());
            statement.setInt(2, phone.getType().ordinal());
            statement.setLong(3, userId);
            statement.setLong(4, phone.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 1)
            {
                log.error("Enable to update Phone");
                throw new SQLException("Enable to update Phone");
            }
        } catch (SQLException e) {
            log.error(e);
            throw new UserServiceException(e);
        }
    }
}
