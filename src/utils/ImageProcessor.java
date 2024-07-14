package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class ImageProcessor extends Utility {
    public static String processImage(HttpServlet servlet, HttpServletRequest req, String modelName)
            throws IOException, ServletException {
        Part imgPart = req.getPart(modelName + "-img");
        String imgPath = makePath(modelName);

        if (isValidImg(imgPart)) {
            String newName = FileProcessing.generateUniqueFileName(FileProcessing.extractExtension(imgPart));
            String savePath = makeSavePath(servlet, modelName);

            imgPath += newName;
            saveImage(imgPart, newName, savePath);
        }

        return imgPath;
    }

    private static void saveImage(Part imgPart, String newName, String savePath) throws IOException {
        try (InputStream inputStream = imgPart.getInputStream()) {
            File imgFile = new File(savePath + File.separator + newName);

            if (imgFile.createNewFile()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(imgFile)) {
                    byte[] bytes = new byte[1024];
                    int read;

                    while ((read = inputStream.read(bytes)) != -1) {
                        fileOutputStream.write(bytes, 0, read);
                    }
                }
            }
        }
    }

    private static boolean isValidImg(Part imgPart) {
        return (imgPart != null && imgPart.getSize() > 0);
    }

    private static String makePath(String prefix) {
        return prefix + "/";
    }

    private static String makeSavePath(HttpServlet servlet, String suffix) {
        return servlet.getServletContext().getRealPath("/uploads/" + suffix);
    }
}
