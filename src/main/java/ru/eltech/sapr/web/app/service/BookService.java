package ru.eltech.sapr.web.app.service;

import ru.eltech.sapr.web.app.model.*;

import java.util.List;

public interface BookService {
    List<Book> getAll(User user);
    List<Book> getByCategories(User user, List<CategoryBook> categoryBooks);
    List<Book> getByTypeAccess(User user, List<TypeAccessBook> listTypeAccessBooks);
    List<Book> getByCatandAccess(User user, List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook);
    Book getById(User user, long id);
    Book add(User user, TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year);
    void update(User user, Book book);
    boolean delete(User user, long id);
}