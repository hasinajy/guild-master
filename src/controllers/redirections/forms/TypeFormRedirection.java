package controllers.redirections.forms;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ExceptionHandler;
import jakarta.servlet.ServletException;

public class TypeFormRedirection extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/type-form.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            ExceptionHandler.handleException(e, Level.SEVERE, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An unexpected error occurred", e), Level.SEVERE, resp);
        }
    }
}
