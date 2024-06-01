package controllers.player;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Player;
import models.PlayerFull;

public class PlayerRDServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String playerID = req.getParameter("player_id");
                new Player(Integer.parseInt(playerID)).delete();
                resp.sendRedirect("Player");
            }

            req.setAttribute("player_list", PlayerFull.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/players.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

}
