package controllers.transaction;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Item;
import models.PlayerFull;
import models.Rarity;
import models.Transaction;
import models.TransactionFull;
import models.Type;
import jakarta.servlet.ServletException;

public class TransactionCUServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String transactionID = req.getParameter("transaction-id");
                TransactionFull updatedTransaction = TransactionFull.getByID(Integer.parseInt(transactionID));
                req.setAttribute("updated-transaction", updatedTransaction);
            }

            req.setAttribute("transaction-type-list", TransactionType.getAll());
            req.setAttribute("item-list", Item.getAll());
            req.setAttribute("player-list", PlayerFull.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/transaction-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doPost(req, resp);
    }

}
