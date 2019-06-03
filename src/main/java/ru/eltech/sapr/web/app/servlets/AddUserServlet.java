package ru.eltech.sapr.web.app.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.eltech.sapr.web.app.exception.UserServiceException;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.service.LibraryService;
import ru.eltech.sapr.web.app.thymeleaf.TemplateEngineUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterNewUser")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LibraryService service = (LibraryService) getServletContext().getAttribute(LibraryService.SERVICE_NAME);

            String firstName = req.getParameter("usr_first_name");
            String lastName = req.getParameter("usr_last_name");
            String login = req.getParameter("usr_email");
            String pass = req.getParameter("psw");

            User user = service.createUser(firstName, lastName, login, pass);
            System.out.println(user.toString());

            resp.sendRedirect("/AdminUsers");
        }
        catch (UserServiceException e)
        {
            resp.sendError(500, "Unable to create user");
        }
    }
}
