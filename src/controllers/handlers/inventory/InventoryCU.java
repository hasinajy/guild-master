package controllers.handlers.inventory;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;
import models.PlayerFull;
import models.Rarity;
import models.Transaction;
import models.Type;
import utils.ExceptionHandler;
import utils.RequestChecker;
import utils.UrlUtils;
import models.Inventory;

public class InventoryCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int inventoryId = Integer.parseInt(req.getParameter("inventory-id"));
                req.setAttribute("updated-inventory", Inventory.getById(inventoryId));
            }

            this.setAttributes(req);
            req.getRequestDispatcher("InventoryForm").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "InventoryCU";
            int itemID = Integer.parseInt(req.getParameter("item-id"));
            int playerID = Integer.parseInt(req.getParameter("player-id"));
            float durability = Float.parseFloat(req.getParameter("durability"));

            Inventory inventory = new Inventory(0, itemID, playerID, durability, 1, 0, 0);

            if (RequestChecker.isUpdateMode(req)) {
                int inventoryId = Integer.parseInt(req.getParameter("inventory-id"));

                url = UrlUtils.prepareUpdateURL(url, "inventory", inventoryId);
                inventory.update(inventoryId);
            } else {
                inventory.create();

                Transaction transaction = new Transaction(0, null, 2, itemID, playerID, 1, "");
                transaction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("item-list", Item.getAll());
        req.setAttribute("player-list", PlayerFull.getAll());
        req.setAttribute("type-list", Type.getAll());
        req.setAttribute("rarity-list", Rarity.getAll());
    }
}
