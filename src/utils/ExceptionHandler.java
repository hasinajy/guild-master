package utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler extends Utility {
    public static void handleException(Exception e, HttpServletResponse resp, boolean isGet) {
        String msg;

        if (e instanceof NumberFormatException) {
            // TODO: All numeric values should be handled
            msg = "Invalid Id value from the request parameters";
        } else if (e instanceof ServletException || e instanceof IOException) {
            if (isGet) {
                msg = "An error occurred while forwarding resources";
            } else {
                msg = "An error occurred while processing the image";
            }
        } else if (e instanceof ClassNotFoundException || e instanceof SQLException) {
            msg = "An error occurred while trying to update the database";
        } else {
            msg = "An unexpected error occurred while processing faction form data";
        }

        ExceptionHandler.handleException(new Exception(msg, e), Level.SEVERE, resp);
    }

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

    public static void handleException(Exception e, Level level, HttpServletResponse resp) {
        logger.log(level, e.getMessage(), e);

        try {
            if (!resp.isCommitted()) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } catch (IOException ioException) {
            logger.log(Level.SEVERE, "Error sending error response to client", ioException);
        }
    }
}
