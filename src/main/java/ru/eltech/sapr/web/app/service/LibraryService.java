package ru.eltech.sapr.web.app.service;

import ru.eltech.sapr.web.app.model.*;

import java.util.List;

public interface LibraryService {
    String SERVICE_NAME = "LibraryService";

    List<Book> getAllBook(User user);
    List<Book> getBookByCategories(User user, List<CategoryBook> categoryBooks);
    List<Book> getBookByTypeAccess(User user, List<TypeAccessBook> listTypeAccessBooks);
    List<Book> getBookByCatandAccess(User user, List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook);
    Book getBookById(User user, long id);
    Book addBook(User user, TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year);
    void updateBook(User user, Book book);
    boolean deleteBook(long id, User user);

    List<User> getAllUsers(User user);
    User getUserById(long id, User user);
    User createUser(String firstName, String lastName, String login, String password);
    void updateUser(User user);
    void updateUserType(long id, User toUser, User fromUser, UserType userType);
    boolean deleteUser(long id);
    boolean deleteUser(long id, User fromUser);

    List<Phone> GetAllPhoneForUser(User user);
    List<Phone> GetAllPhoneForUser(User toUser, User fromUser);
    Phone createPhone(User user, String number, PhoneType type);
    Phone getByIdPhone(long id, User user);
    boolean deletePhone(User user, long phoneId);
    void updatePhone(User user, Phone phone);
}
