package controllers.handlers.inventory;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Inventory;
import models.InventoryFull;
import models.Transaction;

public class InventoryRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String inventoryID = req.getParameter("inventory_id");

                if (req.getParameter("type") != null && req.getParameter("type").equals("w")) {
                    new Transaction(0).withdraw(Integer.parseInt(inventoryID));
                }

                new Inventory(Integer.parseInt(inventoryID)).delete();
                resp.sendRedirect("Inventory");
            }

            req.setAttribute("inventory_list", InventoryFull.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/inventory.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
