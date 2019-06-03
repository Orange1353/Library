package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.Book;
import ru.eltech.sapr.web.app.model.CategoryBook;
import ru.eltech.sapr.web.app.model.TypeAccessBook;
import ru.eltech.sapr.web.app.model.User;

import java.util.List;

public interface BooksDao {
    List<Book> getAll();
    Book getById(long id);
    List<Book> getByCategory(List<CategoryBook> categoryBooks);
    List<Book> getByTypeAccess(List<TypeAccessBook> listTypeAccessBooks);
    List<Book> getByCatandAccess(List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook);
    Book add(TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year);
    void update(Book book);
    boolean delete(long id);
}