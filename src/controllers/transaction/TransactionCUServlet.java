package controllers.transaction;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Item;
import models.PlayerFull;
import models.Transaction;
import models.TransactionFull;
import models.TransactionType;
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
        try {
            String url = "TransactionCU";
            String transactionDate = req.getParameter("transaction-date");
            LocalDate localDate = LocalDate.parse(transactionDate, DateTimeFormatter.ISO_LOCAL_DATE);
            Date sqlDate = java.sql.Date.valueOf(localDate);
            int transactionTypeID = Integer.parseInt(req.getParameter("transaction-type-id"));
            int itemID = Integer.parseInt(req.getParameter("item-id"));
            int playerID = Integer.parseInt(req.getParameter("player-id"));
            int staffID = 1;

            Transaction transaction = new Transaction(0, sqlDate, transactionTypeID, itemID, playerID, staffID, "");

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int transactionID = Integer.parseInt(req.getParameter("transaction-id"));
                url += "?mode=u";
                url += "&transaction-id=" + itemID;

                transaction.setTransactionID(transactionID);
                transaction.update();
            } else {
                transaction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

}
