package controllers.rarity;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Rarity;

public class RarityRDServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String rarityID = req.getParameter("rarity_id");
                new Rarity(Integer.parseInt(rarityID)).delete();
            }

            req.setAttribute("rarity_list", Rarity.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/rarities.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
