package utils;

public class FileProcessing {
    public static String extractExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index > 0 ? fileName.substring(index) : "";
    }

    public static String generateUniqueFileName(String extension) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + extension;
    }
}
