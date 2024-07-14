package controllers.handlers.auth;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import utils.ExceptionHandler;

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Invalidate the session
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            resp.sendRedirect("dashboard");
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An error occurred while logging out", e), Level.SEVERE,
                    resp);
        }
    }
}
