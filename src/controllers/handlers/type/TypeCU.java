package controllers.handlers.type;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Type;
import utils.AuthenticationSecurity;
import utils.ExceptionHandler;
import utils.ImageProcessor;
import utils.RequestChecker;
import utils.UrlUtils;

@MultipartConfig
public class TypeCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!AuthenticationSecurity.isLoggedIn(req)) {
                resp.sendRedirect("types");
            }

            if (RequestChecker.isUpdateMode(req)) {
                int typeId = Integer.parseInt(req.getParameter("type-id"));
                req.setAttribute("updated-type", Type.getById(typeId));
            }

            req.getRequestDispatcher("/type-form").forward(req, resp);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, true);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!AuthenticationSecurity.isLoggedIn(req)) {
                resp.sendRedirect("types");
            }

            String url = "type-cu";
            String name = req.getParameter("type-name");
            String imgPath = ImageProcessor.processImage(this, req, "type");

            Type type = new Type(0, name, imgPath);

            if (RequestChecker.isUpdateMode(req)) {
                int typeId = Integer.parseInt(req.getParameter("type-id"));

                url = UrlUtils.prepareUpdateURL(url, "type", typeId);
                type.update(typeId);
            } else {
                type.create();
            }

            resp.sendRedirect(url);
        } catch (Exception e) {
            ExceptionHandler.handleException(e, resp, false);
        }
    }
}
