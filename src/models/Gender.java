package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class Gender {
    private int genderID;
    private String name;

    // Constructors
    public Gender() {
    }

    public Gender(int genderID) {
        this.genderID = genderID;
        this.name = "Default Gender";
    }

    public Gender(int genderID, String name) {
        this.genderID = genderID;
        this.name = name;
    }

    // Getters & Setters
    public int getGenderID() {
        return genderID;
    }

    public void setGenderID(int genderID) {
        this.genderID = genderID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Class methods
    public static ArrayList<Gender> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<Gender> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM gender";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int genderID = rs.getInt("gender_id");
                String name = rs.getString("name");

                data.add(new Gender(genderID, name));
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (rs != null)
                rs.close();

            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }

        return data;
    }
}
