package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;

public class Staff {
    private int staffId;
    private String username;
    private String characterName;

    /* ------------------------------ Constructors ------------------------------ */
    public Staff() {
    }

    public Staff(int staffId, String username, String characterName) {
        this.setStaffId(staffId);
        this.setUsername(username);
        this.setCharacterName(characterName);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
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

    /* ---------------------------- Database methods ---------------------------- */
    public List<Faction> getAll() throws SQLException {
        List<Faction> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Implement the getAll method of the Staff model
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
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
