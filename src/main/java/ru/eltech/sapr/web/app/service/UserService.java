package ru.eltech.sapr.web.app.service;

import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.PhoneType;
import ru.eltech.sapr.web.app.model.UserType;

import java.util.List;

public interface UserService {
    List<User> getAll(User user);
    User getById(long id, User user);
    User create(String firstName, String lastName, String login, String password);
    void update(User user);
    void updateUserType(long id, User toUser, User fromUser, UserType userType);
    boolean delete(User user);
    boolean delete(User toUser, User fromUser);

    List<Phone> GetAllPhoneForUser(User user);
    List<Phone> GetAllPhoneForUser(User toUser, User fromUser);
    Phone createPhone(User user, String number, PhoneType type);
    Phone getByIdPhone(long id, User user);
    boolean deletePhone(User user, long phoneId);
    void updatePhone(User user, Phone phone);
}