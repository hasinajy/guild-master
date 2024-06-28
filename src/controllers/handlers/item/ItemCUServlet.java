package controllers.handlers.item;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import models.Item;
import models.Rarity;
import models.Type;
import utils.FileProcessing;

@MultipartConfig
public class ItemCUServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String itemID = req.getParameter("item-id");
                Item updatedItem = Item.getByID(Integer.parseInt(itemID));
                req.setAttribute("updated-item", updatedItem);
            }

            req.setAttribute("rarity-list", Rarity.getAll());
            req.setAttribute("type-list", Type.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/item-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "ItemCU";
            String name = req.getParameter("item-name");
            int typeID = Integer.parseInt(req.getParameter("type-id"));
            int rarityID = Integer.parseInt(req.getParameter("rarity-id"));
            String imgPath = "item/";

            // Img processing
            Part imgPart = req.getPart("item-img");

            if (imgPart != null && imgPart.getSize() > 0) {
                String ogName = imgPart.getSubmittedFileName();
                String extension = FileProcessing.extractExtension(ogName);
                String newName = FileProcessing.generateUniqueFileName(extension);
                imgPath += newName;
                String savePath = getServletContext().getRealPath("/uploads/item");

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

            Item item = new Item(0, name, typeID, rarityID, imgPath);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int itemID = Integer.parseInt(req.getParameter("item-id"));
                url += "?mode=u";
                url += "&item-id=" + itemID;

                item.setItemID(itemID);
                item.update();
            } else {
                item.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
