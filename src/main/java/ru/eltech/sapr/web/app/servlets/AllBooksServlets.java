package ru.eltech.sapr.web.app.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.eltech.sapr.web.app.model.User;
import ru.eltech.sapr.web.app.model.UserType;
import ru.eltech.sapr.web.app.service.LibraryService;
import ru.eltech.sapr.web.app.thymeleaf.TemplateEngineUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/AdminBooks")
public class AllBooksServlets extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType ("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter ();
        resp.setCharacterEncoding("UTF-8"); // кодировка ответа
        req.setCharacterEncoding("UTF-8");  // кодировка запроса

        LibraryService service = (LibraryService) getServletContext().getAttribute(LibraryService.SERVICE_NAME);

        User adminUser = new User(100, UserType.ADMIN, "admin", "admin", "admin", 789);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("books", service.getAllBook(adminUser));
        engine.process("Admin2.html", context, resp.getWriter());
    }
}
