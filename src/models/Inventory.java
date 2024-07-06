package models;

import java.sql.SQLException;

import database.PostgresResources;

public class Inventory {
    // Primary attributes
    private int inventoryId;
    private int itemId;
    private int playerId;
    private double durability;
    private int quantity;

    // Join attributes
    private int typeId;
    private int rarityId;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO inventory(item_id, player_id, durability, quantity) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE inventory SET item_id = ?, player_id = ?, durability = ?, quantity = ? WHERE inventory_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM inventory WHERE inventory_id = ?";

    /* ------------------------------ Constructors ------------------------------ */
    public Inventory(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Inventory(int inventoryId, int itemId, int playerId, double durability, int quantity, int typeId,
            int rarityId) {
        this.setInventoryId(inventoryId);
        this.setItemId(itemId);
        this.setPlayerId(playerId);
        this.setDurability(durability);
        this.setQuantity(quantity);
        this.setTypeId(typeId);
        this.setRarityId(rarityId);
    }

    /* --------------------------- Getters and setters -------------------------- */
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
        return this.typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getRarityId() {
        return this.rarityId;
    }

    public void setRarityId(int rarityId) {
        this.rarityId = rarityId;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(this.getCreateQuery());
            pg.setStmtValues(this.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Inventory getById(int inventoryId) throws ClassNotFoundException, SQLException {
        Inventory inventory = null;
        PostgresResources pg = new PostgresResources();

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

            pg.initResources(query);
            pg.setStmtValues(int.class, new Object[] { inventoryId });
            pg.executeQuery(false);

            while (pg.next()) {
                int itemId = pg.getInt("item_id");
                int playerId = pg.getInt("player_id");
                double durability = Math.round(10 * (double) pg.getFloat("durability"));
                int quantity = pg.getInt("quantity");
                int typeId = pg.getInt("type_id");
                int rarityId = pg.getInt("rarity_id");

                if (pg.getString("is_deleted").equals("t"))
                    playerId = 0;

                inventory = new Inventory(inventoryId, itemId, playerId, durability, quantity, typeId, rarityId);
            }
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return inventory;
    }

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(this.getUpdateQuery());
            pg.setStmtValues(this.getUpdateClassList(), this.getUpdateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public void update(int inventoryId) throws ClassNotFoundException, SQLException {
        this.setInventoryId(inventoryId);
        this.update();
    }

    // Delete
    public void delete() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(this.getDeleteQuery());
            pg.setStmtValues(this.getDeleteClassList(), this.getDeleteValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public static void delete(int inventoryId) throws ClassNotFoundException, SQLException {
        new Inventory(inventoryId).delete();
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Create
    private String getCreateQuery() {
        return Inventory.CREATE_QUERY;
    }

    private Class<?>[] getCreateClassList() {
        return new Class<?>[] {
                int.class,
                int.class,
                double.class,
                int.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getItemId(),
                this.getPlayerId(),
                this.getDurability(),
                this.getQuantity()
        };
    }

    // Update
    private String getUpdateQuery() {
        return Inventory.UPDATE_QUERY;
    }

    private Class<?>[] getUpdateClassList() {
        return new Class<?>[] {
                int.class,
                int.class,
                double.class,
                int.class,
                int.class
        };
    }

    private Object[] getUpdateValues() {
        return new Object[] {
                this.getItemId(),
                this.getPlayerId(),
                this.getDurability(),
                this.getQuantity(),
                this.getInventoryId()
        };
    }

    // Delete
    private String getDeleteQuery() {
        return Inventory.DELETE_QUERY;
    }

    private Class<?>[] getDeleteClassList() {
        return new Class[] { int.class };
    }

    private Object[] getDeleteValues() {
        return new Object[] { this.getInventoryId() };
    }
}
