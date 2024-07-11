package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.Postgres;
import database.PostgresResources;
import utils.NameChecker;

public class Rarity {
    private int rarityId;
    private String name;
    private String imgPath;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO rarity(name, img_path) VALUES (?, ?)";
    private static final String READ_QUERY = "SELECT * FROM rarity";

    /* ------------------------------ Constructors ------------------------------ */
    public Rarity() {
    }

    public Rarity(int rarityId, String name, String imgPath) {
        this.rarityId = rarityId;
        this.name = name;
        this.setImgPath(imgPath);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getRarityId() {
        return rarityId;
    }

    public void setRarityId(int rarityId) {
        this.rarityId = rarityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Rarity.getCreateQuery());
            pg.setStmtValues(Rarity.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Rarity getById(int rarityId) throws ClassNotFoundException, SQLException {
        Rarity rarity = null;
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Rarity.getReadQuery(false));
            pg.setStmtValues(int.class, new Object[] { rarityId });
            pg.executeQuery(false);

            rarity = Rarity.getRowInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return rarity;
    }

    public static List<Rarity> getAll() throws ClassNotFoundException, SQLException {
        List<Rarity> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Rarity.getReadQuery(false));
            pg.executeQuery(false);

            data = Rarity.getTableInstance(pg);
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
            // TODO: Extract methods for the classList and values

            String query = null;
            Class<?>[] classList = null;
            Object[] values = null;

            if (NameChecker.isNewImgPath(this.getImgPath(), "rarity")) {
                query = "UPDATE rarity SET name = ?, img_path = ? WHERE rarity_id = ?";
                classList = new Class<?>[] { String.class, String.class, int.class };
                values = new Object[] { this.getName(), this.getImgPath(), this.getRarityId() };
            } else {
                query = "UPDATE rarity SET name = ? WHERE rarity_id = ?";
                classList = new Class<?>[] { String.class, int.class };
                values = new Object[] { this.getName(), this.getRarityId() };
            }

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

    public void update(int rarityId) throws ClassNotFoundException, SQLException {
        this.setRarityId(rarityId);
        this.update();
    }

    // Delete
    public static void deleteById(int rarityId) throws ClassNotFoundException, SQLException {
        new Rarity(rarityId).delete();
    }

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM rarity WHERE rarity_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.rarityId);
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

    public static void delete(int rarityId) throws ClassNotFoundException, SQLException {
        new Rarity(rarityId).delete();
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    protected static Rarity createRarityFromResultSet(PostgresResources pg) throws SQLException {
        Rarity rarity = new Rarity();

        rarity.setRarityId(pg.getInt("rarity.rarity_id"));
        rarity.setName(pg.getString("rarity.name"));
        rarity.setImgPath(pg.getString("rarity.img_path"));

        return rarity;
    }

    protected static Rarity getRowInstance(PostgresResources pg) throws SQLException {
        Rarity rarity = null;

        if (pg.next()) {
            rarity = Rarity.createRarityFromResultSet(pg);
        }

        return rarity;
    }

    private static List<Rarity> getTableInstance(PostgresResources pg) throws SQLException {
        List<Rarity> rarityList = new ArrayList<>();

        while (pg.next()) {
            Rarity rarity = Rarity.createRarityFromResultSet(pg);
            rarityList.add(rarity);
        }

        return rarityList;
    }

    // Create
    private static String getCreateQuery() {
        return Rarity.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class[] {
                String.class,
                String.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getName(),
                this.getImgPath()
        };
    }

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Rarity.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE rarity_id = ?");
        }

        return sb.toString();
    }
}
