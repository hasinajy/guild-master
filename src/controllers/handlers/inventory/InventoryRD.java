package controllers.handlers.inventory;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Inventory;
import models.InventoryFull;
import models.Transaction;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class InventoryRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req)) {
                int inventoryId = Integer.parseInt(req.getParameter("inventory-id"));

                if (RequestChecker.isWithdrawMode(req)) {
                    Transaction.withdraw(inventoryId);
                }

                Inventory.delete(inventoryId);
                resp.sendRedirect("inventories");
            }

            this.setAttributes(req);
            req.getRequestDispatcher("Inventories").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("inventory-list", InventoryFull.getAll());
    }
}
