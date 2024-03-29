package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (PsqlStore.instOf().findUserBy(email, password) != null) {
            HttpSession sc = req.getSession();
            User admin = PsqlStore.instOf().findByUser(email, password);
            admin.setId(admin.getId());
            admin.setName(admin.getName());
            admin.setEmail(admin.getEmail());
            admin.setPassword(admin.getPassword());
            sc.setAttribute("user", admin);
            resp.sendRedirect(req.getContextPath() + "/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}