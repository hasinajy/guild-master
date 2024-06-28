package controllers.handlers.faction;

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

import models.Faction;
import utils.FileProcessing;
import utils.RequestChecker;

@MultipartConfig
public class FactionCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (RequestChecker.isUpdateMode(req)) {
                String factionID = req.getParameter("faction-id");
                Faction updatedFaction = Faction.getByID(Integer.parseInt(factionID));
                req.setAttribute("updated-faction", updatedFaction);
            }

            req.getRequestDispatcher("FactionForm").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "faction-cu";
            String name = req.getParameter("faction-name");
            String imgPath = "faction/";

            // Img processing
            Part imgPart = req.getPart("faction-img");

            if (imgPart != null && imgPart.getSize() > 0) {
                String ogName = imgPart.getSubmittedFileName();
                String extension = FileProcessing.extractExtension(ogName);
                String newName = FileProcessing.generateUniqueFileName(extension);
                imgPath += newName;
                String savePath = getServletContext().getRealPath("/uploads/faction");

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

            Faction faction = new Faction(0, name, imgPath);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int factionID = Integer.parseInt(req.getParameter("faction-id"));

                url += "?mode=u";
                url += "&faction-id=" + factionID;

                faction.setFactionID(factionID);
                faction.update();
            } else {
                faction.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
