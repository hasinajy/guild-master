package utils;

import java.sql.Date;

public class DateUtils extends Utility {
    public static Date getCurrentDate() {
        java.util.Date now = new java.util.Date();
        return new Date(now.getTime());
    }
}
