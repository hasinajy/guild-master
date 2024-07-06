package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;

public class InventoryFull {
    // Primary attributes
    private int inventoryId;
    private int durability;
    private int quantity;

    // Join attributes
    private Item item;
    private Player player;

    // Durability constraints
    public static final int MIN_DURABILITY = 0;
    public static final int MAX_DURABILITY = 100;

    /* ------------------------------ Constructors ------------------------------ */
    public InventoryFull() {
    }

    public InventoryFull(int inventoryId, Item item, Player player, int durability, int quantity) {
        this.setInventoryId(inventoryId);
        this.setItem(item);
        this.setPlayer(player);
        this.setDurability(durability);
        this.setQuantity(quantity);
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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
    public static InventoryFull getById(int factionID) throws ClassNotFoundException, SQLException {
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
                int inventoryId = rs.getInt("inventory_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = 10 * (double) rs.getFloat("durability");
                int quantity = rs.getInt("quantity");
                String typeName = rs.getString("type_name");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                if (rs.getString("player_deleted").equals("t"))
                    characterName = null;

                inventory = new InventoryFull(inventoryId, itemName, characterName, durability, quantity, typeName,
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
                int inventoryId = rs.getInt("inventory_id");
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = Math.round(10 * (double) rs.getFloat("durability"));
                int quantity = rs.getInt("quantity");
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                int rarityId = rs.getInt("rarity_id");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                if (rs.getString("player_deleted").equals("t"))
                    characterName = null;

                data.add(
                        new InventoryFull(inventoryId, itemId, itemName, characterName, durability, quantity, typeId,
                                typeName, rarityId, rarityName, imgPath));
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

    public static List<InventoryFull> searchInventory(InventorySearchCriteria criteria)
            throws ClassNotFoundException, SQLException {

        List<InventoryFull> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            final String BASE_QUERY = "SELECT\r\n" + //
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
                    "    item.is_deleted = false AND 1 = 1";

            StringBuilder query = new StringBuilder(BASE_QUERY);

            List<Object> parameters = new ArrayList<>();

            if (criteria.getItemId() != null) {
                query.append(" AND inventory.item_id = ?");
                parameters.add(criteria.getItemId());
            }
            if (criteria.getCharacterName() != null && !criteria.getCharacterName().isEmpty()) {
                query.append(" AND player.character_name LIKE ?");
                parameters.add("%" + criteria.getCharacterName() + "%");
            }
            if (criteria.getMinDurability() != null) {
                query.append(" AND durability >= ?");
                parameters.add(criteria.getMinDurability());
            }
            if (criteria.getMaxDurability() != null) {
                query.append(" AND durability <= ?");
                parameters.add(criteria.getMaxDurability());
            }
            if (criteria.getTypeId() != null) {
                query.append(" AND item.type_id = ?");
                parameters.add(criteria.getTypeId());
            }
            if (criteria.getRarityId() != null) {
                query.append(" AND item.rarity_id = ?");
                parameters.add(criteria.getRarityId());
            }

            query.append(" ORDER BY inventory_id ASC");

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query.toString());

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                int inventoryId = rs.getInt("inventory_id");
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("item_name");
                String characterName = rs.getString("character_name");
                double durability = Math.round(10 * (double) rs.getFloat("durability"));
                int quantity = rs.getInt("quantity");
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type_name");
                int rarityId = rs.getInt("rarity_id");
                String rarityName = rs.getString("rarity_name");
                String imgPath = rs.getString("img_path");

                if (rs.getString("player_deleted").equals("t"))
                    characterName = null;

                data.add(new InventoryFull(inventoryId, itemId, itemName, characterName, durability, quantity, typeId,
                        typeName, rarityId, rarityName, imgPath));
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
