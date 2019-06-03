package ru.eltech.sapr.web.app.servlets;

import ru.eltech.sapr.web.app.exception.UserServiceException;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;
import ru.eltech.sapr.web.app.service.LibraryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            LibraryService service = (LibraryService) getServletContext().getAttribute(LibraryService.SERVICE_NAME);

            String[] strId = req.getParameterValues("btnId");

            for (String str : strId)
            {
                System.out.println(str);
            }

            long id = Long.parseLong(strId[0]);

            User adminUser = new User(100, UserType.ADMIN, "admin", "admin", "admin", 789);

            if (service.deleteUser(id, adminUser))
            {
                resp.sendRedirect("/AdminUsers");
            }
            else
            {
                resp.sendRedirect("/Error");
            }

        } catch (UserServiceException e) {
            resp.sendError(500, "Unable to delete user");
        }
    }
}
