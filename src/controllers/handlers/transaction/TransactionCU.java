package controllers.handlers.transaction;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Item;
import models.Player;
import models.Staff;
import models.Transaction;
import models.TransactionType;
import utils.ExceptionHandler;
import utils.RequestChecker;
import utils.UrlUtils;
import jakarta.servlet.ServletException;

public class TransactionCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                int transactionId = Integer.parseInt(req.getParameter("transaction-id"));
                req.setAttribute("updated-transaction", Transaction.getById(transactionId));
            }

            this.setAttributes(req);
            req.getRequestDispatcher("TransactionForm").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "TransactionCU";

            String transactionDate = req.getParameter("transaction-date");
            LocalDate localDate = LocalDate.parse(transactionDate, DateTimeFormatter.ISO_LOCAL_DATE);
            Date sqlDate = java.sql.Date.valueOf(localDate);

            int transactionTypeId = Integer.parseInt(req.getParameter("transaction-type-id"));
            int itemId = Integer.parseInt(req.getParameter("item-id"));
            int playerId = Integer.parseInt(req.getParameter("player-id"));
            int staffId = 1;

            Transaction transaction = new Transaction();

            TransactionType transactionType = new TransactionType();
            transactionType.setTransactionTypeId(transactionTypeId);

            Item item = new Item();
            item.setItemId(itemId);

            Player player = new Player();
            player.setPlayerId(playerId);

            Staff staff = new Staff();
            staff.setStaffId(staffId);

            transaction.setTransactionType(transactionType);
            transaction.setDate(sqlDate);
            transaction.setItem(item);
            transaction.setPlayer(player);

            // TODO: Add note from the client
            transaction.setNote("");

            if (RequestChecker.isUpdateMode(req)) {
                int transactionId = Integer.parseInt(req.getParameter("transaction-id"));

                url = UrlUtils.prepareUpdateURL(url, "transaction", transactionId);
                transaction.update(transactionId);
            } else {
                transaction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("transaction-type-list", TransactionType.getAll());
        req.setAttribute("item-list", Item.getAll());
        req.setAttribute("player-list", Player.getAll());
    }
}
