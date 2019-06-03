package ru.eltech.sapr.web.app.service;

import org.apache.log4j.Logger;

import ru.eltech.sapr.web.app.dao.UsersDao;
import ru.eltech.sapr.web.app.model.Phone;
import ru.eltech.sapr.web.app.model.PhoneType;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;
import ru.eltech.sapr.web.app.dao.PhoneDao;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UsersDao userDao;
    private final PhoneDao phoneDao;
    private static final Logger log = Logger.getLogger(UserService.class);

    private void CheckLevelAcces(User user)
    {
        if (user.getLevelAccess() != UserType.ADMIN) {
            log.error("Ошибка доступа");
            throw new SecurityException();
        }
    }

    @Override
    public List<User> getAll(User user) {
        CheckLevelAcces(user);
        log.info("Получение списка всех пользователей");
        return userDao.getAll();
    }

    @Override
    public User getById(long id, User user) {
        CheckLevelAcces(user);
        log.info("Получение пользователя по id");
        return userDao.getById(id);
    }

    @Override
    public User create(String firstName, String lastName, String login, String password) {
        log.info("Создание пользователя");
        int hashPassword = password.hashCode();
        return userDao.create(UserType.USER, firstName, lastName, login, hashPassword);
    }

    @Override
    public void update(User user) {
        log.info("Обновление пользователя");
        userDao.update(user);
    }

    @Override
    public void updateUserType(long id, User toUser, User fromUser, UserType userType) {
        CheckLevelAcces(fromUser);
        User updatedUser = new User(id, userType, toUser.getFirstName(), toUser.getLastName(), toUser.getLogin(), toUser.getPassword());
        userDao.update(updatedUser);
    }

    @Override
    public boolean delete(User user) {
        log.info("Удаление пользователя");
        for (Phone phone:phoneDao.GetAllPhoneForUser(user.getId())){
            phoneDao.deletePhone(phone.getId(), user.getId());
        }
        return userDao.delete(user.getId());
    }

    @Override
    public List<Phone> GetAllPhoneForUser(User toUser, User fromUser) {
        CheckLevelAcces(fromUser);
        return GetAllPhoneForUser(toUser);
    }

    @Override
    public boolean delete(User toUser, User fromUser) {
        log.info("Удаление пользователя");
        CheckLevelAcces(fromUser);
        return delete(toUser);
    }

    @Override
    public List<Phone> GetAllPhoneForUser(User user) {
        log.info("Получение списка телефонов");
        return phoneDao.GetAllPhoneForUser(user.getId());
    }

    @Override
    public Phone createPhone(User user, String number, PhoneType type) {
        log.info("Создание контакта");
        return phoneDao.createPhone(number, type, user.getId());
    }

    @Override
    public boolean deletePhone(User user, long phoneId) {
        log.info("Удаление контакта");
        return phoneDao.deletePhone(phoneId, user.getId());
    }

    @Override
    public Phone getByIdPhone(long id, User user) {
        log.info("Получение контакта по id");
        return phoneDao.getById(id, user.getId());
    }

    @Override
    public void updatePhone(User user, Phone phone) {
        log.info("Обновление контакта");
        phoneDao.updatePhone(phone, user.getId());
    }

    public UserServiceImpl(UsersDao userDao, PhoneDao phoneDao) {
        this.userDao = userDao;
        this.phoneDao = phoneDao;
    }
}
