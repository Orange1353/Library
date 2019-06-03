package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public enum  InMemoryUsers implements UsersDao {
    INSTANCE;

    private AtomicLong idGenerator = new AtomicLong();
    private Map<Long, User> allUsers = new HashMap<>();

    @Override
    public List<User> getAll() {
        return new ArrayList<>(allUsers.values());
    }

    @Override
    public User getById(long id) {
        return allUsers.get(id);
    }

    @Override
    public User create(UserType levelAccess, String firstName, String lastName, String login, int password) {
        User user = new User(idGenerator.incrementAndGet(), levelAccess, firstName, lastName, login, password);
        allUsers.put(user.getId(), user);
        return user;
    }

    @Override
    public void update(User user) {
        allUsers.put(user.getId(), user);
    }

    @Override
    public boolean delete(long id) {
        User remove = allUsers.remove(id);
        return remove != null;
    }
}