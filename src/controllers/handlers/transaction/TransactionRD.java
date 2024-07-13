package controllers.handlers.transaction;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Transaction;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class TransactionRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req)) {
                int transactionId = Integer.parseInt(req.getParameter("transaction-id"));
                Transaction.delete(transactionId);
            }

            this.setAttributes(req);
            req.getRequestDispatcher("/re-transactions").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req) throws ClassNotFoundException, SQLException {
        req.setAttribute("transaction-list", Transaction.getAll());
    }
}
