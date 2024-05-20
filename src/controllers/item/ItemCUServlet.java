package controllers.item;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;

public class ItemCUServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String itemID = req.getParameter("item_id");
                Item updatedItem = Item.getByID(Integer.parseInt(itemID));
                req.setAttribute("updated_item", updatedItem);
            }

            req.getRequestDispatcher("pages/insertion-form/item-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "ItemCU";
            int itemID = Integer.parseInt(req.getParameter("item_id"));
            String name = req.getParameter("name");
            int typeID = Integer.parseInt(req.getParameter("type_id"));

            Item item = new Item(itemID, name, typeID);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                url += "?mode=u";
                url += "&item_id=" + itemID;

                item.update();
            } else {
                item.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

}
