package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;
import database.PostgresResources;

public class Item {
    private int itemId;
    private String name;
    private Type type;
    private Rarity rarity;
    private String imgPath;

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
            String query = "INSERT INTO item(name, type_id, rarity_id, img_path) VALUES (?, ?, ?, ?)";
            Class<?>[] classList = new Class[] {
                    String.class,
                    int.class,
                    int.class,
                    String.class
            };
            Object[] values = new Object[] {
                    this.getName(),
                    this.getType().getTypeId(),
                    this.getRarity().getRarityId(),
                    this.getImgPath()
            };

            pg.initResources(query);
            pg.setStmtValues(classList, values);
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
            stmt.setInt(1, itemId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int typeId = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityId = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                item = new Item(itemId, name, typeId, type, rarityId, rarity, imgPath);
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

    public static List<Item> getAll() throws ClassNotFoundException, SQLException {
        List<Item> data = new ArrayList<>();

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
                int itemId = rs.getInt("item_id");
                String name = rs.getString("item_name");
                int typeId = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityId = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                data.add(new Item(itemId, name, typeId, type, rarityId, rarity, imgPath));
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
                int itemId = rs.getInt("item_id");
                String name = rs.getString("item_name");
                int typeId = rs.getInt("type_id");
                String type = rs.getString("type_name");
                int rarityId = rs.getInt("rarity_id");
                String rarity = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                data.add(new Item(itemId, name, typeId, type, rarityId, rarity, imgPath));
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

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE item SET name = ?, type_id = ?, rarity_id = ?, img_path = ? WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.name);
            stmt.setInt(2, this.typeId);
            stmt.setInt(3, this.rarityId);
            stmt.setString(4, this.imgPath);
            stmt.setInt(5, this.itemId);
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

    public void update(int itemId) throws ClassNotFoundException, SQLException {
        this.setItemId(itemId);
        this.update();
    }

    // Delete
    public static void deleteById(int itemId) throws ClassNotFoundException, SQLException {
        new Item(itemId).delete();
    }

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE item SET is_deleted = true WHERE item_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.itemId);
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
