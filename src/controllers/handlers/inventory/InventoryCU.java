package controllers.handlers.inventory;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;
import models.Player;
import models.Rarity;
import models.Staff;
import models.Transaction;
import models.TransactionType;
import models.Type;
import utils.DateUtils;
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
            int itemId = Integer.parseInt(req.getParameter("item-id"));
            int playerId = Integer.parseInt(req.getParameter("player-id"));
            int durability = Integer.parseInt(req.getParameter("durability"));

            Item item = new Item();
            item.setItemId(itemId);

            Player player = new Player();
            player.setPlayerId(playerId);

            Inventory inventory = new Inventory(0, durability, 1, item, player);

            if (RequestChecker.isUpdateMode(req)) {
                int inventoryId = Integer.parseInt(req.getParameter("inventory-id"));

                url = UrlUtils.prepareUpdateURL(url, "inventory", inventoryId);
                inventory.update(inventoryId);

                // TODO: Check if transaction needs to be updated
            } else {
                inventory.create();

                // Create a new deposit transaction history when a new inventory is added
                Transaction transaction = new Transaction();

                TransactionType transactionType = new TransactionType();
                transactionType.setTransactionTypeId(2);

                Staff staff = new Staff();
                staff.setStaffId(1);

                transaction.setTransactionType(transactionType);
                transaction.setDate(DateUtils.getCurrentDate());
                transaction.setItem(item);
                transaction.setPlayer(player);

                transaction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("item-list", Item.getAll());
        req.setAttribute("player-list", Player.getAll());
        req.setAttribute("type-list", Type.getAll());
        req.setAttribute("rarity-list", Rarity.getAll());
    }
}
