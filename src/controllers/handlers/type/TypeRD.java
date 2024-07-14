package controllers.handlers.type;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Type;
import utils.AuthenticationSecurity;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class TypeRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req) && AuthenticationSecurity.isLoggedIn(req)) {
                int typeId = Integer.parseInt(req.getParameter("type-id"));
                Type.delete(typeId);
            }

            this.setAttributes(req);
            req.getRequestDispatcher("/re-types").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("type-list", Type.getAll());
    }
}
