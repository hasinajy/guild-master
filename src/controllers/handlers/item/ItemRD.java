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
                Item.deleteById(itemId);
            }

            String searchKeyword = null;

            if (RequestChecker.isSearchMode(req)) {
                searchKeyword = req.getParameter("item-name");
            }

            this.setAttributes(req, searchKeyword);
            req.getRequestDispatcher("/re-items").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req, String searchKeyword) throws ClassNotFoundException, SQLException {
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            req.setAttribute("item-list", Item.searchItem(searchKeyword));
        } else {
            req.setAttribute("item-list", Item.getAll());
        }
    }
}
