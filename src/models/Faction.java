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

    public static Faction getById(int factionId) throws ClassNotFoundException, SQLException {
        Faction faction = null;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT name, img_path FROM faction WHERE faction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, factionId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                faction = new Faction(factionId, name, imgPath);
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

        return faction;
    }

    public static List<Faction> getAll() throws ClassNotFoundException, SQLException {
        List<Faction> data = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT faction_id, name, img_path FROM faction";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int factionId = rs.getInt("faction_id");
                String name = rs.getString("name");
                String imgPath = rs.getString("img_path");

                data.add(new Faction(factionId, name, imgPath));
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

    public void create() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO faction(name, img_path) VALUES (?, ?)";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, this.getName());
            stmt.setString(2, this.getImgPath());
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

    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM faction WHERE faction_id = ?";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.getFactionId());
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
            conn = Postgres.getInstance().getConnection();

            if (this.getImgPath().equals("faction/")) {
                String query = "UPDATE faction SET name = ? WHERE faction_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setInt(2, this.getFactionId());
            } else {
                String query = "UPDATE faction SET name = ?, img_path = ? WHERE faction_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getName());
                stmt.setString(2, this.getImgPath());
                stmt.setInt(3, this.getFactionId());
            }

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

    public void update(int factionId) throws ClassNotFoundException, SQLException {
        this.setFactionId(factionId);
        this.update();
    }
}