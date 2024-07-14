package controllers.handlers.transaction;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.runner.Request;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import models.Item;
import models.Transaction;
import models.TransactionSearchCriteria;
import models.TransactionType;
import utils.AuthenticationSecurity;
import utils.ExceptionHandler;
import utils.RequestChecker;

public class TransactionRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isDeleteMode(req) && AuthenticationSecurity.isLoggedIn(req)) {
                int transactionId = Integer.parseInt(req.getParameter("transaction-id"));
                Transaction.delete(transactionId);
            }

            TransactionSearchCriteria criteria = null;

            if (RequestChecker.isSearchMode(req)) {
                criteria = TransactionSearchCriteria.getCriteriaFromRequest(req);
            }

            this.setAttributes(req, criteria);
            req.getRequestDispatcher("/re-transactions").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    private void setAttributes(HttpServletRequest req, TransactionSearchCriteria criteria) throws ClassNotFoundException, SQLException {
        req.setAttribute("transaction-type-list", TransactionType.getAll());
        req.setAttribute("item-list", Item.getAll());

        if (criteria == null) {
            req.setAttribute("transaction-list", Transaction.getAll());
        } else {
            req.setAttribute("transaction-list", Transaction.searchTransactions(criteria));
        }
    }
}
