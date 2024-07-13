package controllers.handlers.item;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Item;
import models.Rarity;
import models.Type;
import utils.ExceptionHandler;
import utils.ImageProcessor;
import utils.RequestChecker;
import utils.UrlUtils;

@MultipartConfig
public class ItemCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int itemId = Integer.parseInt(req.getParameter("item-id"));
                req.setAttribute("updated-item", Item.getById(itemId));
            }

            this.setAttributes(req);
            req.getRequestDispatcher("/item-form").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "item-cu";
            String name = req.getParameter("item-name");
            String imgPath = ImageProcessor.processImage(this, req, "item");

            Type type = new Type();
            type.setTypeId(Integer.parseInt(req.getParameter("type-id")));

            Rarity rarity = new Rarity();
            rarity.setRarityId(Integer.parseInt(req.getParameter("rarity-id")));

            Item item = new Item(0, name, type, rarity, imgPath);

            if (RequestChecker.isUpdateMode(req)) {
                int itemId = Integer.parseInt(req.getParameter("item-id"));

                url = UrlUtils.prepareUpdateURL(url, "item", itemId);
                item.update(itemId);
            } else {
                item.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("rarity-list", Rarity.getAll());
        req.setAttribute("type-list", Type.getAll());
    }
}
