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
    private static final String READ_QUERY = "SELECT * FROM v_inventory_active";
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
            pg.initResources(Inventory.getCreateQuery());
            pg.setStmtValues(Inventory.getCreateClassList(), this.getCreateValues());
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
            pg.setStmtValues(int.class, new Object[] { inventoryId });
            pg.executeQuery(false);

            inventory = Inventory.getRowInstance(pg);
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
    protected static Inventory createInventoryFromResultSet(PostgresResources pg) throws SQLException {
        Inventory inventory = new Inventory();

        Item item = new Item();
        item.setItemId(pg.getInt("inventory.item_id"));

        // Player may be deleted but the item remains in the inventory
        int playerId = (pg.getString("player.is_deleted").equals("t")) ? 0 : pg.getInt("inventory.player_id");

        Player player = new Player();
        player.setPlayerId(playerId);

        inventory.setInventoryId(pg.getInt("inventory.inventory_id"));
        inventory.setDurability(pg.getInt("inventory.durability"));
        inventory.setQuantity(pg.getInt("inventory.quantity"));
        inventory.setItem(item);
        inventory.setPlayer(player);

        return inventory;
    }

    private static Inventory getRowInstance(PostgresResources pg) throws SQLException {
        Inventory inventory = new Inventory();

        if (pg.next()) {
            inventory = Inventory.createInventoryFromResultSet(pg);
        }

        return inventory;
    }

    // Create
    private static String getCreateQuery() {
        return Inventory.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class<?>[] {
                int.class,
                int.class,
                int.class,
                int.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getItem().getItemId(),
                this.getPlayer().getPlayerId(),
                this.getDurability(),
                this.getQuantity()
        };
    }

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Inventory.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE inventory.inventory_id = ?");
        }

        return sb.toString();
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
