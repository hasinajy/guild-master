package controllers.handlers.type;

import java.io.IOException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import models.Type;

public class TypeRD extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("d")) {
                String typeID = req.getParameter("type_id");
                new Type(Integer.parseInt(typeID)).delete();
            }

            req.setAttribute("type_list", Type.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/types.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
