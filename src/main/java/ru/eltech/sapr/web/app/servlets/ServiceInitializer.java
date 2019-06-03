package ru.eltech.sapr.web.app.servlets;

import ru.eltech.sapr.web.ConnectionManager;
import ru.eltech.sapr.web.app.dao.H2BooksDao;
import ru.eltech.sapr.web.app.dao.H2PhoneDao;
import ru.eltech.sapr.web.app.dao.H2UsersDao;
import ru.eltech.sapr.web.app.service.LibraryService;
import ru.eltech.sapr.web.app.service.LibraryServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;

@WebListener
public class ServiceInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DataSource dataSource = ConnectionManager.createDataSource();
            LibraryServiceImpl service = new LibraryServiceImpl(
                    new H2UsersDao(dataSource),
                    new H2PhoneDao(dataSource),
                    new H2BooksDao(dataSource));

            sce.getServletContext().setAttribute(LibraryService.SERVICE_NAME, service);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Unable to initialize books service");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
