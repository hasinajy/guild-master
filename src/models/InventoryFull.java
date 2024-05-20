package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Postgres;

public class InventoryFull {
    private int inventoryID;
    private int itemID;
    private String itemName;
    private String playerCharacterName;
    private double durability;
    private int quantity;
    private int typeID;
    private String typeName;
    private int rarityID;
    private String rarityName;
    private String imgPath;

    public static final int MIN_DURABILITY = 0;
    public static final int MAX_DURABILITY = 100;

    // Constructors
    public InventoryFull(int inventoryID, String itemName, String playerCharacterName, double durability, int quantity,
            String typeName, String rarityName, String imgPath) {
        this.inventoryID = inventoryID;
        this.itemName = itemName;
        this.playerCharacterName = playerCharacterName;
        this.durability = durability;
        this.quantity = quantity;
        this.typeName = typeName;
        this.rarityName = rarityName;
        this.setImgPath(imgPath);
    }

    public InventoryFull(int inventoryID, int itemID, String itemName, String playerCharacterName, double durability,
            int quantity, int typeID, String typeName, int rarityID, String rarityName, String imgPath) {
        this.inventoryID = inventoryID;
        this.itemID = itemID;
        this.itemName = itemName;
        this.playerCharacterName = playerCharacterName;
        this.durability = durability;
        this.quantity = quantity;
        this.typeID = typeID;
        this.typeName = typeName;
        this.rarityID = rarityID;
        this.rarityName = rarityName;
        this.imgPath = imgPath;
    }

    // Getters & Setters
    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPlayerCharacterName() {
        return playerCharacterName;
    }

    public void setPlayerCharacterName(String playerCharacterName) {
        this.playerCharacterName = playerCharacterName;
    }

    public double getDurability() {
        return durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getRarityID() {
        return rarityID;
    }

    public void setRarityID(int rarityID) {
        this.rarityID = rarityID;
    }

    public String getRarityName() {
        return rarityName;
    }

    public void setRarityName(String rarityName) {
        this.rarityName = rarityName;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    // User methods
    public static InventoryFull getByID(int factionID) throws ClassNotFoundException, SQLException {
        InventoryFull inventory = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    inventory.inventory_id AS inventory_id,\r\n" + //
                    "    item.item_id AS item_id,\r\n" + //
                    "    item.name AS item_name,\r\n" + //
                    "    player.character_name AS character_name,\r\n" + //
                    "    durability,\r\n" + //
                    "    quantity,\r\n" + //
                    "    type.type_id AS type_id,\r\n" + //
                    "    type.name AS type_name,\r\n" + //
                    "    rarity.rarity_id AS rarity_id,\r\n" + //
                    "    rarity.name AS rarity_name,\r\n" + //
                    "    item.img_path AS img_path,\r\n" + //
                    "    player.is_deleted AS player_deleted\r\n" + //
                    "FROM\r\n" + //
                    "    inventory\r\n" + //
                    "    JOIN item ON inventory.item_id = item.item_id\r\n" + //
                    "    LEFT JOIN type ON item.type_id = type.type_id\r\n" + //
                    "    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id\r\n" + //
                    "    JOIN player ON inventory.player_id = player.player_id\r\n" + //
                    "WHERE\r\n" + //
                    "    item.is_deleted = false\r\n" + //
                    "ORDER BY inventory_id ASC;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, factionID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int inventoryID = rs.getInt("inventory_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = 10 * (double) rs.getFloat("durability");
                int quantity = rs.getInt("quantity");
                String typeName = rs.getString("type_name");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                if (rs.getString("player_deleted").equals("t"))
                    characterName = null;

                inventory = new InventoryFull(inventoryID, itemName, characterName, durability, quantity, typeName,
                        rarityName, imgPath);
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

        return inventory;
    }

    public static ArrayList<InventoryFull> getAll() throws ClassNotFoundException, SQLException {
        ArrayList<InventoryFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    inventory.inventory_id AS inventory_id,\r\n" + //
                    "    item.item_id AS item_id,\r\n" + //
                    "    item.name AS item_name,\r\n" + //
                    "    player.character_name AS character_name,\r\n" + //
                    "    durability,\r\n" + //
                    "    quantity,\r\n" + //
                    "    type.type_id AS type_id,\r\n" + //
                    "    type.name AS type_name,\r\n" + //
                    "    rarity.rarity_id AS rarity_id,\r\n" + //
                    "    rarity.name AS rarity_name,\r\n" + //
                    "    item.img_path AS img_path,\r\n" + //
                    "    player.is_deleted AS player_deleted\r\n" + //
                    "FROM\r\n" + //
                    "    inventory\r\n" + //
                    "    JOIN item ON inventory.item_id = item.item_id\r\n" + //
                    "    LEFT JOIN type ON item.type_id = type.type_id\r\n" + //
                    "    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id\r\n" + //
                    "    JOIN player ON inventory.player_id = player.player_id\r\n" + //
                    "WHERE\r\n" + //
                    "    item.is_deleted = false\r\n" + //
                    "ORDER BY inventory_id ASC;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int inventoryID = rs.getInt("inventory_id");
                int itemID = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = Math.round(10 * (double) rs.getFloat("durability"));
                int quantity = rs.getInt("quantity");
                int typeID = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                int rarityID = rs.getInt("rarity_id");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                if (rs.getString("player_deleted").equals("t"))
                    characterName = null;

                data.add(
                        new InventoryFull(inventoryID, itemID, itemName, characterName, durability, quantity, typeID,
                                typeName, rarityID, rarityName, imgPath));
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

    public static ArrayList<InventoryFull> search(String sItemName, String sCharName, int minD, int maxD, int sTypeID,
            int sRarityID, int minQtt, int maxQtt) throws ClassNotFoundException, SQLException {

        ArrayList<InventoryFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = buildQuery(sItemName, sCharName, minD, maxD, sTypeID, sRarityID, minQtt, maxQtt);

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int inventoryID = rs.getInt("inventory_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = 10 * (double) rs.getFloat("durability");
                int quantity = rs.getInt("quantity");
                String typeName = rs.getString("type_name");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                data.add(
                        new InventoryFull(inventoryID, itemName, characterName, durability, quantity, typeName,
                                rarityName, imgPath));
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

    private static String buildQuery(String sItemName, String sCharName, int minD, int maxD, int sTypeID,
            int sRarityID, int minQtt, int maxQtt) {
        String query = "SELECT\r\n" + //
                "    inventory_id,\r\n" + //
                "    item.name AS item_name,\r\n" + //
                "    player.character_name AS character_name,\r\n" + //
                "    durability,\r\n" + //
                "    quantity,\r\n" + //
                "    type.name AS type_name,\r\n" + //
                "    rarity.name AS rarity_name\r\n" + //
                "FROM\r\n" + //
                "    inventory\r\n" + //
                "    JOIN item ON inventory.item_id = item.item_id\r\n" + //
                "    JOIN type ON item.type_id = type.type_id\r\n" + //
                "    JOIN player ON inventory.player_id = player.player_id\r\n" + //
                "    JOIN rarity ON inventory.rarity_id = rarity.rarity_id\r\n" + //
                "WHERE\r\n" + //
                "    1 = 1";
        StringBuilder queryBuilder = new StringBuilder(query);

        if (sCharName != null) {
            queryBuilder.append(" AND character_name LIKE '%").append(sItemName).append("%'");
        }

        if (sItemName != null) {
            queryBuilder.append(" AND item_name LIKE '%").append(sItemName).append("%'");
        }

        if (minD != -1 && maxD != -1) {
            queryBuilder.append(" AND durability BETWEEN ").append(minD).append(" AND ").append(maxD);
        } else if (minD != -1) {
            queryBuilder.append(" AND durability >= ").append(minD);
        } else if (maxD != -1) {
            queryBuilder.append(" AND durability <= ").append(maxD);
        }

        if (sTypeID != -1) {
            queryBuilder.append(" AND type_id = ").append(sTypeID);
        }

        if (sRarityID != -1) {
            queryBuilder.append(" AND rarity_id = ").append(sRarityID);
        }

        if (minQtt != -1 && maxQtt != -1) {
            queryBuilder.append(" AND quantity BETWEEN ").append(minQtt).append(" AND ").append(maxQtt);
        } else if (minQtt != -1) {
            queryBuilder.append(" AND quantity >= ").append(minQtt);
        } else if (maxQtt != -1) {
            queryBuilder.append(" AND quantity <= ").append(maxQtt);
        }

        return queryBuilder.toString();
    }
}
