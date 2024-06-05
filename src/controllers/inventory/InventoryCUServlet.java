package controllers.inventory;

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

public class InventoryCUServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String inventoryID = req.getParameter("inventory_id");
                Inventory updatedInventory = Inventory.getByID(Integer.parseInt(inventoryID));
                req.setAttribute("updated_inventory", updatedInventory);
            }

            req.setAttribute("item_list", Item.getAll());
            req.setAttribute("player_list", PlayerFull.getAll());
            req.setAttribute("type_list", Type.getAll());
            req.setAttribute("rarity_list", Rarity.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/inventory-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "InventoryCU";
            int itemID = Integer.parseInt(req.getParameter("item_id"));
            int playerID = Integer.parseInt(req.getParameter("player_id"));
            float durability = Float.parseFloat(req.getParameter("durability"));

            Inventory inventory = new Inventory(0, itemID, playerID, durability, 1, 0, 0 );

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int InventoryID = Integer.parseInt(req.getParameter("inventory_id"));
                url += "?mode=u";
                url += "&inventory_id=" + InventoryID;

                inventory.setInventoryID(InventoryID);
                inventory.update();
            } else {
                inventory.create();
                new Transaction(0, null, 2, itemID, playerID, 1, "").create();;
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
