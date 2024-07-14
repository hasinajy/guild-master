package controllers.handlers.auth;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Account;
import utils.ExceptionHandler;

public class Authentication extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                // TODO: Redirect to the authentication page
                resp.sendRedirect("auth");
            }

            Account account = new Account(username, password);

            if (account.exists()) {
                // TODO: Redirect to the dashboard page

                HttpSession session = req.getSession();
                session.setAttribute("username", username);

                resp.sendRedirect("inventories");
            } else {
                // TODO: Redirect to the authentication page
                resp.sendRedirect("auth");
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An error occurred during authentication", e), Level.SEVERE,
                    resp);
        }
    }
}