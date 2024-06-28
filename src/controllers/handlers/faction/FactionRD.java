package controllers.handlers.faction;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Faction;

public class FactionRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String factionID = req.getParameter("faction-id");
                new Faction(Integer.parseInt(factionID)).delete();
            }

            req.setAttribute("faction-list", Faction.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/factions.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
