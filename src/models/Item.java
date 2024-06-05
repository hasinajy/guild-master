package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;

public class Item {
    private int itemID;
    private String name;
    private int typeID;
    private String sType;
    private int rarityID;
    private String sRarity;
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

    public Item(int itemID, String name, int typeID, int rarityID, String imgPath) {
        this.setItemID(itemID);
        this.setName(name);
        this.setTypeID(typeID);
        this.setRarityID(rarityID);
        this.setImgPath(imgPath);
    }

    public Item(int itemID, String name, int typeID, String sType, int rarityID, String sRarity, String imgPath) {
        this(itemID, name, typeID, imgPath);
        this.setType(sType);
        this.setRarityID(rarityID);
        this.setRarity(sRarity);
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
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getType() {
        return sType;
    }

    public void setType(String sType) {
        this.sType = sType;
    }

    public int getRarityID() {
        return rarityID;
    }

    public void setRarityID(int rarityID) {
        this.rarityID = rarityID;
    }

    public String getRarity() {
        return sRarity;
    }

    public void setRarity(String sRarity) {
        this.sRarity = sRarity;
    }

    public String getImgPath() {
        return imgPath;
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
            String query = "SELECT " +
                    "item.item_id AS item_id, " +
                    "item.name AS item_name, " +
                    "item.type_id AS type_id, " +
                    "type.name AS type_name, " +
                    "item.rarity_id AS rarity_id, " +
                    "rarity.name AS rarity_name, " +
                    "item.img_path AS img_path, " +
                    "item.is_deleted AS is_deleted " +
                    "FROM " +
                    "item " +
                    "LEFT JOIN type ON item.type_id = type.type_id " +
                    "LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id " +
                    "WHERE " +
                    "(is_deleted = false AND item_id = ?) " +
                    "ORDER BY " +
                    "item_name";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, itemID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int typeID = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityID = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                item = new Item(itemID, name, typeID, type, rarityID, rarity, imgPath);
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
            String query = "SELECT " +
                    "item.item_id AS item_id, " +
                    "item.name AS item_name, " +
                    "item.type_id AS type_id, " +
                    "type.name AS type_name, " +
                    "item.rarity_id AS rarity_id, " +
                    "rarity.name AS rarity_name, " +
                    "item.img_path AS img_path, " +
                    "item.is_deleted AS is_deleted " +
                    "FROM " +
                    "item " +
                    "LEFT JOIN type ON item.type_id = type.type_id " +
                    "LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id " +
                    "WHERE " +
                    "is_deleted = false " +
                    "ORDER BY " +
                    "item_name";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("item_id");
                String name = rs.getString("item_name");
                int typeID = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityID = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                data.add(new Item(itemID, name, typeID, type, rarityID, rarity, imgPath));
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
            String query = "INSERT INTO item(name, type_id, rarity_id, img_path) VALUES (?, ?, ?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.typeID);
            stmt.setInt(3, this.rarityID);
            stmt.setString(4, this.imgPath);
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
            String query = "UPDATE item SET name = ?, type_id = ?, rarity_id = ?, img_path = ? WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.typeID);
            stmt.setInt(3, this.rarityID);
            stmt.setString(4, this.imgPath);
            stmt.setInt(5, this.itemID);
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

    public static List<Item> searchItem(String sName) throws ClassNotFoundException, SQLException {
        List<Item> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            final String BASE_QUERY = "SELECT " +
                    "item.item_id AS item_id, " +
                    "item.name AS item_name, " +
                    "item.type_id AS type_id, " +
                    "type.name AS type_name, " +
                    "item.rarity_id AS rarity_id, " +
                    "rarity.name AS rarity_name, " +
                    "item.img_path AS img_path, " +
                    "item.is_deleted AS is_deleted " +
                    "FROM " +
                    "item " +
                    "LEFT JOIN type ON item.type_id = type.type_id " +
                    "LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id " +
                    "WHERE " +
                    "is_deleted = false";

            StringBuilder query = new StringBuilder(BASE_QUERY);
            List<Object> parameters = new ArrayList<>();

            if (sName != null && !sName.isEmpty()) {
                query.append(" AND item.name ILIKE ?");
                parameters.add("%" + sName + "%");
            }

            query.append(" ORDER BY item_name");

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query.toString());

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("item_id");
                String name = rs.getString("item_name");
                int typeID = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityID = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                data.add(new Item(itemID, name, typeID, type, rarityID, rarity, imgPath));
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
