package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;
import utils.NameChecker;

public class Faction {
    private int factionId;
    private String name;
    private String imgPath;

    /* ------------------------------ Constructors ------------------------------ */
    public Faction() {
    }

    public Faction(int factionId) {
        this.setFactionId(factionId);
        this.setName("Default Faction");
    }

    public Faction(int factionId, String name, String imgPath) {
        this.setFactionId(factionId);
        this.setName(name);
        this.setImgPath(imgPath);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getFactionId() {
        return this.factionId;
    }

    public void setFactionId(int factionId) {
        this.factionId = factionId;
    }

    public String getName() {
        return this.name;
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
    // Utility methods
    private static Faction getRowInstance(PostgresResources pg) throws SQLException {
        Faction faction = new Faction();

        faction.setFactionId(pg.getInt("faction_id"));
        faction.setName(pg.getString("name"));
        faction.setImgPath(pg.getString("img_path"));

        return faction;
    }

    private static List<Faction> getTableInstance(PostgresResources pg) throws SQLException {
        List<Faction> factionList = new ArrayList<>();

        while (pg.next()) {
            factionList.add(Faction.getRowInstance(pg));
        }

        return factionList;
    }

    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            String query = "INSERT INTO faction(name, img_path) VALUES (?, ?)";

            pg.initResources(query);
            pg.setStmtValues(String.class, new Object[] { this.getName(), this.getImgPath() });
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Faction getById(int factionId) throws ClassNotFoundException, SQLException {
        Faction faction = null;
        PostgresResources pg = new PostgresResources();

        try {
            String query = "SELECT name, img_path FROM faction WHERE faction_id = ?";

            pg.initResources(query);
            pg.setStmtValues(new Class<?>[] { int.class }, new Object[] { factionId });
            pg.executeQuery(false);

            while (pg.next()) {
                String name = pg.getString("name");
                String imgPath = pg.getString("img_path");

                faction = new Faction(factionId, name, imgPath);
            }
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return faction;
    }

    public static List<Faction> getAll() throws ClassNotFoundException, SQLException {
        List<Faction> data = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            String query = "SELECT faction_id, name, img_path FROM faction";

            pg.initResources(query);
            pg.executeQuery(false);

            data = Faction.getTableInstance(pg);
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
            String query = null;
            Class<?>[] classList = null;
            Object[] values = null;

            if (NameChecker.isNewImgPath(this.getImgPath(), "faction")) {
                query = "UPDATE faction SET name = ?, img_path = ? WHERE faction_id = ?";
                classList = new Class<?>[] { String.class, String.class, int.class };
                values = new Object[] { this.getName(), this.getImgPath(), this.getFactionId() };
            } else {
                query = "UPDATE faction SET name = ? WHERE faction_id = ?";
                classList = new Class<?>[] { String.class, int.class };
                values = new Object[] { this.getName(), this.getFactionId() };
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

    public void update(int factionId) throws ClassNotFoundException, SQLException {
        this.setFactionId(factionId);
        this.update();
    }

    // Delete
    public static void deleteById(int factionId) throws ClassNotFoundException, SQLException {
        new Faction(factionId).delete();
    }

    public void delete() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            String query = "DELETE FROM faction WHERE faction_id = ?";

            pg.initResources(query);
            pg.setStmtValues(int.class, new Object[] { this.getFactionId() });
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }
}