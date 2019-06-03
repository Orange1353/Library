package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.Book;
import ru.eltech.sapr.web.app.model.CategoryBook;
import ru.eltech.sapr.web.app.model.TypeAccessBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public enum  InMemoryBooks implements BooksDao {
    INSTANCE;

    private AtomicLong idGenerator = new AtomicLong();
    private Map<Long, Book> allBooks = new HashMap<>();

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(allBooks.values());
    }

    @Override
    public Book getById(long id) {
        return allBooks.get(id);
    }

    private boolean checkCategory(CategoryBook categoryBook, List<CategoryBook> listCategoryBook)
    {
        for (CategoryBook cur : listCategoryBook)
        {
            if (cur == categoryBook)
                return true;
        }
        return false;
    }

    private boolean checkTypeAccess(TypeAccessBook typeAccessBook, List<TypeAccessBook> typeAccessBookList)
    {
        for (TypeAccessBook cur : typeAccessBookList)
        {
            if (cur == typeAccessBook)
                return true;
        }
        return false;
    }

    @Override
    public List<Book> getByCategory(List<CategoryBook> categoryBooks) {
        List<Book> books = new ArrayList<>();

        for (Book book : getAll())
            if (checkCategory(book.getCategory(), categoryBooks))
                books.add(book);

        return books;
    }

    @Override
    public List<Book> getByTypeAccess(List<TypeAccessBook> listTypeAccessBooks) {
        List<Book> books = new ArrayList<>();

        for (Book book : getAll())
            if (checkTypeAccess(book.getTypeAccess(), listTypeAccessBooks))
                books.add(book);

        return books;
    }

    @Override
    public List<Book> getByCatandAccess(List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook) {
        List<Book> books = new ArrayList<>();

        for (Book book : getAll())
            if (checkTypeAccess(book.getTypeAccess(), listTypeAccessBook) && checkCategory(book.getCategory(), listCategoryBook))
                books.add(book);

        return books;
    }

    @Override
    public Book add(TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year) {
        Book book = new Book(idGenerator.incrementAndGet(), typeAccess, title, publishing, author, category, year);
        allBooks.put(book.getId(), book);
        return book;
    }

    @Override
    public void update(Book book) {
        allBooks.put(book.getId(), book);
    }

    @Override
    public boolean delete(long id) {
        Book remove = allBooks.remove(id);
        return remove != null;
    }
}
