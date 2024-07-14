package controllers.handlers.auth;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;
import utils.ExceptionHandler;

public class Authentication extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("pwd");

            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                // TODO: Redirect to the authentication page
            }

            Account account = new Account(username, password);

            if (account.exists()) {
                // TODO: Redirect to the dashboard page
            } else {
                // TODO: Redirect to the authentication page
            }
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An error occurred during authentication", e), Level.SEVERE,
                    resp);
        }
    }
}