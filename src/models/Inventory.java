package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.Postgres;

public class Inventory {
    private int inventoryId;
    private int itemId;
    private int playerId;
    private double durability;
    private int quantity;
    private int typeID;
    private int rarityId;

    // Constructors
    public Inventory(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Inventory(int inventoryId, int itemId, int playerId, double durability, int quantity, int typeID,
            int rarityId) {
        this.setInventoryId(inventoryId);
        this.setItemId(itemId);
        this.setPlayerId(playerId);
        this.setDurability(durability);
        this.setQuantity(quantity);
        this.setTypeId(typeID);
        this.setRarityId(rarityId);
    }

    // Getters & Setters
    public int getInventoryId() {
        return this.inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public double getDurability() {
        return this.durability;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTypeId() {
        return this.typeID;
    }

    public void setTypeId(int typeID) {
        this.typeID = typeID;
    }

    public int getRarityId() {
        return this.rarityId;
    }

    public void setRarityId(int rarityId) {
        this.rarityId = rarityId;
    }

    // User methods
    public static Inventory getById(int inventoryId) throws ClassNotFoundException, SQLException {
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
            stmt.setInt(1, inventoryId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int playerId = rs.getInt("player_id");
                double durability = Math.round(10 * (double) rs.getFloat("durability"));
                int quantity = rs.getInt("quantity");
                int typeID = rs.getInt("type_id");
                int rarityId = rs.getInt("rarity_id");

                if (rs.getString("is_deleted").equals("t"))
                    playerId = 0;

                inventory = new Inventory(inventoryId, itemId, playerId, durability, quantity, typeID, rarityId);
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
            stmt.setInt(1, this.getInventoryId());
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
            stmt.setInt(1, this.getItemId());
            stmt.setInt(2, this.getPlayerId());
            stmt.setFloat(3, (float) this.getDurability());
            stmt.setInt(4, this.getQuantity());
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
            stmt.setInt(1, this.getItemId());
            stmt.setInt(2, this.getPlayerId());
            stmt.setFloat(3, (float) this.getDurability());
            stmt.setInt(4, this.getQuantity());
            stmt.setInt(5, this.getInventoryId());
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

    public void update(int inventoryId) throws ClassNotFoundException, SQLException {
        this.setInventoryId(inventoryId);
        this.update();
    }
}
