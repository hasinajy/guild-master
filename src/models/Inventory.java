package models;

import java.sql.SQLException;

import database.PostgresResources;

public class Inventory {
    private int inventoryId;
    private int durability;
    private int quantity;
    private Item item;
    private Player player;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO inventory(item_id, player_id, durability, quantity) VALUES (?, ?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM v_inventory";
    private static final String UPDATE_QUERY = "UPDATE inventory SET item_id = ?, player_id = ?, durability = ?, quantity = ? WHERE inventory_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM inventory WHERE inventory_id = ?";

    /* ------------------------------ Constructors ------------------------------ */
    public Inventory() {
    }

    public Inventory(int inventoryId, int durability, int quantity, Item item, Player player) {
        this.setInventoryId(inventoryId);
        this.setDurability(durability);
        this.setQuantity(quantity);
        this.setItem(item);
        this.setPlayer(player);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
            pg.initResources(Inventory.getReadQuery(true));
            pg.setStmtValues(Inventory.getReadClassList(true), Inventory.getReadValues(inventoryId));
            pg.executeQuery(false);

            inventory = Inventory.getRowInstance(pg, inventoryId);
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
    // Object instantiation
    private static Inventory getRowInstance(PostgresResources pg, int inventoryId) throws SQLException {
        Inventory inventory = Inventory.getRowInstance(pg);
        inventory.setInventoryId(inventoryId);

        return inventory;
    }

    private static Inventory getRowInstance(PostgresResources pg) throws SQLException {
        Inventory inventory = new Inventory();

        while (pg.next()) {
            inventory.setItemId(pg.getInt("item_id"));
            inventory.setPlayerId(pg.getInt("player_id"));
            inventory.setDurability(pg.getInt("durability"));
            inventory.setQuantity(pg.getInt("quantity"));
            inventory.setTypeId(pg.getInt("type_id"));
            inventory.setRarityId(pg.getInt("rarity_id"));

            if (pg.getString("is_deleted").equals("t")) {
                inventory.setPlayerId(0);
            }
        }

        return inventory;
    }

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

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Inventory.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE inventory_id = ?");
        }

        return sb.toString();
    }

    private static Class<?>[] getReadClassList(boolean hasWhere) {
        if (hasWhere) {
            return new Class[] { int.class };
        }

        return new Class<?>[0];
    }

    private static Object[] getReadValues(int inventoryId) {
        return new Object[] { inventoryId };
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
