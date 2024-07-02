package utils;

public class UrlUtils extends Utility {
    public static String prepareUpdateURL(String url, String modelName, int id) {
        url += "?mode=u";
        url += "&" + modelName + "-id=" + id;
        return url;
    }
}
