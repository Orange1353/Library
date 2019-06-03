package ru.eltech.sapr.web.app.service;

import org.apache.log4j.Logger;
import ru.eltech.sapr.web.app.dao.*;
import ru.eltech.sapr.web.app.model.*;

import java.util.List;

public class LibraryServiceImpl implements LibraryService {
    private UsersDao userDao;
    private PhoneDao phoneDao;
    private BooksDao dao;
    private static final Logger log = Logger.getLogger(LibraryService.class);

    public LibraryServiceImpl(UsersDao userDao, PhoneDao phoneDao, BooksDao dao) {
        this.userDao = userDao;
        this.phoneDao = phoneDao;
        this.dao = dao;
    }

    private void checkLevelAccessForBook(User user)
    {
        if (user.getLevelAccess() == UserType.USER) {
            log.error("Ошибка доступа");
            throw new SecurityException();
        }
    }

    private void CheckLevelAccesForUsers(User user)
    {
        if (user.getLevelAccess() != UserType.ADMIN) {
            log.error("Ошибка доступа");
            throw new SecurityException();
        }
    }

    @Override
    public List<Book> getAllBook(User user) {
        checkLevelAccessForBook(user);
        log.info("Получение списка всех книг");
        return dao.getAll();
    }

    @Override
    public List<Book> getBookByCategories(User user, List<CategoryBook> categoryBooks) {
        checkLevelAccessForBook(user);
        log.info("Получение списка по категориям");
        return dao.getByCategory(categoryBooks);
    }

    @Override
    public List<Book> getBookByTypeAccess(User user, List<TypeAccessBook> listTypeAccessBooks) {

        return null;
    }

    @Override
    public List<Book> getBookByCatandAccess(User user, List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook) {
        return null;
    }

    @Override
    public Book getBookById(User user, long id) {
        checkLevelAccessForBook(user);
        log.info("Получение книги по id");
        return dao.getById(id);
    }

    @Override
    public Book addBook(User user, TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year) {
        checkLevelAccessForBook(user);
        log.info("Добавление книги");
        return dao.add(typeAccess, title, publishing, author, category, year);
    }

    @Override
    public void updateBook(User user, Book book) {
        checkLevelAccessForBook(user);
        log.info("Изменение книги");
        dao.update(book);
    }

    @Override
    public boolean deleteBook(long id, User user) {
        checkLevelAccessForBook(user);
        log.info("Удаление книги");
        return dao.delete(id);
    }

    @Override
    public List<User> getAllUsers(User user) {
        CheckLevelAccesForUsers(user);
        log.info("Получение списка всех пользователей");
        return userDao.getAll();
    }

    @Override
    public User getUserById(long id, User user) {
        CheckLevelAccesForUsers(user);
        log.info("Получение пользователя по id");
        return userDao.getById(id);
    }

    @Override
    public User createUser(String firstName, String lastName, String login, String password) {
        log.info("Создание пользователя");
        int hashPassword = password.hashCode();
        return userDao.create(UserType.USER, firstName, lastName, login, hashPassword);
    }

    @Override
    public void updateUser(User user) {
        log.info("Обновление пользователя");
        userDao.update(user);
    }

    @Override
    public void updateUserType(long id, User toUser, User fromUser, UserType userType) {
        CheckLevelAccesForUsers(fromUser);
        User updatedUser = new User(id, userType, toUser.getFirstName(), toUser.getLastName(), toUser.getLogin(), toUser.getPassword());
        userDao.update(updatedUser);
    }

    @Override
    public boolean deleteUser(long id) {
        log.info("Удаление пользователя");

        /*for (Phone phone : phoneDao.GetAllPhoneForUser(id)){
            phoneDao.deletePhone(phone.getId(), id);
        }*/

        return userDao.delete(id);
    }

    @Override
    public boolean deleteUser(long id, User fromUser) {
        log.info("Удаление пользователя");
        CheckLevelAccesForUsers(fromUser);
        return deleteUser(id);
    }

    @Override
    public List<Phone> GetAllPhoneForUser(User user) {
        return null;
    }

    @Override
    public List<Phone> GetAllPhoneForUser(User toUser, User fromUser) {
        return null;
    }

    @Override
    public Phone createPhone(User user, String number, PhoneType type) {
        return null;
    }

    @Override
    public Phone getByIdPhone(long id, User user) {
        return null;
    }

    @Override
    public boolean deletePhone(User user, long phoneId) {
        return false;
    }

    @Override
    public void updatePhone(User user, Phone phone) {

    }
}
