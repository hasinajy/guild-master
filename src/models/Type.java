package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class Type {
    private int typeID;
    private String name;
    private String imgPath;

    // Constructors
    public Type() {
    }

    public Type(int typeID) {
        this.typeID = typeID;
        this.name = "Default Type";
    }

    public Type(int typeID, String name, String imgPath) {
        this.typeID = typeID;
        this.name = name;
        this.setImgPath(imgPath);
    }

    // Getters & Setters
    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // User methods
    public static Type getByID(int typeID) throws ClassNotFoundException, SQLException {
        Type type = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM type WHERE type_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, typeID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                type = new Type(typeID, name, imgPath);
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

        return type;
    }

    public static void deleteByID(int typeID) throws ClassNotFoundException, SQLException {
        new Type(typeID).delete();
    }

    public static ArrayList<Type> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<Type> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM type";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int typeID = rs.getInt("type_id");
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                data.add(new Type(typeID, name, imgPath));
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

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM type WHERE type_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.typeID);
            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public void create() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO type(name, img_path) VALUES (?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.getName());
            stmt.setString(2, this.getImgPath());
            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }

    public void update() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Postgres.getInstance().getConnection();

            
            if (this.getImgPath().equals("type/")) {
                String query = "UPDATE type SET name = ? WHERE type_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setInt(2, this.getTypeID());
            } else {
                String query = "UPDATE type SET name = ?, img_path = ? WHERE type_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setString(2, this.getImgPath());
                stmt.setInt(3, this.getTypeID());
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (stmt != null)
                stmt.close();

            if (conn != null)
                conn.close();
        }
    }
}
