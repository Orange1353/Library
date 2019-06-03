package ru.eltech.sapr.web.app.dao;

import ru.eltech.sapr.web.app.model.Book;
import ru.eltech.sapr.web.app.exception.BookServiceException;

import java.io.PipedInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ru.eltech.sapr.web.app.model.CategoryBook;
import ru.eltech.sapr.web.app.model.TypeAccessBook;

import javax.sql.DataSource;

public class H2BooksDao implements BooksDao {
    private final static Logger log = Logger.getLogger(H2BooksDao.class);
    private DataSource dataSource;

    public H2BooksDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Book retriveBook(ResultSet resultSet) throws SQLException
    {
        TypeAccessBook typeAccessBook;
        switch (resultSet.getInt(2))
        {
            case 0:
                typeAccessBook = TypeAccessBook.IN_STOCK;
                break;
            case 1:
                typeAccessBook = TypeAccessBook.COMING_SOON;
                break;
            case 2:
                typeAccessBook = TypeAccessBook.FROM_USER;
                break;
            case 3:
                typeAccessBook = TypeAccessBook.RECYCLABLE;
                break;
                default:
                    log.error("Unknown index typeAccessBook");
                    throw new RuntimeException("Unknown index typeAccessBook");
        }

        CategoryBook categoryBook = CategoryBook.valueOf(resultSet.getString(6));
        return new Book(
                resultSet.getLong(1),
                typeAccessBook,
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                categoryBook,
                resultSet.getInt(7)
        );
    }

    @Override
    public List<Book> getByTypeAccess(List<TypeAccessBook> listTypeAccessBooks) {
        return null;
    }

    @Override
    public List<Book> getByCatandAccess(List<CategoryBook> listCategoryBook, List<TypeAccessBook> listTypeAccessBook) {
        return null;
    }

    private String getSqlRequestForCategories(List<CategoryBook> categoryBooks) throws Exception
    {
        String categories = "";
        if (!categoryBooks.isEmpty())
        {
            for (int i = 1; i < categoryBooks.size(); i++)
            {
                categories += " OR CATEGORY LIKE ?";
            }
        }
        else
        {
            log.error("No categories found");
            throw new BookServiceException("No categories found");
        }
        return categories;
    }

    @Override
    public List<Book> getByCategory(List<CategoryBook> categoryBooks) {
        try {
            String categories = getSqlRequestForCategories(categoryBooks);
            String sqlRequest = "select * from BOOKS where CATEGORY LIKE ?";
            sqlRequest += categories;

            log.info(sqlRequest);
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(sqlRequest);
            ){
                statement.setString(1, categoryBooks.get(0).toString());
                log.info("Getting book by category");
                //for (int i = 0; i < categoryBooks.size(); i++)
//                    statement.setString(i + 1, categoryBooks.get(i).toString());

                try(ResultSet resultSet = statement.executeQuery())
                {
                    List<Book> newbooks = new ArrayList<>();
                    while (resultSet.next())
                        newbooks.add(retriveBook(resultSet));

                    return newbooks;
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public List<Book> getAll() {
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * from BOOKS");
                ){
            log.info("getting all books from Database");

            if (resultSet == null)
            {
                throw new SQLException("Unable to load books");
            }

            List<Book> books = new ArrayList<>();
            while(resultSet.next())
                books.add(retriveBook(resultSet));

            return books;
        } catch (SQLException e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book getById(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("select * from BOOKS where id = ?")
                ){
            log.info("Getting book by id");
            statement.setLong(1, id);

            try(ResultSet resultSet = statement.executeQuery())
            {
                resultSet.next();
                return retriveBook(resultSet);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public Book add(TypeAccessBook typeAccess, String title, String publishing, String author, CategoryBook category, int year) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("insert into BOOKS (TYPEACCASS, TITLE, PUBLISHING, AUTHOR, CATEGORY, YEAR)" +
                        "VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ){
            log.info("Creating new book");

            statement.setInt(1, typeAccess.ordinal());
            statement.setString(2, title);
            statement.setString(3, publishing);
            statement.setString(4, author);
            statement.setString(5, category.name());
            statement.setInt(6, year);

            int createdRows = statement.executeUpdate();
            if (createdRows != 1) { log.error("Unable to create book"); throw new SQLException("Unable to create book"); }

            try (ResultSet genKey = statement.getGeneratedKeys()){
                if (genKey.next())
                {
                    long id = genKey.getLong(1);
                    return new Book(id, typeAccess, title, publishing, author, category, year);
                }
                else
                {
                    log.error("Creating book failed, no ID obtained");
                    throw new BookServiceException("Creating book failed, no ID obtained");
                }
            }
        } catch (SQLException e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE BOOKS " +
                        "SET TYPEACCASS = ?, TITLE = ?, PUBLISHING= ?, AUTHOR= ?, CATEGORY= ?, YEAR= ? " +
                        "where ID = ?")
                ){
            statement.setInt(1, book.getTypeAccess().ordinal());
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getPublishing());
            statement.setString(4, book.getAuthor());
            statement.setString(5, book.getCategory().name());
            statement.setInt(6, book.getYear());
            statement.setLong(7, book.getId());

            int updatedRows = statement.executeUpdate();
            if (updatedRows != 1) { log.error("Unable to update book"); throw new SQLException("Unable to update book"); }

        } catch (SQLException e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement("DELETE FROM BOOKS where id = ?")
                ){
            statement.setLong(1, id);

            int deletedRows = statement.executeUpdate();
            if (deletedRows != 1)
            {
                log.error("Unable to delete book");
                throw new SQLException("Unable to delete book");
            }
            return true;
        } catch (SQLException e) {
            log.error(e);
            throw new BookServiceException(e);
        }
    }
}
