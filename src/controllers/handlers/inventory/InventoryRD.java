package controllers.handlers.inventory;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Inventory;
import models.InventorySearchCriteria;
import models.Item;
import models.Rarity;
import models.Transaction;
import models.Type;
import utils.AuthenticationSecurity;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class InventoryRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req) && AuthenticationSecurity.isLoggedIn(req)) {
                int inventoryId = Integer.parseInt(req.getParameter("inventory-id"));

                if (RequestChecker.isWithdrawMode(req)) {
                    Transaction.withdraw(inventoryId);
                }

                Inventory.delete(inventoryId);
                resp.sendRedirect("inventories");
            }

            InventorySearchCriteria criteria = null;

            if (RequestChecker.isSearchMode(req)) {
                criteria = InventorySearchCriteria.getCriteriaFromRequest(req);
            }

            this.setAttributes(req, criteria);
            req.getRequestDispatcher("/re-inventories").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req, InventorySearchCriteria criteria) throws ClassNotFoundException, SQLException {
        req.setAttribute("item-list", Item.getAll());
        req.setAttribute("type-list", Type.getAll());
        req.setAttribute("rarity-list", Rarity.getAll());

        if (criteria == null) {
            req.setAttribute("inventory-list", Inventory.getAll());
        } else {
            req.setAttribute("inventory-list", Inventory.searchInventories(criteria));
        }
    }
}
 