package ru.eltech.sapr.web.app.service;

import ru.eltech.sapr.web.app.dao.BooksDao;
import ru.eltech.sapr.web.app.model.*;

import java.util.List;
import org.apache.log4j.Logger;

public class BookServiceImpl implements BookService {
    private static final Logger log = Logger.getLogger(BookService.class);
    private BooksDao dao;

    private void checkLevelAcces(User user)
    {
        if (user.getLevelAccess() == UserType.USER) {
            log.error("Ошибка доступа");
            throw new SecurityException();
        }
    }

    public BookServiceImpl(BooksDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Book> getByTypeAccess(User user, List<TypeAccessBook> listTypeAccessBooks) {
        checkLevelAcces(user);
        return null;
    }

    @Override
    public List<Book> getByCatandAccess(User user, List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook) {
        return null;
    }

    @Override
    public List<Book> getByCategories(User user, List<CategoryBook> categoryBooks) {
        checkLevelAcces(user);
        return dao.getByCategory(categoryBooks);
    }

    @Override
    public Book getById(User user, long id) {
        checkLevelAcces(user);
        return dao.getById(id);
    }

    @Override
    public Book add(User user, TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year) {
        checkLevelAcces(user);
        return dao.add(typeAccess, title, publishing, author, category, year);
    }

    @Override
    public void update(User user, Book book) {
        checkLevelAcces(user);
        dao.update(book);
    }

    @Override
    public boolean delete(User user, long id) {
        checkLevelAcces(user);
        return dao.delete(id);
    }

    @Override
    public List<Book> getAll(User user) {
        checkLevelAcces(user);
        return dao.getAll();
    }
}
