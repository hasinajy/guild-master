package controllers.handlers.player;

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
import models.Player;
import utils.FileProcessing;
import models.Gender;

@MultipartConfig
public class PlayerCU extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                String playerID = req.getParameter("player_id");
                Player updatedPlayer = Player.getByID(Integer.parseInt(playerID));
                req.setAttribute("updated_player", updatedPlayer);

                if (updatedPlayer == null) {
                    resp.sendRedirect("Player");
                }
            }

            req.setAttribute("gender_list", Gender.getAll());
            req.setAttribute("faction_list", Faction.getAll());
            req.getRequestDispatcher("WEB-INF/jsp/insertion-form/player-form.jsp").forward(req, resp);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String url = "PlayerCU";
            String username = req.getParameter("username");
            String characterName = req.getParameter("character_name");
            int genderID = Integer.parseInt(req.getParameter("gender_id"));
            int level = Integer.parseInt(req.getParameter("level"));
            int factionID = Integer.parseInt(req.getParameter("faction_id"));
            String imgPath = "player/";

            // Img processing
            Part imgPart = req.getPart("player_img");

            if (imgPart != null && imgPart.getSize() > 0) {
                String ogName = imgPart.getSubmittedFileName();
                String extension = FileProcessing.extractExtension(ogName);
                String newName = FileProcessing.generateUniqueFileName(extension);
                imgPath += newName;
                String savePath = getServletContext().getRealPath("/uploads/player");

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

            Player player = new Player(0, username, characterName, genderID, level, factionID, "", imgPath);

            if (req.getParameter("mode") != null && req.getParameter("mode").equals("u")) {
                int playerID = Integer.parseInt(req.getParameter("player_id"));
                url += "?mode=u";
                url += "&player_id=" + playerID;

                player.setPlayerID(playerID);
                player.update();
            } else {
                player.create();
            }

            resp.sendRedirect(url);
        } catch (Exception err) {
            err.printStackTrace(resp.getWriter());
        }
    }
}
