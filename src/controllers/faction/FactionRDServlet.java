package controllers.faction;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Faction;

public class FactionRDServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String factionID = req.getParameter("faction_id");
                new Faction(Integer.parseInt(factionID)).delete();
            }

            req.setAttribute("faction_list", Faction.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/factions.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
