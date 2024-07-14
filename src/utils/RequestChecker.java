package utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestChecker extends Utility {
    public static boolean isUpdateMode(HttpServletRequest req) {
        return (req.getParameter("mode") != null && req.getParameter("mode").equals("u"));
    }

    public static boolean isDeleteMode(HttpServletRequest req) {
        return (req.getParameter("mode") != null && req.getParameter("mode").equals("d"));
    }

    public static boolean isWithdrawMode(HttpServletRequest req) {
        return (req.getParameter("type") != null && req.getParameter("type").equals("w"));
    }

    public static boolean isSearchMode(HttpServletRequest req) {
        return (req.getParameter("mode") != null && req.getParameter("mode").equals("s"));
    }
}
