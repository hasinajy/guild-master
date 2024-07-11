package controllers.handlers.player;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Faction;
import models.Player;
import utils.ExceptionHandler;
import utils.ImageProcessor;
import utils.RequestChecker;
import utils.UrlUtils;
import models.Gender;

@MultipartConfig
public class PlayerCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int playerId = Integer.parseInt(req.getParameter("player-id"));
                req.setAttribute("updated-player", Player.getById(playerId));
            }

            this.setAttributes(req);
            req.getRequestDispatcher("PlayerForm").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "PlayerCU";
            String username = req.getParameter("username");
            String characterName = req.getParameter("character-name");
            int genderId = Integer.parseInt(req.getParameter("gender-id"));
            int level = Integer.parseInt(req.getParameter("level"));
            int factionId = Integer.parseInt(req.getParameter("faction-id"));
            String imgPath = ImageProcessor.processImage(null, req, "player");

            Player player = new Player(0, username, characterName, genderId, level, factionId, "", imgPath);

            if (RequestChecker.isUpdateMode(req)) {
                int playerId = Integer.parseInt(req.getParameter("player-id"));

                url = UrlUtils.prepareUpdateURL(url, "player", playerId);
                player.update(playerId);
            } else {
                player.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("gender-list", Gender.getAll());
        req.setAttribute("faction-list", Faction.getAll());
    }
}
