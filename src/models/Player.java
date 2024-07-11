package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import database.Postgres;
import database.PostgresResources;

public class Player {
    private int playerId;
    private int level;
    private String description;
    private String imgPath;
    private Name name;
    private Gender gender;
    private Faction faction;

    // Queries
    private static final String CREATE_QUERY = "INSERT INTO player(username, character_name, gender_id, level, faction_id, description, img_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_QUERY = "SELECT * FROM player";

    /* ------------------------------ Constructors ------------------------------ */
    public Player() {
    }

    public Player(int playerId, int level, String description, String imgPath, Name name, Gender gender,
            Faction faction) {
        this.setPlayerId(playerId);
        this.setLevel(level);
        this.setDescription(description);
        this.setImgPath(imgPath);
        this.setName(name);
        this.setGender(gender);
        this.setFaction(faction);
    }

    /* --------------------------- Getters and setters -------------------------- */
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    /* ---------------------------- Database methods ---------------------------- */
    // Create
    public void create() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Player.getCreateQuery());
            pg.setStmtValues(Player.getCreateClassList(), this.getCreateValues());
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    // Read
    public static Player getById(int playerId) throws ClassNotFoundException, SQLException {
        Player player = null;
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Player.getReadQuery(true));
            pg.setStmtValues(int.class, new Object[] { playerId });
            pg.executeQuery(false);

            player = Player.getRowInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return player;
    }

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Postgres.getInstance().getConnection();

            if (this.getImgPath().equals("player/")) {
                String query = "UPDATE player SET"
                        + " username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?"
                        + " WHERE player_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getUsername());
                stmt.setString(2, this.getCharacterName());
                stmt.setInt(3, this.getGenderId());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionId() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionId());
                }

                stmt.setString(6, this.getDescription());
                stmt.setInt(7, this.playerId);
            } else {
                String query = "UPDATE player SET"
                        + " username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?, img_path = ?"
                        + " WHERE player_id = ?";

                stmt = conn.prepareStatement(query);
                stmt.setString(1, this.getUsername());
                stmt.setString(2, this.getCharacterName());
                stmt.setInt(3, this.getGenderId());
                stmt.setInt(4, this.getLevel());

                if (this.getFactionId() == 0) {
                    stmt.setNull(5, Types.INTEGER);
                } else {
                    stmt.setInt(5, this.getFactionId());
                }

                stmt.setString(6, this.getDescription());
                stmt.setString(7, this.getImgPath());
                stmt.setInt(8, this.playerId);
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

    public void update(int playerId) throws ClassNotFoundException, SQLException {
        this.setPlayerId(playerId);
        this.update();
    }

    // Delete
    public void delete() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE\r\n" + //
                    "    player\r\n" + //
                    "SET\r\n" + //
                    "    is_deleted = true\r\n" + //
                    "WHERE\r\n" + //
                    "    player_id = ?;";

            conn = Postgres.getInstance().getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, this.getPlayerId());
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

    public static void delete(int playerId) throws ClassNotFoundException, SQLException {
        new Player(playerId).delete();
    }

    /* ----------------------------- Utility methods ---------------------------- */
    // Instantiation methods
    protected static Player createPlayerFromResultSet(PostgresResources pg) throws SQLException {
        Player player = new Player();

        Name name = new Name();
        name.setUsername(pg.getString("player.username"));
        name.setCharacterName(pg.getString("player.character_name"));

        Gender gender = new Gender();
        gender.setGenderId(pg.getInt("player.gender_id"));

        Faction faction = new Faction();
        faction.setFactionId(pg.getInt("player.faction_id"));

        player.setPlayerId(pg.getInt("player.player_id"));
        player.setName(name);
        player.setGender(gender);
        player.setLevel(pg.getInt("player.level"));
        player.setFaction(faction);
        player.setDescription(pg.getString("player.description"));
        player.setImgPath(pg.getString("player.img_path"));

        return player;
    }

    private static Player getRowInstance(PostgresResources pg) throws SQLException {
        Player player = null;

        if (pg.next()) {
            player = Player.createPlayerFromResultSet(pg);
        }

        return player;
    }

    // Create
    private static String getCreateQuery() {
        return Player.CREATE_QUERY;
    }

    private static Class<?>[] getCreateClassList() {
        return new Class[] {
                String.class,
                String.class,
                int.class,
                int.class,
                int.class,
                String.class,
                String.class
        };
    }

    private Object[] getCreateValues() {
        return new Object[] {
                this.getName().getUsername(),
                this.getName().getCharacterName(),
                this.getGender().getGenderId(),
                this.getLevel(),
                this.getFaction().getFactionId(),
                this.getDescription(),
                this.getImgPath()
        };
    }

    // Read
    private static String getReadQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Player.READ_QUERY);

        if (hasWhere) {
            sb.append(" WHERE player_id = ?");
        }

        return sb.toString();
    }
}
