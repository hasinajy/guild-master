package utils;

import jakarta.servlet.http.Part;

public class FileProcessing extends Utility {
    public static String extractExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index > 0 ? fileName.substring(index) : "";
    }

    public static String extractExtension(Part imgPart) {
        return extractExtension(imgPart.getSubmittedFileName());
    }

    public static String generateUniqueFileName(String extension) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + extension;
    }
}
