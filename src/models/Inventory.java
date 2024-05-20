package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Postgres;

public class Inventory {
    private int inventoryID;
    private int itemID;
    private int playerID;
    private double durability;
    private int quantity;
    private int typeID;
    private int rarityID;

    // Constructors
    public Inventory(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public Inventory(int inventoryID, int itemID, int playerID, double durability, int quantity, int typeID,
            int rarityID) {
        this.setInventoryID(inventoryID);
        this.setItemID(itemID);
        this.setPlayerID(playerID);
        this.setDurability(durability);
        this.setQuantity(quantity);
        this.setTypeID(typeID);
        this.setRarityID(rarityID);
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

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
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

    public int getTypeID() {
        return this.typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getRarityID() {
        return rarityID;
    }

    public void setRarityID(int rarityID) {
        this.rarityID = rarityID;
    }

    // User methods
    public static Inventory getByID(int inventoryID) throws ClassNotFoundException, SQLException {
        Inventory inventory = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT\r\n" + //
                    "    inventory_id,\r\n" + //
                    "    item.item_id AS item_id,\r\n" + //
                    "    inventory.player_id AS player_id,\r\n" + //
                    "    durability,\r\n" + //
                    "    quantity,\r\n" + //
                    "    item.type_id AS type_id,\r\n" + //
                    "    item.rarity_id AS rarity_id,\r\n" + //
                    "    player.is_deleted AS is_deleted\r\n" + //
                    "FROM\r\n" + //
                    "    inventory\r\n" + //
                    "    JOIN item ON inventory.item_id = item.item_id\r\n" + //
                    "    JOIN player ON inventory.player_id = player.player_id\r\n" + //
                    "WHERE\r\n" + //
                    "    inventory_id = ?;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, inventoryID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int itemID = rs.getInt("item_id");
                int playerID = rs.getInt("player_id");
                double durability = Math.round(10 * (double) rs.getFloat("durability"));
                int quantity = rs.getInt("quantity");
                int typeID = rs.getInt("type_id");
                int rarityID = rs.getInt("rarity_id");

                if (rs.getString("is_deleted").equals("t"))
                    playerID = 0;

                inventory = new Inventory(inventoryID, itemID, playerID, durability, quantity, typeID, rarityID);
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

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM inventory WHERE inventory_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.inventoryID);
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
            String query = "INSERT INTO inventory(item_id, player_id, durability, quantity)"
                    + " VALUES (?, ?, ?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.itemID);
            stmt.setInt(2, this.playerID);
            stmt.setFloat(3, (float) this.durability);
            stmt.setInt(4, this.quantity);
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
            String query = "UPDATE inventory SET"
                    + " item_id = ?, player_id = ?, durability = ?, quantity = ?"
                    + " WHERE inventory_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.itemID);
            stmt.setInt(2, this.playerID);
            stmt.setFloat(3, (float) this.durability);
            stmt.setInt(4, this.quantity);
            stmt.setInt(5, this.inventoryID);
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
