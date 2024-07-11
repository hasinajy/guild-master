package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.PostgresResources;
import utils.NameChecker;

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
    private static final String DELETE_QUERY = "UPDATE player SET is_deleted = true WHERE player_id = ?";
    private static final String JOIN_QUERY = "SELECT * FROM v_player";

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

    public static List<Player> getAll() throws ClassNotFoundException, SQLException {
        List<Player> playerList = new ArrayList<>();
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Player.getJoinQuery(false));
            pg.executeQuery(false);

            playerList = Player.getJoinTableInstance(pg);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }

        return playerList;
    }

    // Update
    public void update() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            // TODO: Check if factionId can be null
            // TODO: Extract methods for classList and values

            String query = null;
            Class<?>[] classList = null;
            Object[] values = null;

            if (NameChecker.isNewImgPath(this.getImgPath(), "player")) {
                query = "UPDATE player SET username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ?, img_path = ? WHERE player_id = ?";

                classList = new Class[] {
                        String.class,
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        String.class,
                        int.class
                };

                values = new Object[] {
                        this.getName().getUsername(),
                        this.getName().getCharacterName(),
                        this.getGender().getGenderId(),
                        this.getLevel(),
                        this.getFaction().getFactionId(),
                        this.getDescription(),
                        this.getImgPath(),
                        this.getPlayerId()
                };
            } else {
                query = "UPDATE player SET username = ?, character_name = ?, gender_id = ?, level = ?, faction_id = ?, description = ? WHERE player_id = ?";

                classList = new Class[] {
                        String.class,
                        String.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        int.class
                };

                values = new Object[] {
                        this.getName().getUsername(),
                        this.getName().getCharacterName(),
                        this.getGender().getGenderId(),
                        this.getLevel(),
                        this.getFaction().getFactionId(),
                        this.getDescription(),
                        this.getPlayerId()
                };

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

    public void update(int playerId) throws ClassNotFoundException, SQLException {
        this.setPlayerId(playerId);
        this.update();
    }

    // Delete
    public void delete() throws ClassNotFoundException, SQLException {
        PostgresResources pg = new PostgresResources();

        try {
            pg.initResources(Player.getDeleteQuery());
            pg.setStmtValues(int.class, new Object[] { this.getPlayerId() });
            pg.executeQuery(true);
        } catch (Exception e) {
            pg.rollback();
            throw e;
        } finally {
            pg.closeResources();
        }
    }

    public static void delete(int playerId) throws ClassNotFoundException, SQLException {
        Player player = new Player();
        player.setPlayerId(playerId);
        player.delete();
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

    protected static Player createPlayerFromJoin(PostgresResources pg) throws SQLException {
        Player player = new Player();

        Name name = new Name();
        name.setUsername(pg.getString("player.username"));
        name.setCharacterName(pg.getString("player.character_name"));

        Gender gender = new Gender();
        gender.setGenderId(pg.getInt("player.gender_id"));
        gender.setName(pg.getString("gender.name"));

        Faction faction = new Faction();
        faction.setFactionId(pg.getInt("player.faction_id"));
        faction.setName(pg.getString("faction.name"));

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

    private static List<Player> getJoinTableInstance(PostgresResources pg) throws SQLException {
        List<Player> playerList = new ArrayList<>();

        while (pg.next()) {
            Player player = Player.createPlayerFromJoin(pg);
            playerList.add(player);
        }

        return playerList;
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

    // Delete
    private static String getDeleteQuery() {
        return Player.DELETE_QUERY;
    }

    // Join
    private static String getJoinQuery(boolean hasWhere) {
        StringBuilder sb = new StringBuilder(Player.JOIN_QUERY);

        if (hasWhere) {
            sb.append(" WHERE 1 = 1");
        }

        return sb.toString();
    }
}
