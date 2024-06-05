package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Staff {
    private int staffID;
    private String username;
    private String characterName;

    // Constructors
    public Staff() {
    }

    public Staff(int staffID) {
        this.staffID = staffID;
        this.username = "Default Staff";
    }

    public Staff(int staffID, String username, String characterName) {
        this.staffID = staffID;
        this.username = username;
        this.characterName = characterName;
    }

    // Getters & Setters
    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    // User methods
    public ArrayList<Faction> getAll() {
        ArrayList<Faction> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        } catch (Exception e) {
            // Database operation error messages here
        } finally {
            try {

                if (conn != null)
                    conn.close();

                if (stmt != null)
                    stmt.close();

                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                // Closing error messages here
            }
        }

        return data;
    }

    public void delete() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        } catch (Exception e) {
            // Database operation error messages here
        } finally {
            try {

                if (conn != null)
                    conn.close();

                if (stmt != null)
                    stmt.close();

                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                // Closing error messages here
            }
        }
    }

    public void create() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        } catch (Exception e) {
            // Database operation error messages here
        } finally {
            try {

                if (conn != null)
                    conn.close();

                if (stmt != null)
                    stmt.close();

                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                // Closing error messages here
            }
        }
    }

    public void update() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

        } catch (Exception e) {
            // Database operation error messages here
        } finally {
            try {

                if (conn != null)
                    conn.close();

                if (stmt != null)
                    stmt.close();

                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                // Closing error messages here
            }
        }
    }
}
