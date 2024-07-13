package controllers.handlers.player;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Player;
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

            this.setAttributes(req);
            req.getRequestDispatcher("/re-players").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("player-list", Player.getAll());
    }
}
