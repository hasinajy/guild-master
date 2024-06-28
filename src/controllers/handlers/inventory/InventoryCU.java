package controllers.handlers.inventory;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;
import models.PlayerFull;
import models.Rarity;
import models.Transaction;
import models.Type;
import models.Inventory;

public class InventoryCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String inventoryID = req.getParameter("inventory-id");
                Inventory updatedInventory = Inventory.getByID(Integer.parseInt(inventoryID));
                req.setAttribute("updated-inventory", updatedInventory);
            }

            req.setAttribute("item-list", Item.getAll());
            req.setAttribute("player-list", PlayerFull.getAll());
            req.setAttribute("type-list", Type.getAll());
            req.setAttribute("rarity-list", Rarity.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/inventory-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "inventory-cu";
            int itemID = Integer.parseInt(req.getParameter("item-id"));
            int playerID = Integer.parseInt(req.getParameter("player-id"));
            float durability = Float.parseFloat(req.getParameter("durability"));

            Inventory inventory = new Inventory(0, itemID, playerID, durability, 1, 0, 0);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int InventoryID = Integer.parseInt(req.getParameter("inventory-id"));
                url += "?mode=u";
                url += "&inventory-id=" + InventoryID;

                inventory.setInventoryID(InventoryID);
                inventory.update();
            } else {
                inventory.create();
                new Transaction(0, null, 2, itemID, playerID, 1, "").create();
                ;
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
