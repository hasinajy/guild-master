package controllers.handlers.item;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Item;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class ItemRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req)) {
                int itemId = Integer.parseInt(req.getParameter("item-id"));
                Item.deleteByID(itemId);
            }

            this.setAttributes(req);
            req.getRequestDispatcher("Items").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("item-list", Item.getAll());
    }
}
