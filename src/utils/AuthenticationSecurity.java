package utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthenticationSecurity extends Utility {
    public static boolean isLoggedIn(HttpServletRequest req) {
        HttpSession session = req.getSession();

        return (session.getAttribute("username") != null);
    }
}
