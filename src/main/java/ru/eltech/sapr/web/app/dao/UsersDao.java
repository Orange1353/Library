package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;

import java.util.List;

public interface UsersDao {
    List<User> getAll();
    User getById(long id);
    User create(UserType levelAccess, String firstName, String lastName, String login, int password);
    void update(User user);
    boolean delete(long id);
}