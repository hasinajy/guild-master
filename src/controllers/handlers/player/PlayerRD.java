package controllers.handlers.player;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import models.Faction;
import models.Player;
import models.PlayerSearchCriteria;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class PlayerRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req)) {
                int playerId = Integer.parseInt(req.getParameter("player-id"));
                Player.delete(playerId);
            }

            PlayerSearchCriteria criteria = null;

            if (RequestChecker.isSearchMode(req)) {
                criteria = PlayerSearchCriteria.getCriteriaFromRequest(req);
            }
            
            this.setAttributes(req, criteria);
            req.getRequestDispatcher("/re-players").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req, PlayerSearchCriteria criteria) throws ClassNotFoundException, SQLException {
        req.setAttribute("faction-list", Faction.getAll());

        if (criteria == null) {
            req.setAttribute("player-list", Player.getAll());
        } else {
            req.setAttribute("player-list", Player.searchPlayers(criteria));
        }
    }
}
