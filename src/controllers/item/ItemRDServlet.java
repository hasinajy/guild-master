package controllers.item;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;

public class ItemRDServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String itemID = req.getParameter("item-id");
                Item.deleteByID(Integer.parseInt(itemID));
            }

            req.setAttribute("item-list", Item.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/items.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
