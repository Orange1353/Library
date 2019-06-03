package ru.eltech.sapr.web.app.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.eltech.sapr.web.ConnectionManager;
import ru.eltech.sapr.web.app.dao.H2BooksDao;
import ru.eltech.sapr.web.app.model.*;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookServiceImplTest {
    private User adminUser = new User(100, UserType.ADMIN, "admin", "admin", "admin", 789);
    private User editorUser = new User(101, UserType.EDITOR, "editor", "editor", "editor", 789);
    private User user1 = new User(1, UserType.USER, "Ivan", "Ivanov", "Ivan@mail.ru", 522301);

    private Book book1 = new Book(1, TypeAccessBook.IN_STOCK, "PDD", "ACT", "Жульнев", CategoryBook.CARS, 2018);

    private DataSource dataSource;
    private BookServiceImpl service;

    @Before
    public void setUp() throws Exception {
        dataSource = ConnectionManager.createDataSource();
        service = new BookServiceImpl(new H2BooksDao(dataSource));
    }

    @Test
    public void getByCategories() {
        List<CategoryBook> categoryBooks = new ArrayList<>();
        categoryBooks.add(CategoryBook.CARS);

        List <Book> getBooks = service.getByCategories(adminUser, categoryBooks);

        List <Book> actual = new ArrayList<>();
        actual.add(book1);

        Assert.assertEquals(getBooks, actual);
    }

    @Test
    public void getByIdForAdmin() {
        Book book = service.getById(adminUser,1 );

        Assert.assertEquals(book1, book);
    }

    @Test
    public void getByIdForEditor() {
        Book book = service.getById(editorUser,1 );

        Assert.assertEquals(book1, book);
    }

    @Test
    public void getByIdForUser() {
        try {
            Book book = service.getById(user1,1 );
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }

    @Test
    public void add() {
        Book newBook = service.add(adminUser,TypeAccessBook.FROM_USER, "TITLE", "Public", "Author", CategoryBook.FANTASY, 2019);
        Book actual = new Book(newBook.getId(), TypeAccessBook.FROM_USER, "TITLE", "Public", "Author", CategoryBook.FANTASY, 2019);
        Assert.assertEquals(newBook, actual);
    }

    @Test
    public void update() {
        Book newBook = service.add(adminUser,TypeAccessBook.RECYCLABLE, "TITLE", "PRIVATE", "Author", CategoryBook.FANTASY, 2015);
        Book actual = new Book(newBook.getId(), TypeAccessBook.RECYCLABLE, "TITLE", "PRIVATE", "Author", CategoryBook.FANTASY, 2019);

        service.update(adminUser, actual);
        Book updatedBook = service.getById(adminUser, newBook.getId());

        Assert.assertEquals(updatedBook, actual);
    }

    @Test
    public void delete() {
        Book newBook = service.add(adminUser, TypeAccessBook.RECYCLABLE, "fgsdfg", "cwer", "fsdgsdf", CategoryBook.FANTASY, 2015);

        boolean deletedBook = service.delete(adminUser, newBook.getId());
        Assert.assertTrue(deletedBook);
    }

    @Ignore
    public void getAllForAdmin() {
        List<Book> expected = service.getAll(adminUser);

        List<Book> actual = new ArrayList<>();
        actual.add(book1);

        Assert.assertEquals(expected, actual);
    }

    @Ignore
    public void getAllForEditor() {
        List<Book> expected = service.getAll(editorUser);

        List<Book> actual = new ArrayList<>();
        actual.add(book1);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllForUser() {
        try {
            List<Book> expected = service.getAll(user1);
            fail();
        }
        catch (Exception e)
        {
            Assert.assertEquals(e.toString(), "java.lang.SecurityException");
        }
    }
}