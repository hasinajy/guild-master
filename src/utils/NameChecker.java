package utils;

public class NameChecker extends Utility {
    public static boolean isNewImgPath(String imgPath, String prefix) {
        return (imgPath != null && !imgPath.isEmpty() && !imgPath.equalsIgnoreCase(prefix + "/"));
    }
}
