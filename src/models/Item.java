package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class Item {
    private int itemID;
    private String name;
    private int typeID;
    private String imgPath;

    // Constructors
    public Item() {
    }

    public Item(int itemID) {
        this.itemID = itemID;
        this.name = "Default Item";
    }

    public Item(int itemID, String name, int typeID) {
        this.itemID = itemID;
        this.name = name;
        this.typeID = typeID;
    }

    public Item(int itemID, String name, int typeID, String imgPath) {
        this(itemID, name, typeID);
        this.setImgPath(imgPath);
    }

    // Getters & Setters
    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeID() {
        return this.typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // User methods
    public static Item getByID(int itemID) throws ClassNotFoundException, SQLException {
        Item item = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM item WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int typeID = rs.getInt("type_id");
                String imgPath = rs.getString("img_path");

                item = new Item(itemID, name, typeID, imgPath);
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

        return item;
    }

    public static void deleteByID(int itemID) throws ClassNotFoundException, SQLException {
        new Item(itemID).delete();
    }

    public static ArrayList<Item> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<Item> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT * FROM item";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("item_id");
                String name = rs.getString("name");
                int typeID = rs.getInt("type_id");
                String imgPath = rs.getString("img_path");

                data.add(new Item(itemID, name, typeID, imgPath));
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
            String query = "UPDATE item SET is_deleted = true WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.itemID);
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
            String query = "INSERT INTO item(name, type_id, img_path) VALUES (?, ?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.typeID);
            stmt.setString(3, this.imgPath);
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
            String query = "UPDATE item SET name = ?, type_id = ? WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.typeID);
            stmt.setInt(3, this.itemID);
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
