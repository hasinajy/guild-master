package controllers.handlers.dashboard;

import java.io.IOException;
import java.util.logging.Level;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Dashboard;
import utils.ExceptionHandler;

public class DashboardRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("dashboard-data", Dashboard.getData());
            req.getRequestDispatcher("/re-dashboard").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(new Exception("An error occurred while initializing the dashboard", e),
                    Level.SEVERE, resp);
        }
    }
}
