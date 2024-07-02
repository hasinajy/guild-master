package controllers.handlers.rarity;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Rarity;
import utils.ExceptionHandler;
import utils.ImageProcessor;
import utils.RequestChecker;
import utils.UrlUtils;

@MultipartConfig
public class RarityCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int rarityId = Integer.parseInt(req.getParameter("rarity-id"));
                req.setAttribute("updated-rarity", Rarity.getById(rarityId));
            }

            req.getRequestDispatcher("RarityForm").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "RarityCU";
            String name = req.getParameter("rarity-name");
            String imgPath = ImageProcessor.processImage(this, req, "rarity");

            Rarity rarity = new Rarity(0, name, imgPath);

            if (RequestChecker.isUpdateMode(req)) {
                int rarityId = Integer.parseInt(req.getParameter("rarity-id"));

                url = UrlUtils.prepareUpdateURL(url, "rarity", rarityId);
                rarity.update(rarityId);
            } else {
                rarity.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }
}
