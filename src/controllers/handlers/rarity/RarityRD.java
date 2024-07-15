package controllers.handlers.rarity;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Rarity;
import utils.AuthenticationSecurity;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class RarityRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req) && AuthenticationSecurity.isLoggedIn(req)) {
                int rarityId = Integer.parseInt(req.getParameter("rarity-id"));
                Rarity.delete(rarityId);
            }

            this.setAttributes(req);
            req.getRequestDispatcher("/re-rarities").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("rarity-list", Rarity.getAll());
    }
}
