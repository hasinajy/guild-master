package controllers.handlers.faction;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Faction;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class FactionRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req)) {
                int factionId = Integer.parseInt(req.getParameter("faction-id"));
                Faction.deleteById(factionId);
            }

            this.setAttributes(req);
            req.getRequestDispatcher("Factions").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("faction-list", Faction.getAll());
    }
}
