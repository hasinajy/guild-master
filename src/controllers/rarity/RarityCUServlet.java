package controllers.rarity;

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

import models.Rarity;
import utils.FileProcessing;

@MultipartConfig
public class RarityCUServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String rarityID = req.getParameter("rarity_id");
                Rarity updatedRarity = Rarity.getByID(Integer.parseInt(rarityID));
                req.setAttribute("updated_rarity", updatedRarity);
            }

            req.getRequestDispatcher("pages/insertion-form/rarity-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "RarityCU";
            String name = req.getParameter("rarity_name");
            String imgPath = "rarity/";

            // Img processing
            Part imgPart = req.getPart("rarity_img");

            if (imgPart != null && imgPart.getSize() > 0) {
                String ogName = imgPart.getSubmittedFileName();
                String extension = FileProcessing.extractExtension(ogName);
                String newName = FileProcessing.generateUniqueFileName(extension);
                imgPath += newName;
                String savePath = getServletContext().getRealPath("/upload/rarity");

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

            Rarity rarity = new Rarity(0, name, imgPath);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int rarityID = Integer.parseInt(req.getParameter("rarity_id"));

                url += "?mode=u";
                url += "&rarity_id=" + rarityID;

                rarity.setRarityID(rarityID);
                rarity.update();
            } else {
                rarity.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

}
