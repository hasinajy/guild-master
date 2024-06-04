package controllers.transaction;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Transaction;
import models.TransactionFull;

public class TransactionRDServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String transactionID = req.getParameter("transaction_id");
                new Transaction(Integer.parseInt(transactionID)).delete();
            }

            req.setAttribute("transaction_list", TransactionFull.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/transactions.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
