package controllers.type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import models.Type;
import utils.FileProcessing;

@MultipartConfig
public class TypeCUServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String typeID = req.getParameter("type_id");
                Type updatedType = Type.getByID(Integer.parseInt(typeID));
                req.setAttribute("updated_type", updatedType);
            }

            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/type-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "TypeCU";
            String name = req.getParameter("type_name");
            String imgPath = "type/";

            // Img processing
            Part imgPart = req.getPart("type_img");

            if (imgPart != null && imgPart.getSize() > 0) {
                String ogName = imgPart.getSubmittedFileName();
                String extension = FileProcessing.extractExtension(ogName);
                String newName = FileProcessing.generateUniqueFileName(extension);
                imgPath += newName;
                String savePath = getServletContext().getRealPath("/upload/type");

                try (InputStream inputStream = imgPart.getInputStream()) {
                    File imgFile = new File(savePath + File.separator + newName);
                    imgFile.createNewFile();

                    FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
                    byte[] bytes = new byte[1024];
                    int read;

                    while ((read = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, read);
                    }

                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }

            Type type = new Type(0, name, imgPath);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int typeID = Integer.parseInt(req.getParameter("type_id"));

                url += "?mode=u";
                url += "&type_id=" + typeID;

                type.setTypeID(typeID);
                type.update();
            } else {
                type.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

}
