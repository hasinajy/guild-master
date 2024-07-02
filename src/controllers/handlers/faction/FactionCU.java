package controllers.handlers.faction;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Faction;
import utils.ExceptionHandler;
import utils.ImageProcessor;
import utils.RequestChecker;
import utils.UrlUtils;

@MultipartConfig
public class FactionCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int factionId = Integer.parseInt(req.getParameter("faction-id"));
                req.setAttribute("updated-faction", Faction.getById(factionId));
            }

            req.getRequestDispatcher("faction-form").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "faction-cu";
            String name = req.getParameter("faction-name");
            String imgPath = ImageProcessor.processImage(this, req, "faction");

            Faction faction = new Faction(0, name, imgPath);

            if (RequestChecker.isUpdateMode(req)) {
                int factionId = Integer.parseInt(req.getParameter("faction-id"));

                url = UrlUtils.prepareUpdateURL(url, "faction", factionId);
                faction.update(factionId);
            } else {
                faction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }
}
