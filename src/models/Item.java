package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;

public class Item {
    private int itemId;
    private String name;
    private Type type;
    private Rarity rarity;
    private String imgPath;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO item(name, type_id, rarity_id, img_path) VALUES (?, ?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM v_item";
    private static final String UPDATE_QUERY = "UPDATE item SET name = ?, type_id = ?, rarity_id = ?, img_path = ? WHERE item_id = ?";
    private static final String DELETE_QUERY = "UPDATE item SET is_deleted = true WHERE item_id = ?";

    /* ------------------------------ Constructors ------------------------------ */
    public Item() {
    }

    public Item(int itemId) {
        this.setItemId(itemId);
    }

    public Item(int itemId, String name, Type type, Rarity rarity, String imgPath) {
        this.setItemId(itemId);
        this.setName(name);
        this.setType(type);
        this.setRarity(rarity);
        this.setImgPath(imgPath);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Item.getCreateQuery());
            pg.setStmtValues(Item.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Item getById(int itemId) throws ClassNotFoundException, SQLException {
        Item item = null;
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Item.getReadQuery(true));
            pg.setStmtValues(int.class, new Object[] { itemId });
            pg.executeQuery(false);

            item = Item.getRowInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return item;
    }

    public static List<Item> getAll() throws ClassNotFoundException, SQLException {
        List<Item> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Item.getReadQuery(false));
            pg.executeQuery(false);

            data = Item.getTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }

    public static List<Item> searchItem(String name) throws ClassNotFoundException, SQLException {
        List<Item> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            StringBuilder query = new StringBuilder(Item.getReadQuery(false));
            List<Object> parameters = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                query.append(" AND item.name ILIKE ?");
                parameters.add("%" + name + "%");
            }

            Class<?>[] paramTypes = new Class<?>[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                paramTypes[i] = parameters.get(i).getClass();
            }

            pg.initResources(query.toString());
            pg.setStmtValues(paramTypes, parameters.toArray());
            pg.executeQuery(false);

            data = Item.getTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return data;
    }

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Dynamic query based on img_path
            pg.initResources(Item.getUpdateQuery());
            pg.setStmtValues(Item.getUpdateClassList(), this.getUpdateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public void update(int itemId) throws ClassNotFoundException, SQLException {
        this.setItemId(itemId);
        this.update();
    }

    // Delete
    public static void deleteById(int itemId) throws ClassNotFoundException, SQLException {
        new Item(itemId).delete();
    }

    public void delete() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Item.getDeleteQuery());
            pg.setStmtValues(int.class, new Object[] { this.getItemId() });
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    protected static Item createItemFromResultSet(PostgresResources pg) throws SQLException {
        Item item = new Item();
        item.setItemId(pg.getInt("item.item_id"));
        item.setName(pg.getString("item.name"));
        item.setImgPath(pg.getString("item.img_path"));

        // TODO: Check getRowInstance use of Rarity since it's changed
        Type type = Type.createTypeFromResultSet(pg);
        Rarity rarity = Rarity.createRarityFromResultSet(pg);

        item.setType(type);
        item.setRarity(rarity);

        return item;
    }

    private static Item getRowInstance(PostgresResources pg) throws SQLException {
        Item item = null;

        if (pg.next()) {
            item = createItemFromResultSet(pg);
        }

        return item;
    }

    private static List<Item> getTableInstance(PostgresResources pg) throws SQLException {
        List<Item> itemList = new ArrayList<>();

        while (pg.next()) {
            Item item = createItemFromResultSet(pg);
            itemList.add(item);
        }

        return itemList;
    }

    // Create
    private static String getCreateQuery() {
        return Item.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class[] {
                String.class,
                int.class,
                int.class,
                String.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getName(),
                this.getType().getTypeId(),
                this.getRarity().getRarityId(),
                this.getImgPath()
        };
    }

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Item.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE item_id = ?");
        }

        return sb.toString();
    }

    // Update
    private static String getUpdateQuery() {
        return Item.UPDATE_QUERY;
    }

    private static Class<?>[] getUpdateClassList() {
        return new Class<?>[] {
                String.class,
                int.class,
                int.class,
                String.class,
                int.class
        };
    }

    private Object[] getUpdateValues() {
        return new Object[] {
                this.getName(),
                this.getType().getTypeId(),
                this.getRarity().getRarityId(),
                this.getImgPath(),
                this.getItemId()
        };
    }

    // Delete
    private static String getDeleteQuery() {
        return Item.DELETE_QUERY;
    }
}
